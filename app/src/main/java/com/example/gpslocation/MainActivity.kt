package com.example.gpslocation

import android.app.Notification
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.app.NotificationManager



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        val notification = Notification()
//        notification.setLights(0xFF0000FF,100,3000);
//        nm.notify(LED_NOTIFICATION_ID, notification)

        configureTabLayout()
    }

    private fun configureTabLayout() {

        tabsMain.addTab(tabsMain.newTab().setText("Location"))
        tabsMain.addTab(tabsMain.newTab().setText("Temperature"))

        val adapter = PageAdapter(supportFragmentManager, tabsMain.tabCount)
        viewpagerMain.adapter = adapter
        viewpagerMain.addOnPageChangeListener( TabLayout.TabLayoutOnPageChangeListener(tabsMain))
        tabsMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpagerMain.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
    }
}