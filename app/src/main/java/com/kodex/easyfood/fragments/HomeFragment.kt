package com.kodex.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kodex.easyfood.R
import com.kodex.easyfood.activites.CategoryMealsActivity
import com.kodex.easyfood.activites.MainActivity
import com.kodex.easyfood.activites.MealActivity
import com.kodex.easyfood.adapters.CategoriesAdapter
import com.kodex.easyfood.adapters.MostPopularAdapter
import com.kodex.easyfood.databinding.FragmentHomeBinding
import com.kodex.easyfood.fragments.bottomsheet.MealBottomSheetFragment
import com.kodex.easyfood.pojo.MealsByCategory
import com.kodex.easyfood.pojo.Meal
import com.kodex.easyfood.utils.Constants.CATEGORY_NAME
import com.kodex.easyfood.utils.Constants.MEAL_ID
import com.kodex.easyfood.utils.Constants.MEAL_NAME
import com.kodex.easyfood.utils.Constants.MEAL_THUMB
import com.kodex.easyfood.viewmodel.HomeViewModel


@Suppress("UNREACHABLE_CODE")
class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        popularItemsAdapter = MostPopularAdapter()
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

           preparePopularItemsRecyclerView()

           viewModel.getRandomMeal()
           observerRandomMeal()
           onRandomMealClick()

           viewModel.getPopularItems()
           observePopularItemLiveData()
           onPopularItemClick()

           viewModel.getCategories()
           observeCategoriesLiveData()

           prepareCategoriesRecyclerView()
           onCategoryClick()

           onPopularItemLongClick()

           onSearchItemClick()

    }

    private fun onSearchItemClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {
        popularItemsAdapter.onLongItemClick = {meal ->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager, "Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recyclerView.apply{
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
                categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
         binding.recViewMealsPopular.apply {
             layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
             adapter = popularItemsAdapter
         }
    }

    private fun observePopularItemLiveData() {
        viewModel.observePopularItemLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)

        }
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
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }
}

