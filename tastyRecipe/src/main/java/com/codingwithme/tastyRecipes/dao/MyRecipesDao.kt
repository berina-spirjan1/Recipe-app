package com.codingwithme.tastyRecipes.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codingwithme.tastyRecipes.entities.MyRecipes

@Dao
interface MyRecipesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMyRecipes(recipes: MyRecipes)

    @Update
    suspend fun updateMyRecipes(recipes: MyRecipes)

    @Delete
    suspend fun deleteMyRecipe(recipes: MyRecipes)

    @Query("DELETE FROM my_recipes")
    suspend fun deleteAllMyRecipes()

    @Query("SELECT * FROM my_recipes ORDER BY id")
    fun readAllMyRecipes(): LiveData<List<MyRecipes>>
}