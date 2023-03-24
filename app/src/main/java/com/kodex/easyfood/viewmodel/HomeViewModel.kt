package com.kodex.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kodex.easyfood.pojo.CategoryList
import com.kodex.easyfood.pojo.Meal
import com.kodex.easyfood.pojo.MealList
import com.kodex.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {
        private  var randomMealLiveData = MutableLiveData<Meal>()
        private val popularItemsLiveData = MutableLiveData<CategoryList>()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                        randomMealLiveData.value = randomMeal
                }else {
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("check", t.message.toString())
            }

        })
    }
    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null){
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
}