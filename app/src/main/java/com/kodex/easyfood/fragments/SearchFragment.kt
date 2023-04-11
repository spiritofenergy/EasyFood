package com.kodex.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kodex.easyfood.activites.MainActivity
import com.kodex.easyfood.adapters.MealsAdapter
import com.kodex.easyfood.databinding.FragmentSearchBinding
import com.kodex.easyfood.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRecyclerViewAdapter: MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        binding.imgSearchArrow.setOnClickListener {searchMeals() }

        observeSearchedMealsLiveData()

        var searchJob: Job? = null
        binding.edSearchBox.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                // Вызываем модель просмотра
                delay(500)
                viewModel.searchMeals(searchJob.toString())

            }

        }
    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchedMealsLiveData()
            .observe(viewLifecycleOwner, Observer { mealList ->
            searchRecyclerViewAdapter.differ.submitList(mealList)
        })
    }

    private fun searchMeals(){
        val searchQuery = binding.edSearchBox.text.toString()
        if (searchQuery.isEmpty()){
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
       searchRecyclerViewAdapter = MealsAdapter()
        binding.rvSearchMeals .apply {
            layoutManager = GridLayoutManager(context,
                2,GridLayoutManager.VERTICAL,false  )
            adapter = searchRecyclerViewAdapter
        }
    }


}