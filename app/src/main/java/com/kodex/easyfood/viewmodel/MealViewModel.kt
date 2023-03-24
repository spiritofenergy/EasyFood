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

class MealViewModel():ViewModel() {
    private val mealDetailLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id: String){
        RetrofitInstance.api.getMealDetail(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    mealDetailLiveData.value = response.body()!!.meals[0]
                }
                else
                    return

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                           }
        })
    }
    fun observerMealDetailsLiveData():LiveData<Meal>{
        return mealDetailLiveData
    }
}