package x.co.tips

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.t4_activity_csv.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset


class T4CsvActivity : AppCompatActivity() {

    var stationService2: T4StationService2? = null

    private val service2Connection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            val binder = service as T4StationService2.MyLocalBinder
            stationService2 = binder.getService()

            println("Service2 connected")
            println("${stationService2?.getMyMagicNum()}")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            stationService2 = null
            println("Service2 disconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.t4_activity_csv)

        // https://stackoverflow.com/questions/1748977/making-textview-scrollable-on-android
        text_view_station_list.movementMethod = ScrollingMovementMethod()

        readStationData()

        checkLocationPermission()

        button_start_service1.setOnClickListener {
            val serviceIntent = Intent(this, T4StationService::class.java)
            startService(serviceIntent)
        }

        // example to bind service
        button_bind_service2.setOnClickListener {
            println("about to bind service2")
            val intent = Intent(this, T4StationService2::class.java)
            bindService(intent, service2Connection, Context.BIND_AUTO_CREATE)
        }

        button_get_loc_service2.setOnClickListener {
            // stationService2?.getLastLocation()
            // stationService2?.createLocationRequest()
            // stationService2?.createLocationRequest()
            createLocationRequest()
        }
    }

    @SuppressLint("MissingPermission")
    fun createLocationRequest() {
        /*
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    println("google location ${location.longitude} ${location.latitude}")
                }
            }
        }*/

        val locationRequest = LocationRequest().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }


        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())


        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
            println("settiong ok")
            stationService2?.createLocationRequest()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                println("settiong failure")
                // https://developer.android.com/training/location/change-location-settings
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(this@T4CsvActivity, 0x1)

                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }

            }
        }
    }

    private fun readStationData() {
        val csvStreamReader = resources.openRawResource(R.raw.station20180330free)

        val bufferReader = BufferedReader(InputStreamReader(csvStreamReader, Charset.forName("UTF-8")))

        var stationText = ""

        var count = 0

        val maxCount = 100

        while (true) {
            val line: String? = bufferReader.readLine() ?: break

            count += 1

            if (count == 1) {
                // skip the first line with title
                continue
            }

            val cols = line?.split(",")

            if (count < maxCount) {
                cols?.let {

                    val station = T4Station(cols[2].trim(), cols[9].toDouble(), cols[10].toDouble())
                    println("${cols[2].trim()} ${cols[9].trim()} ${cols[10].trim()}")
                    stationText += station.toString() + "\n"
                }
            } else {
                break
            }


        }

        text_view_station_list.text = stationText
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                println("Should Show Request Permisson Rationale")

            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0
                )

            }
        } else {
            println("permission already granted")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            println("granted")

        } else {
            println("Please allow the permission")
        }
    }
}
