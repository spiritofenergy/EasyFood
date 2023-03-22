package com.kodex.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kodex.easyfood.pojo.Meal
import com.kodex.easyfood.pojo.MealList
import com.kodex.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {
        private  var randomMealLiveData = MutableLiveData<Meal>()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                        randomMealLiveData.value = randomMeal
                    // Log.d("check", "Meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}")
                }else {
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("check", t.message.toString())
            }

        })
    }
    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
}