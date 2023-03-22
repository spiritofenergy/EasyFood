package com.kodex.easyfood.retrofit

import com.kodex.easyfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>


}