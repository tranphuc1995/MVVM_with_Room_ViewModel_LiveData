package com.tranphuc.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.widget.Toast
import com.tranphuc.mvvm.repository.database.AppDatabase
import com.tranphuc.mvvm.repository.database.entity.People
import com.tranphuc.mvvm.repository.service.RetrofitClient
import com.tranphuc.mvvm.utils.AppUtils
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mListPeople: MutableLiveData<List<People>>
    private var mIsShowProgress: MutableLiveData<Boolean>
    private var mListPeopleFromDb: LiveData<List<People>>?
    private var db: AppDatabase? = null

    init {
        mListPeople = MutableLiveData<List<People>>().apply { value = ArrayList() }
        mIsShowProgress = MutableLiveData<Boolean>().apply { value = false }
        mListPeopleFromDb = null
        db = AppDatabase.getAppDataBase(application.applicationContext)
    }

    public fun getListPeople(): LiveData<List<People>> {
        return mListPeople
    }

    public fun getIsShowProgress(): LiveData<Boolean> {
        return mIsShowProgress
    }

    public fun getListPeopleFromDb(): LiveData<List<People>>? {
        return mListPeopleFromDb
    }

    public fun loadDataListPeople() {
        if (AppUtils.isNetworkReachable(getApplication())) {
            getPeopleFromApi()
        } else {
            getPeopleFromDb()
        }
    }


    private fun getPeopleFromApi() {
        Thread(Runnable {
            db?.peopleDao()?.deleteAllPeople()
        }).start()
        setIsShowProgress(true)
        var response = RetrofitClient.getInstance()?.getApi()?.getPeople("5", "en")
        response?.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                onCallApiFailed()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                onCallApiSuccess(response.body().toString())
            }
        })
    }

    private fun onCallApiFailed() {
        Toast.makeText(getApplication(), "can't get list people", Toast.LENGTH_SHORT).show()
    }

    private fun onCallApiSuccess(result: String) {
        var root = JSONObject(result)
        var listPeople: MutableList<People> = ArrayList()
        var jsonArrays = root.getJSONArray("results")
        for (i in (0..jsonArrays.length() - 1)) {
            var jsonItem = jsonArrays.getJSONObject(i)
            var name = jsonItem.getJSONObject("name").getString("last")
            var linkImage = jsonItem.getJSONObject("picture").getString("medium")
            var people = People(name, linkImage)
            listPeople.add(people)
            Thread(Runnable {
                db?.peopleDao()?.insertPeople(people)
            }).start()
        }
        mListPeople.postValue(listPeople)
        setIsShowProgress(false)
    }

    private fun setIsShowProgress(isShowProgress: Boolean) {
        mIsShowProgress.value = isShowProgress
    }

    private fun getPeopleFromDb() {
        mListPeopleFromDb = db?.peopleDao()?.getAllPeople()
    }
}