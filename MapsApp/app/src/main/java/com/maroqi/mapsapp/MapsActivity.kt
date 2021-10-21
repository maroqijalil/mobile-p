package com.maroqi.mapsapp

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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

  private lateinit var focusedLocationClient: FusedLocationProviderClient

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

    focusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED
        && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED
      ) {
        focusedLocationClient.lastLocation
          .addOnSuccessListener { location : Location? ->
            // Got last known location. In some rare situations this can be null.
          }
      } else {
        getLocationPermissionFor {
          focusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
              // Got last known location. In some rare situations this can be null.
            }
        }
      }
    } else {
      focusedLocationClient.lastLocation
        .addOnSuccessListener { location : Location? ->
          // Got last known location. In some rare situations this can be null.
        }
    }

    setupLocationMap()
    setupButtons()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.main_normal -> {
        googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
      }
      R.id.main_hybird -> {
        googleMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
      }
      R.id.main_sattelit -> {
        googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
      }
      R.id.main_terrain -> {
        googleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
      }
      R.id.main_none -> {
        googleMap?.mapType = GoogleMap.MAP_TYPE_NONE
      }
    }
    return super.onOptionsItemSelected(item)
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

      if (binding.mapsTiLatitude.editText?.text.isNullOrEmpty()) {
        isValid = false
      }
      if (binding.mapsTiLongitude.editText?.text.isNullOrEmpty()) {
        isValid = false
      }

      if (isValid) {
        if (binding.mapsTiZoom.editText?.text.isNullOrEmpty()) {
          setLocation(
            binding.mapsTiLatitude.editText?.text.toString().toDouble(),
            binding.mapsTiLongitude.editText?.text.toString().toDouble()
          )
        } else {
          setLocation(
            binding.mapsTiLatitude.editText?.text.toString().toDouble(),
            binding.mapsTiLongitude.editText?.text.toString().toDouble(),
            binding.mapsTiZoom.editText?.text.toString().toFloat()
          )
        }
      }

      (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
        it.windowToken,
        0
      )
    }
  }

  private fun setLocation(latitude: Double, longitude: Double, zoom: Float = 8f) {
    val loc = LatLng(latitude, longitude)

    if (this.googleMap != null) {
      this.googleMap?.addMarker(MarkerOptions().position(loc).title("Lokasi Pilihan"))
      this.googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom))
    }
  }
}
