package com.kodex.easyfood.activites

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.kodex.easyfood.adapters.CategoriesAdapter
import com.kodex.easyfood.adapters.CategoryMealsAdapter
import com.kodex.easyfood.adapters.MealsAdapter
import com.kodex.easyfood.databinding.ActivityCategoryMealsMainBinding
import com.kodex.easyfood.fragments.HomeFragment
import com.kodex.easyfood.pojo.Category
import com.kodex.easyfood.pojo.MealsByCategory
import com.kodex.easyfood.utils.Constants
import com.kodex.easyfood.utils.Constants.CATEGORY_NAME
import com.kodex.easyfood.viewmodel.CategoryMealsViewModel
import com.kodex.easyfood.viewmodel.MealViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsMainBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter
    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var mealsAdapter: MealsAdapter
    var onItemClick : ((Category) -> Unit)? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(CATEGORY_NAME)!!)

        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList ->
            binding.tvCategoryCount.text = mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)
        })
        mealsAdapter = MealsAdapter()

        mealsAdapter.getMealsByCategory(intent.getStringExtra(CATEGORY_NAME)!!)

        mealsAdapter.observeMealsLiveData().observe(this, Observer { mealsList ->
            binding.tvCategoryCount.text = mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)
        })


        onCategoryClick()
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }

    private fun onCategoryClick() {
        mealsAdapter.onItemClick = {meal ->
            Log.d("onCategoryClick"," meal")
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(CATEGORY_NAME, meal.strCategory)
            Log.d("check", "Category.strCategory: \n $meal.strCategory")
            startActivity(intent)
        }




          /*  val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(Constants.MEAL_ID, meal.strCategory)
            intent.putExtra(Constants.MEAL_NAME, meal.strCategoryThumb)
            intent.putExtra(Constants.MEAL_THUMB, meal.strCategoryDescription)
            startActivity(intent)*/
        }
        }


