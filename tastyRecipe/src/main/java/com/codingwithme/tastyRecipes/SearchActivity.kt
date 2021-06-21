package com.codingwithme.tastyRecipes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ba.unsa.pmf.math.myapplication.tastyRecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_second.*

class SearchActivity : AppCompatActivity() {
//    lateinit var dugme: Button
    lateinit var slika:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val navController= findNavController(R.id.fragment2)
        bottomNavigationView.setupWithNavController(navController)
        val appBarConfiguration= AppBarConfiguration(
            setOf(
                R.id.prviFragment,
                R.id.drugiFragment,
                R.id.treciFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController=findNavController(R.id.drugiFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    fun logujSe(view:View){
        val intent = Intent(this@SearchActivity, HomeActivity::class.java)
        startActivity(intent)
    }
}