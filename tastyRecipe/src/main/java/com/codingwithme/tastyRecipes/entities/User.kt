package com.codingwithme.tastyRecipes.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//User predstavlja tabelu unutar baze podataka

@Parcelize
@Entity(tableName="user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="mail")
    val mail: String,
    @ColumnInfo(name="password")
    val password: String
    ):Parcelable