package com.tranphuc.mvvm.repository.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.tranphuc.mvvm.repository.database.entity.People


@Dao
interface PeopleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeople(people: List<People>)

    @Query("SELECT * FROM people_table")
    fun getAllPeople(): LiveData<List<People>>

    @Query("DELETE FROM people_table")
    fun deleteAllPeople()
}