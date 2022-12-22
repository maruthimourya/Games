package com.maruthimourya.games.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.maruthimourya.R
import com.maruthimourya.databinding.ActivityContainerBinding

class ContainerActivity : AppCompatActivity() {

    private lateinit var containerBinding: ActivityContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        containerBinding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(containerBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.fragmentContainerView)
            .navigateUp() || super.onSupportNavigateUp()
    }

}