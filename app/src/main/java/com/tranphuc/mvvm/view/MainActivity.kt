package com.tranphuc.mvvm.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.tranphuc.mvvm.R
import com.tranphuc.mvvm.adapter.PeopleAdapter
import com.tranphuc.mvvm.repository.database.entity.People
import com.tranphuc.mvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mPeopleAdapter: PeopleAdapter
    private lateinit var mMainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        addObserve()
    }

    private fun initView() {
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initRvPeople()
        mMainViewModel.loadDataListPeople()
    }

    private fun addObserve() {
        mMainViewModel.getListPeople().observe(this, object : Observer<List<People>> {
            override fun onChanged(t: List<People>?) {
                mPeopleAdapter.ListPeople = t as MutableList<People>
                mPeopleAdapter.notifyDataSetChanged()
            }
        })

        mMainViewModel.getIsShowProgress().observe(this, object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                if (t as Boolean) {
                    pbLoadingDataPeople.visibility = View.VISIBLE
                } else {
                    pbLoadingDataPeople.visibility = View.GONE
                }
            }
        })

        mMainViewModel.getListPeopleFromDb()?.observe(this, object : Observer<List<People>> {
            override fun onChanged(t: List<People>?) {
                mPeopleAdapter.ListPeople = t as MutableList<People>
                mPeopleAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun initRvPeople() {
        var linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rvPeople.layoutManager = linearLayoutManager
        mPeopleAdapter = PeopleAdapter(mMainViewModel.getListPeople().value as MutableList<People>, this)
        rvPeople.adapter = mPeopleAdapter
    }
}
