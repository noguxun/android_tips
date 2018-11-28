package x.co.tips

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat


class T4StationService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
        println("onBind is called T4 Service, return NULL")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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


        Thread(
            Runnable {
                (0..100).map {
                    println("about to sleep in above 26")
                    Thread.sleep(1000)
                }

                println("stopForeground")
                stopForeground(true)
                // もしくは
                // stopSelf()

            }).start()

        startForeground(2, notification)
    }

    private fun startServiceBelow26() {
        val notification = NotificationCompat.Builder(this).apply {
            setContentTitle("T4Station Notification Title")
            setContentText("T4Station Test Service")
            setSmallIcon(R.mipmap.ic_launcher)
        }.build()

        Thread(
            Runnable {
                (0..100).map {
                    println("about to sleep in below 26")
                    Thread.sleep(1000)
                }

                println("stopForeground")
                stopForeground(true)
                // もしくは
                // stopSelf()

            }).start()


        println("Start Fore Ground")
        startForeground(1, notification)
    }
}
