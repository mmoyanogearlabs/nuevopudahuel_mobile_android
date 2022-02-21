package com.massiva.nuevopudahuel.push

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.util.Log
import com.bzutils.LogBZ
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.massiva.nuevopudahuel.R
import com.massiva.nuevopudahuel.ui.activities.SplashActivity

class FCMService : FirebaseMessagingService() {

    val TAG = "Service"
    private var count = 0

            /*
    override fun onNewToken(p0: String?) {

    }
*/
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage?.from)
                Log.d(TAG, "Notification Message Title: " + remoteMessage?.notification?.title)
                Log.d(TAG, "Notification Message Body: " + remoteMessage?.notification?.body)
        Log.d(TAG, "Notification Message Data: " + remoteMessage?.data)

        val extraData = remoteMessage?.data

                val title = remoteMessage?.notification?.title ?: remoteMessage?.data?.get("title") ?: ""
                val message = remoteMessage?.notification?.body ?: remoteMessage?.data?.get("message") ?: ""

                try {
                    sendNotification(this, message, title)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

/*
        //Here notification is received from server
        if (!shown) {
            try {
                sendNotification(remoteMessage.data.get("title") ?: "", remoteMessage.data.get("message") ?: "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
*/
    }

    fun sendNotification(context: Context, message: String, title: String) {
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "nuevo_pudahuel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LogBZ.d("Using Android 0 version")
            val name = "Notificacion"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(channelId, name, importance)
            //channel configuration
            notificationChannel.enableLights(true)
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }

        val contentIntent = PendingIntent.getActivity(context, 11,
                Intent(context, SplashActivity::class.java).putExtra("fromPush", true), 0)

        val mBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.push_icon)
                //.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setColor(getResources().getColor(R.color.blue))
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message)

        mBuilder.setContentIntent(contentIntent)
        mNotificationManager.notify(System.currentTimeMillis().toInt(), mBuilder.build())

    }

}
