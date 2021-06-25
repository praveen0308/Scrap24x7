package com.jmm.brsap.scrap24x7.ui.customer.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.ActivityAddNewAddressBinding
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AddNewAddress : AppCompatActivity(), OnMapReadyCallback,
    ApplicationToolbar.ApplicationToolbarListener {

    private lateinit var binding:ActivityAddNewAddressBinding
    private lateinit var map: GoogleMap
//    private lateinit var currentLocationMarker:Marker
    private lateinit var currentSelectedMarker:Marker
    private lateinit var selectedAddress: Address

    private val LOCATION_PERMISSION_REQUEST = 1

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.toolbarNewAddress.setApplicationToolbarListener(this)
        binding.btnContinue.setOnClickListener {
            val addNewAddressSheet = AddNewAddressSheet()
            val bundle = Bundle()
            bundle.putParcelable("LAT_LNG", LatLng(currentSelectedMarker.position.latitude,currentSelectedMarker.position.longitude))
            addNewAddressSheet.arguments = bundle
            addNewAddressSheet.show(supportFragmentManager, addNewAddressSheet.tag)
        }

    }


    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true

            startLocationUpdates()
        }
        else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
    }



    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {


            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            val latLng = LatLng(it.latitude, it.longitude)
            val markerOptions = MarkerOptions().position(latLng)
            currentSelectedMarker = map.addMarker(markerOptions)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

            getAddressDetail(latLng)
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                map.isMyLocationEnabled = true
            }
            else {
                Toast.makeText(
                    this,
                    "User has not granted location access permission",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }



    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
        map.setOnMapClickListener { point ->
            if (currentSelectedMarker!=null){
                currentSelectedMarker.remove()
            }
//            currentLocationMarker.remove()
            val marker = MarkerOptions()
                .position(LatLng(point.latitude, point.longitude))
                .title("Pickup Address")
            currentSelectedMarker = map.addMarker(marker)

            getAddressDetail(point)

//            println(point.latitude.toString() + "---" + point.longitude)
        }

        getLocationAccess()
    }

    private fun getAddressDetail(latLng: LatLng){
        val gcd = Geocoder(
            baseContext,
            Locale.getDefault()
        )
        val addresses: List<Address>
        try {
            addresses = gcd.getFromLocation(
                latLng.latitude,
                latLng.longitude, 1
            )
            if (addresses.isNotEmpty()) {
                selectedAddress = addresses[0]
//
//                val address: String =
//                    addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                val locality: String = addresses[0].locality
//
//                val state: String = addresses[0].adminArea
//                val country: String = addresses[0].countryName
//                val postalCode: String = addresses[0].postalCode
//                val knownName: String = addresses[0].featureName
//
////                if (addresses[0].getSubLocality()!=null){
////                    val subLocality:String = addresses[0].getSubLocality()
////                    Log.d("CurrentLocation : ",subLocality.toString())
////                }else{
////                    Log.d("CurrentSubLocality","null")
////                }
////                Log.d("CurrentLocation : ",address)
////                Log.d("CurrentLocation : ",locality)
////
////
////
////                Log.d("CurrentLocation : ",state)
////                Log.d("CurrentLocation : ",country)
////                Log.d("CurrentLocation : ",postalCode)
////                Log.d("CurrentLocation : ",knownName)

            }
        } catch (e: Exception) {
            e.printStackTrace()

            Toast.makeText(
                this@AddNewAddress,
                e.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onToolbarNavClick() {
        finish()
    }

    override fun onMenuClick() {

    }

}