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
import androidx.recyclerview.widget.GridLayoutManager

import com.kodex.easyfood.R
import com.kodex.easyfood.activites.CategoryMealsActivity
import com.kodex.easyfood.activites.MainActivity
import com.kodex.easyfood.adapters.CategoriesAdapter
import com.kodex.easyfood.adapters.CategoryMealsAdapter
import com.kodex.easyfood.databinding.FragmentCategoryBinding
import com.kodex.easyfood.utils.Constants
import com.kodex.easyfood.viewmodel.CategoryMealsViewModel
import com.kodex.easyfood.viewmodel.HomeViewModel

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoriesAdapter:CategoriesAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        observerCategories()

        onCategoryClick()

    }
    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(Constants.CATEGORY_NAME, category.strCategory)
            Log.d("check", "Category.strCategory: \n $category.strCategory")
            startActivity(intent)
        }
    }

    private fun observerCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply{
             layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

}