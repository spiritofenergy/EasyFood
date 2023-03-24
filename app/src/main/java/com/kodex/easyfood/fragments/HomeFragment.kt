package com.kodex.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.kodex.easyfood.R
import com.kodex.easyfood.activites.MealActivity
import com.kodex.easyfood.databinding.FragmentHomeBinding
import com.kodex.easyfood.pojo.Meal
import com.kodex.easyfood.pojo.MealList
import com.kodex.easyfood.retrofit.RetrofitInstance
import com.kodex.easyfood.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("UNREACHABLE_CODE")
class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    companion object{
        const val MEAL_ID = "com.kodex.easyfood.fragments.idMeal"
        const val MEAL_NAME = "com.kodex.easyfood.fragments.nameMeal"
        const val MEAL_THUMB = "com.kodex.easyfood.fragments.thumbMeal"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

       override fun onViewCreated(view: View , savedInstanceState: Bundle?){
          super.onViewCreated(view, savedInstanceState)

           homeMvvm.getRandomMeal()
           observerRandomMeal()
           onRandomMealClick()
    }

    private fun onRandomMealClick() {
        binding.randomMealCart.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }
}