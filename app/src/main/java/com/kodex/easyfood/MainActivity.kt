package com.kodex.easyfood

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kodex.easyfood.R
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kodex.easyfood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


         val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
         val navController = Navigation.findNavController(this, R.id.frag_host)
         NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}