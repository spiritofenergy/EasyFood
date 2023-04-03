package com.kodex.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodex.easyfood.db.MealDatabase
import com.kodex.easyfood.pojo.*
import com.kodex.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
   private val mealDatabase: MealDatabase
):ViewModel(

) {
        private var randomMealLiveData = MutableLiveData<Meal>()
        private val popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
        private var categoriesLiveData = MutableLiveData<List<Category>>()
        private val favoriteMealLiveData = mealDatabase.mealDao().getAllMeals()
        private val bottomSheetMealLiveData = MutableLiveData<Meal>()
        private val searchMealLiveData = MutableLiveData<List<Meal>>()

     private var saveStateRandomMeal: Meal ? = null
        fun getRandomMeal(){
            saveStateRandomMeal?.let { randomMeal ->
                randomMealLiveData.postValue(randomMeal)
                return
            }

        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                        randomMealLiveData.value = randomMeal
                        saveStateRandomMeal = randomMeal
                }else {
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("check onFailure HomeViewModel ", t.message.toString())
            }
        })
    }
    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.body() != null){
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }
            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
               Log.d( "check onFailure HomeViewModel 2", t.message.toString())
            }

        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d( "api.getCategories", t.message.toString())
            }
        })
    }
    fun getMealById(id: String){
        RetrofitInstance.api.getMealDetail(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let {meal ->
                    bottomSheetMealLiveData.postValue(meal)
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("HomeViewModel", t.message.toString())            }

        })
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)

        }
    }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
    fun searchMeals(searchQuery: String) = RetrofitInstance.api.searchMeals(searchQuery).enqueue(
        object: Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    val mealList = response.body()?.meals
                mealList?.let {
                    searchMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeViewModel searchMeal", t.message.toString())            }


        }
    )
    fun observeSearchedMealsLiveData(): LiveData<List<Meal>> = searchMealLiveData

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
    fun observePopularItemLiveData(): LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }
    fun observeCategoriesLiveData(): MutableLiveData<List<Category>> {
        return categoriesLiveData
    }
    fun observeFavoriteMealsLiveData():LiveData<List<Meal>>{
        return favoriteMealLiveData
    }
    fun observeBottomSheetMeal() : LiveData<Meal> = bottomSheetMealLiveData
}