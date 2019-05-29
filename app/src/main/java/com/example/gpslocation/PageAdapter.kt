package com.example.gpslocation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PageAdapter(fm: FragmentManager, private var tabCount:Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 ->  LocationFragment()
            1 ->  TemperatureFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

}