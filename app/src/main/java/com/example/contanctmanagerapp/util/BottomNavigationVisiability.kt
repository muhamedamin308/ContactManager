package com.example.contanctmanagerapp.util


import androidx.fragment.app.Fragment
import com.example.contanctmanagerapp.MainActivity
import com.example.contanctmanagerapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.invisibleNavigation() {
    val navigationView =
        (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
    navigationView.gone()
}

fun Fragment.visibleNavigation() {
    val navigationView =
        (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
    navigationView.show()
}