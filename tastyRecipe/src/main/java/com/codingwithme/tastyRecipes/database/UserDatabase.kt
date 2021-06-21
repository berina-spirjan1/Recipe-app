package com.codingwithme.tastyRecipes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ba.unsa.pmf.math.tastyrecipes.data.UserDao
import com.codingwithme.tastyRecipes.entities.User

/*Sadrži nositelja baze podataka i usluge kao glavnu pristupnu tačku za temeljnu vezu s podacima koji se održavaju u našoj aplikaciji*/
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase?=null

            fun getDataBase(context: Context): UserDatabase {
                val tempInstance = INSTANCE
                if (tempInstance != null) {
                    return tempInstance
                }
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
}