package x.co.tips

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

// https://developer.android.com/training/location/receive-location-updates
class T4StationService2 : Service() {

    private val myBinder = MyLocalBinder()
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onBind(intent: Intent): IBinder {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        return myBinder
    }

    inner class MyLocalBinder : Binder() {
        fun getService(): T4StationService2 {
            return this@T4StationService2
        }
    }

    fun getMyMagicNum(): Int {
        return 1234567
    }

    /**
     *
     * https://developers.google.com/android/guides/setup?hl=es
     * https://developer.android.com/training/location/retrieve-current
     *
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     *
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                println("location ${location?.latitude} ${location?.longitude}")
            }
    }

}
