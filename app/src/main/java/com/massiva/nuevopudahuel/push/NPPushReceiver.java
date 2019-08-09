package com.massiva.nuevopudahuel.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.bzutils.LogBZ;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.ui.activities.SplashActivity;
import com.innoquant.moca.push.GcmPushReceiver;

import java.util.Iterator;

/**
 * Created by iaguila on 24/3/16.
 */
public class NPPushReceiver extends GcmPushReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            Iterator<String> iterator = intent.getExtras().keySet().iterator();
            String body = null;
            String title = "";
            while (iterator.hasNext()) {
                String key = iterator.next();
                if ("gcm.notification.body".equals(key))
                    body = (String) intent.getExtras().get(key);
                if ("gcm.notification.title".equals(key))
                    title = (String) intent.getExtras().get(key);
                LogBZ.d("PushExtra_NPPushReceiver " + key + ": " + intent.getExtras().get(key));
            }
            if (body != null) {
                if (TextUtils.isEmpty(title))
                    title = context.getString(R.string.app_name);
               // sendNotification(context, body, title);
            }
        }
        super.onReceive(context, intent);

    }

    public static void sendNotification(Context context, String message, String title) {
        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId  = "nuevo_pudahuel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LogBZ.d("Using Android 0 version");
            CharSequence name ="Notificacion";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, name, importance);
            //channel configuration
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        PendingIntent contentIntent = PendingIntent.getActivity(context, 11,
                new Intent(context, SplashActivity.class).putExtra("fromPush", true), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, channelId )
                        .setSmallIcon(R.drawable.push_icon)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentText(message);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());

    }

}
