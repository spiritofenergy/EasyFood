package com.kodex.easyfood.activites

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kodex.easyfood.R
import com.kodex.easyfood.databinding.ActivityMealBinding
import com.kodex.easyfood.fragments.HomeFragment
import com.kodex.easyfood.pojo.Meal
import com.kodex.easyfood.viewmodel.MealViewModel



class MealActivity : AppCompatActivity() {

    private lateinit var mealId : String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var youtubeLink: String
    private lateinit var mealMvvm: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]

        getMealInformationFormIntent()

        setInformationViews()
        loadingSCase()
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYotube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
       mealMvvm.observerMealDetailsLiveData().observe(this
       ) { t ->
           onResponseCase()
           val meal = t

           Log.d("check2", "meal: ${t!!.strCategory}")

           binding.tvCategory.text = "Category : ${meal!!.strCategory}"
           binding.tvArea.text = "Area : ${meal.strArea}"
           binding.tvInstructionSt.text = meal.strInstructions

           youtubeLink = meal.strYoutube
       }
    }

    private fun setInformationViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFormIntent() {
          val intent = intent
            mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
            mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
            mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
        }
    private fun loadingSCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYotube.visibility = View.INVISIBLE

    }
    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYotube.visibility = View.VISIBLE
    }
    }

