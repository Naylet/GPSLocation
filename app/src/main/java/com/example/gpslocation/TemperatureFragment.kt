package com.example.gpslocation

import android.content.Context
import android.hardware.SensorManager
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.os.BatteryManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.IntentFilter
import java.lang.Thread.sleep


class TemperatureFragment : Fragment(){

    private lateinit var updateButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var temperatureText: TextView

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if(Intent.ACTION_BATTERY_CHANGED == intent.action){
                val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0).toFloat() / 10
                sleep(500)
                temperatureText.text = temperature.toString() + " " + 0x00B0.toChar() + "C"
                progressBar.visibility = View.INVISIBLE
                updateButton.isEnabled = true
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.temperature_fragment, container, false)


        updateButton = view.findViewById(R.id.updateButton)
        progressBar = view.findViewById(R.id.progressBar)
        temperatureText = view.findViewById(R.id.temperatureText)

        // Create persistent LocationManager reference

        updateButton.setOnClickListener {
            getTemperature()
        }

        getTemperature()

        return view
    }


    private fun getTemperature(){
        updateButton.isEnabled = false
        progressBar.visibility = View.VISIBLE

        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        requireContext().registerReceiver(broadcastReceiver, intentFilter)
    }

}