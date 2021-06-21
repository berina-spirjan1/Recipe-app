package com.codingwithme.tastyRecipes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codingwithme.tastyRecipes.dao.MyRecipesDao
import com.codingwithme.tastyRecipes.entities.MyRecipes


@Database(entities = [MyRecipes::class], version = 1, exportSchema = false)
abstract class MyRecipesDatabase:RoomDatabase() {
    abstract fun myRecipesDao(): MyRecipesDao
    companion object {
        @Volatile
        private var INSTANCE: MyRecipesDatabase?=null

        fun getDataMyRecipes(context: Context): MyRecipesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRecipesDatabase::class.java,
                    "my_recipes"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}