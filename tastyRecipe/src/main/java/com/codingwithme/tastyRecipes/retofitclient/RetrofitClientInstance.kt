package com.codingwithme.tastyRecipes.retofitclient

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.constraintlayout.solver.state.State
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

public class RetrofitClientInstance {

    companion object{
        val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
        private var retrofit: Retrofit? = null
        val retrofitInstance: Retrofit?
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                }
                return retrofit
            }
    }



}