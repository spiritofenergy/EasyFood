package com.kodex.easyfood.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.kodex.easyfood.pojo.Meal

@Dao
interface MealDao {

         @Insert(onConflict = OnConflictStrategy.REPLACE)
           suspend fun upsert(meal: Meal){
             Log.d("check MealDao", "meal: \n $meal")
           }
            @Query("SELECT * FROM mealInformation")
            fun getAllMeals():LiveData<List<Meal>>


         @Update
          fun update(meal: Meal)

         @Delete
          fun delete(meal: Meal)





/*
       @Insert(onConflict = OnConflictStrategy.REPLACE)
       fun upsert(meal: Meal)
       @Delete
       fun delete(meal: Meal)



       @Insert
       fun insertFavorite(meal: Meal)

       @Update
       fun updateFavorite(meal: Meal)

       @Query("SELECT * FROM meal_Information order by idMeal asc")
       fun getAllSavedMeals(): LiveData<List<Meal>>

       @Query("SELECT * FROM meal_Information WHERE idMeal =:id")
       fun getMealById(id: String): Meal

       @Query("DELETE FROM meal_Information WHERE idMeal =:id")
       fun deleteMealById(id: String)

       @Delete
       fun deleteMeal(meal: Meal)
     @Insert
   fun insertFavorite(meal: Meal)

       @Query("SELECT * FROM meal_Information")
       fun getAllMeal():LiveData<List<Meal>>
   */

}




