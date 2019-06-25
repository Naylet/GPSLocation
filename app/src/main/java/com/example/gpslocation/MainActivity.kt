package com.example.gpslocation

import android.app.Notification
import android.app.NotificationChannel
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Build
import android.support.annotation.RequiresApi


class MainActivity : AppCompatActivity() {

    private var notificationManager: NotificationManager? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

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

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(title:String, content: String, context: Context) {

        val channel = NotificationChannel("com.example.gpslocation.news", "GPS Location", NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.lightColor=Color.RED
        notificationManager?.createNotificationChannel(channel)

        val resultIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //val icon: Icon = Icon.createWithResource(this, android.R.drawable.ic_dialog_info)
        //val action: Notification.Action = Notification.Action.Builder(icon, "Open", pendingIntent).build()

        val notificationID = 101

        val notification = Notification.Builder(context, channel.id)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setChannelId(channel.id)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager?.notify(notificationID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStop() {
        super.onStop()
        showNotification("GPS Location", "Application is running in background", this@MainActivity)
    }
}