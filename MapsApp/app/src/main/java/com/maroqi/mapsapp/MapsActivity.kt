package com.maroqi.mapsapp

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.maroqi.mapsapp.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMapsBinding

  private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

  private lateinit var locationListener: LocationListener

  private var googleMap: GoogleMap? = null

  private var locationAction: () -> Unit = {}

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMapsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    requestPermissionLauncher =
      registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.forEach { (permit, granted) ->
          when (permit) {
            android.Manifest.permission.ACCESS_COARSE_LOCATION -> {
              if (!granted) {
                finish()
              } else {
                this.locationAction()
              }
            }
            android.Manifest.permission.ACCESS_FINE_LOCATION -> {
              if (!granted) {
                finish()
              } else {
                this.locationAction()
              }
            }
          }
        }
      }

    setupLocationMap()
    setupButtons()
  }

  override fun onStart() {
    getLocationPermissionFor {}
    super.onStart()
  }

  private fun getLocationPermissionFor(action: () -> Unit) {
    this.locationAction = action

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      val permissions = arrayListOf<String>()

      if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
      }
      if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
      }

      if (permissions.size > 0) {
        requestPermissionLauncher.launch(permissions.toTypedArray())
      }
    } else {
      this.locationAction()
    }
  }

  private fun setupLocationMap() {
    locationListener = LocationListener { loc ->
      setLocation(loc.latitude, loc.longitude)

      binding.mapsTiLatitude.editText?.setText(loc.latitude.toString())
      binding.mapsTiLongitude.editText?.setText(loc.longitude.toString())
    }

    val mapFragment = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync { googleMap ->
      this.googleMap = googleMap

      val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED
          && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED
        ) {
          locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            (0).toLong(),
            200f,
            locationListener
          )
        } else {
          getLocationPermissionFor {
            locationManager.requestLocationUpdates(
              LocationManager.GPS_PROVIDER,
              (0).toLong(),
              200f,
              locationListener
            )
          }
        }
      } else {
        locationManager.requestLocationUpdates(
          LocationManager.GPS_PROVIDER,
          (0).toLong(),
          200f,
          locationListener
        )
      }
    }
  }

  private fun setupButtons() {
    binding.mainBtnGo.setOnClickListener {
      var isValid = true

      if (!binding.mapsTiLatitude.editText?.text.isNullOrEmpty()) {
        isValid = false
      }
      if (!binding.mapsTiLongitude.editText?.text.isNullOrEmpty()) {
        isValid = false
      }

      if (isValid) {
        setLocation(
          binding.mapsTiLatitude.editText?.text.toString().toDouble(),
          binding.mapsTiLongitude.editText?.text.toString().toDouble()
        )
      }
    }
  }

  private fun setLocation(latitude: Double, longitude: Double) {
    val loc = LatLng(latitude, longitude)

    if (this.googleMap != null) {
      this.googleMap?.addMarker(MarkerOptions().position(loc).title("Lokasiku"))
      this.googleMap?.moveCamera(CameraUpdateFactory.newLatLng(loc))
    }
  }
}
