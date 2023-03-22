package com.kodex.easyfood.fragments

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
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner, object :Observer<Meal>{
            override fun onChanged(t: Meal?) {
                Glide.with(this@HomeFragment)
                    .load(t!!.strMealThumb)
                    .into(binding.imgRandomMeal)
            }
        })
    }
}