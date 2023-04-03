package com.kodex.easyfood.activites

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kodex.easyfood.R
import com.kodex.easyfood.databinding.ActivityMealBinding
import com.kodex.easyfood.db.MealDatabase
import com.kodex.easyfood.pojo.Meal
import com.kodex.easyfood.viewmodel.MealViewModel

import com.kodex.easyfood.utils.Constants.MEAL_ID
import com.kodex.easyfood.utils.Constants.MEAL_NAME
import com.kodex.easyfood.utils.Constants.MEAL_THUMB
import com.kodex.easyfood.viewmodel.MealViewModelFactory

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

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        getMealInformationFormIntent()

        setInformationViews()
        loadingSCase()
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this,"Meal saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYotube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave:Meal? = null
    @SuppressLint("SetTextI18n")
    private fun observerMealDetailsLiveData() {
       mealMvvm.observerMealDetailsLiveData().observe(this
       ) { t ->
           onResponseCase()
           mealToSave = t

           Log.d("check DetailsLiveData", "strCategory: ${t!!.strCategory}")

           binding.tvCategory.text = "Category : ${t.strCategory}"
           binding.tvArea.text = "Area : ${t.strArea}"
           binding.tvInstructionSt.text = t.strInstructions

           youtubeLink = t.strYoutube.toString()
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
            mealId = intent.getStringExtra(MEAL_ID)!!
            mealName = intent.getStringExtra(MEAL_NAME)!!
            mealThumb = intent.getStringExtra(MEAL_THUMB)!!
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

