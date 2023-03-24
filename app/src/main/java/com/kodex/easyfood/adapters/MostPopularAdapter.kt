package com.kodex.easyfood.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kodex.easyfood.databinding.PopularItemsBinding
import com.kodex.easyfood.pojo.CategoryList
import com.kodex.easyfood.pojo.CategoryMeals

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
        private var mealList = ArrayList<CategoryMeals>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(mutableList: ArrayList<CategoryMeals>){
        this.mealList = mealList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
       return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
        class PopularMealViewHolder( var binding: PopularItemsBinding ): RecyclerView.ViewHolder(binding.root)

}