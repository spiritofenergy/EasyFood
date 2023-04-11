package com.kodex.easyfood.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kodex.easyfood.databinding.MealItemBinding
import com.kodex.easyfood.pojo.Category
import com.kodex.easyfood.pojo.Meal
import com.kodex.easyfood.pojo.MealsByCategory
import com.kodex.easyfood.pojo.MealsByCategoryList
import com.kodex.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealsAdapter: RecyclerView.Adapter<MealsAdapter.FavoritesMealAdapterViewModel>() {
    var onItemClick : ((Category) -> Unit)? = null
    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()


    inner class FavoritesMealAdapterViewModel(val binding: MealItemBinding): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoritesMealAdapterViewModel {
        return FavoritesMealAdapterViewModel(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesMealAdapterViewModel, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = meal.strMeal
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
    fun observeMealsLiveData(): LiveData<List<MealsByCategory>> {
        return mealsLiveData
    }
    fun getMealsByCategory(categoryName: String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :
            Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>,
            ) {
                response.body()?.let {mealsList ->
                    mealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("check onFailure CategoryMealsViewModel", t.message.toString())

            }
        })
    }
}