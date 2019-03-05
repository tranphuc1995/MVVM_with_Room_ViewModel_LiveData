package com.tranphuc.mvvm.repository.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.tranphuc.mvvm.repository.database.entity.People


@Dao
interface PeopleDao {
    @Insert
    fun insertPeople(people: People)

    @Query("SELECT * FROM people_table")
    fun getAllPeople(): LiveData<List<People>>

    @Query("DELETE FROM people_table")
    fun deleteAllPeople()
}