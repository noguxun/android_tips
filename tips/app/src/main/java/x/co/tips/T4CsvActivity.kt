package x.co.tips

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import kotlinx.android.synthetic.main.t4_activity_csv.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset


class T4CsvActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.t4_activity_csv)

        // https://stackoverflow.com/questions/1748977/making-textview-scrollable-on-android
        text_view_station_list.movementMethod = ScrollingMovementMethod()

        readStationData()

        checkLocationPermission()

        button_start_service.setOnClickListener {
            val serviceIntent = Intent(this, T4StationService::class.java)
            startService(serviceIntent)

            // for minSdk over 26 we need to use
            // startForegroundService(serviceIntent)
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
