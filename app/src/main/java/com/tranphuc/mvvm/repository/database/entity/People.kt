package com.tranphuc.mvvm.repository.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "people_table")
class People {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    private var mName: String = ""

    private var mLinkImage: String = ""

    constructor(mName: String, mLinkImage: String) {
        this.mName = mName
        this.mLinkImage = mLinkImage
    }

    public var Name: String
        get() {
            return mName
        }
        set(value) {
            mName = value
        }

    public var LinkImage: String
        get() {
            return mLinkImage
        }
        set(value) {
            mLinkImage = value
        }
}