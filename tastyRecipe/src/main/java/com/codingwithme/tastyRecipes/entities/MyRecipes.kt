package com.codingwithme.tastyRecipes.entities

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName="my_recipes")
data class MyRecipes(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name="name")
    val name: String,

    @ColumnInfo(name="ingredients")
    val ingredients: String,

    @ColumnInfo(name="instructions")
    val instructions: String
): Parcelable