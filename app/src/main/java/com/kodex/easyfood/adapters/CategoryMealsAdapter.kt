package com.kodex.easyfood.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kodex.easyfood.databinding.ActivityCategoryMealsMainBinding
import com.kodex.easyfood.databinding.MealItemBinding
import com.kodex.easyfood.pojo.Category
import com.kodex.easyfood.pojo.MealList
import com.kodex.easyfood.pojo.MealsByCategory

class CategoryMealsAdapter:RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>() {
        private var mealsList = ArrayList<MealsByCategory>()
    var onItemClick : ((Category) -> Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun  setMealsList(mealsList:List<MealsByCategory>){
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }
    inner class CategoryMealsViewModel(var binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
            return CategoryMealsViewModel(
                MealItemBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
    }

    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealsList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }



}

