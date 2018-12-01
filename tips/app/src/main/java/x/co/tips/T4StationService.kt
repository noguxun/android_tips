package x.co.tips

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import android.support.v4.app.NotificationCompat


class T4StationService : Service() {

    var mLocSer: LocationService? = null
    var mHandler: MyHandler? = null

    inner class MyHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            mLocSer?.getLocation()
        }
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
        println("onBind is called T4 Service, return NULL")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("service onStartCommand")

        println("Build.VERSION.SDK_INT is ${Build.VERSION.SDK_INT}")
        println("Build.VERSION_CODES.O ${Build.VERSION_CODES.O}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startServiceAbove26()
            return START_STICKY
        } else {

            startServiceBelow26()
            return START_STICKY
        }
    }

    override fun onCreate() {
        super.onCreate()

        println("service onCreate")
        mHandler = MyHandler(Looper.getMainLooper())
        mLocSer = LocationService()
        mLocSer?.prepare()

    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun startServiceAbove26() {
        val channelId = "x.co.tips.t4"
        val channelName = "My Background Service"
        val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(chan)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("App is running in background")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        //Thread(MyRunnable()).start()
        mLocSer?.getLocation()


        startForeground(2, notification)
    }

    private fun startServiceBelow26() {
        val notification = NotificationCompat.Builder(this).apply {
            setContentTitle("T4Station Notification Title")
            setContentText("T4Station Test Service")
            setSmallIcon(R.mipmap.ic_launcher)
        }.build()

        // Thread(MyRunnable()).start()
        mLocSer?.getLocation()

        println("Start Fore Ground")
        startForeground(1, notification)
    }

    // inner is required to access outter class method
    inner class MyRunnable : Runnable {

        override fun run() {
            val message = mHandler?.obtainMessage(1)
            message?.sendToTarget()

            (0..15).map {
                println("about to sleep")
                Thread.sleep(1000)
            }

            println("stopForeground")
            stopForeground(true)
        }
    }

    inner class LocationService : LocationListener {

        private var mLocationManager: LocationManager? = null

        fun prepare() {
            if (mLocationManager == null) {
                mLocationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            }
        }


        fun getLocation() {
            // getting GPS status
            val isGpsEnabled = mLocationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false

            // getting network status
            val isNetworkEnabled = mLocationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false

            if (!isGpsEnabled && !isNetworkEnabled) {
                println("Location GPS or Network are not available")
                return
            }

            val minTime = 1000 * 2L
            val minDistance = 10.0f

            if (isNetworkEnabled) {
                try {
                    mLocationManager?.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        minTime,
                        minDistance,
                        this
                    )
                    val location = mLocationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    println("Network RequestLocation Last Known,  ${location?.latitude} ${location?.longitude}")
                } catch (e: SecurityException) {
                    println("Security Failed")
                }
            } else {
                println("Network Location not available")
            }

            if (isGpsEnabled) {
                try {
                    mLocationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this)
                    val location = mLocationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    println("GPS RequestLocation Last Known ${location?.latitude} ${location?.longitude}")
                } catch (e: SecurityException) {
                    println("Security Failed")
                }
            }
        }

        override fun onLocationChanged(location: Location?) {
            try {
                //var location = mLocationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                println("in getLocation Network ${location?.latitude} ${location?.longitude}")

                //
                println("in getLocation GPS ${location?.latitude} ${location?.longitude}")
            } catch (e: SecurityException) {
                println("Security Failed")
            }
        }

        override fun onProviderDisabled(provider: String?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

    }

}
