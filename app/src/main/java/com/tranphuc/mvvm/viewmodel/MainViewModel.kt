package com.tranphuc.mvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import com.tranphuc.mvvm.repository.database.AppDatabase
import com.tranphuc.mvvm.repository.database.dao.PeopleDao
import com.tranphuc.mvvm.repository.database.entity.People
import com.tranphuc.mvvm.repository.service.ApiUtils
import com.tranphuc.mvvm.utils.AppUtils
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mListPeople: MutableLiveData<List<People>>
    private var mIsShowProgress: MutableLiveData<Boolean>
    private var mListPeopleFromDb: LiveData<List<People>>?
    private lateinit var mApiUtils: ApiUtils
    private var db: AppDatabase? = null

    init {
        mListPeople = MutableLiveData<List<People>>().apply { value = ArrayList() }
        mIsShowProgress = MutableLiveData<Boolean>().apply { value = false }
        mListPeopleFromDb = null
        mApiUtils = ApiUtils()
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
        var response = mApiUtils.getApiService().getPeople("5", "en")
        response.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                var listPeople: MutableList<People> = ArrayList()
                var root = JSONObject(response.body().toString())
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
        })
    }

    private fun setIsShowProgress(isShowProgress: Boolean) {
        mIsShowProgress.value = isShowProgress
    }

    private fun getPeopleFromDb() {
        mListPeopleFromDb = db?.peopleDao()?.getAllPeople()
    }
}