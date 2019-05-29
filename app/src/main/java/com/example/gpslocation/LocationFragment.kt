package com.example.gpslocation

import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class LocationFragment : Fragment(){
    private lateinit var longitude : TextView
    private lateinit var latitude : TextView
    private lateinit var updateButton: Button
    private lateinit var progressBar: ProgressBar

    private var PERMISSIONS_REQUEST_LOCATION = 0
    private var locationManager : LocationManager? = null

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            longitude.text = location.longitude.toString()
            latitude.text = location.latitude.toString()
            updateButton.isClickable = true
            progressBar.visibility = View.INVISIBLE
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

//    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//
//        }
//
//    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.location_fragment, container, false)

        longitude = view.findViewById(R.id.longitudeLabel)
        latitude = view.findViewById(R.id.latitudeLabel)
        updateButton = view.findViewById(R.id.updateButton)
        progressBar = view.findViewById(R.id.progressBar)

        // Create persistent LocationManager reference
        locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager?

        updateButton.setOnClickListener {
            getLocation()
        }

        getLocation()

        return view
    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            PERMISSIONS_REQUEST_LOCATION -> {
                if ((grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED))) {
                    locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
                }
            }
            else -> {permissionDeniedMessage()}

        }
    }

    private fun getLocation(){

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)

            progressBar.visibility = View.VISIBLE
            updateButton.isClickable = false
        }
        else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSIONS_REQUEST_LOCATION)
        }
    }

    private fun permissionDeniedMessage(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 11) }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
                requireActivity().finish()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

}