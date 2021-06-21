package com.codingwithme.tastyRecipes.repository

import androidx.lifecycle.LiveData
import ba.unsa.pmf.math.tastyrecipes.data.UserDao
import com.codingwithme.tastyRecipes.dao.MyRecipesDao
import com.codingwithme.tastyRecipes.entities.MyRecipes
import com.codingwithme.tastyRecipes.entities.User

class MyRecipesRepository(private val myRecipesDao: MyRecipesDao) {
    val readAllMyRecipes: LiveData<List<MyRecipes>> = myRecipesDao.readAllMyRecipes()
    suspend fun addMyRecipes(recipes: MyRecipes){
        myRecipesDao.addMyRecipes(recipes)
    }
    suspend fun updateMyRecipes(recipes: MyRecipes){
        myRecipesDao.updateMyRecipes(recipes)
    }
    suspend fun deleteMyRecipe(recipes: MyRecipes){
        myRecipesDao.deleteMyRecipe(recipes)
    }
    suspend fun deleteAllMyRecipes() {
        myRecipesDao.deleteAllMyRecipes()
    }

}