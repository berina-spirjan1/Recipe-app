package com.codingwithme.tastyRecipes.interfaces

import com.codingwithme.tastyRecipes.entities.Category
import com.codingwithme.tastyRecipes.entities.Meal
import com.codingwithme.tastyRecipes.entities.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDataService {
    @GET("categories.php")
    fun getCategoryList(): Call<Category>

    @GET("filter.php")
    fun getMealList(@Query("c") category: String): Call<Meal>

    @GET("lookup.php")
    fun getSpecificItem(@Query("i") id: String): Call<MealResponse>


}