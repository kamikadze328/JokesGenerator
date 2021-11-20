package com.kamikadze328.hedgehogtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kamikadze328.hedgehogtest.view.WebApiFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main) {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavBar()
    }

    private fun updateToolbar(title: String) {
        supportActionBar?.title = title
    }

    private fun setupNavBar() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _: NavController,
                                                        destination: NavDestination,
                                                        _: Bundle? ->
            destination.label?.let {
                updateToolbar(it.toString())
            }
        }
    }

    override fun onBackPressed() {
        //navController.currentDestination?.id == R.id.navigation_web_api
        val currFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
        if (currFragment is WebApiFragment && currFragment.onBackPressed()) return
        super.onBackPressed()
    }

}
