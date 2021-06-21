package com.codingwithme.tastyRecipes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.codingwithme.tastyRecipes.database.MyRecipesDatabase
import com.codingwithme.tastyRecipes.entities.MyRecipes
import com.codingwithme.tastyRecipes.repository.MyRecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyRecipesViewModel(application : Application): AndroidViewModel(application) {
    val readAllMyRecipes: LiveData<List<MyRecipes>>
    private val repository: MyRecipesRepository
    init {
        val myRecipesDao= MyRecipesDatabase.getDataMyRecipes(application).myRecipesDao()
        repository= MyRecipesRepository(myRecipesDao)
        readAllMyRecipes=repository.readAllMyRecipes

    }
    fun addMyRecipes(recipes: MyRecipes){
        viewModelScope.launch(Dispatchers.IO){
            repository.addMyRecipes(recipes)
        }
    }
    fun updateMyRecipes(recipes: MyRecipes){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateMyRecipes(recipes)
        }
    }
    fun deleteMyRecipe(recipes: MyRecipes){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteMyRecipe(recipes)
        }
    }
    fun deleteAllMyRecipes(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllMyRecipes()
        }
    }

}