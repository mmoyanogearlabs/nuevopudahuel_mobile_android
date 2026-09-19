package com.massiva.nuevopudahuel.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.PudahuelApplication;
import com.massiva.nuevopudahuel.constants.PudahuelPrefs;
import com.massiva.nuevopudahuel.controllers.UserController;
import com.massiva.nuevopudahuel.ui.activities.AlertActivity;
import com.massiva.nuevopudahuel.ui.activities.HomeActivity;
import com.massiva.nuevopudahuel.ui.activities.SplashActivity;

import java.util.Map;

public class FCMService extends FirebaseMessagingService {

    private static final String TAG_TOKEN = "FCM_TOKEN";
    private static final String TAG_DEBUG = "FCM_DEBUG";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG_TOKEN, "====================================================");
        Log.d(TAG_TOKEN, ">> NEW FCM TOKEN RECEIVED IN SERVICE:");
        Log.d(TAG_TOKEN, token);
        Log.d(TAG_TOKEN, "====================================================");

        try {
            if (getApplicationContext() instanceof PudahuelApplication) {
                PudahuelApplication app = (PudahuelApplication) getApplicationContext();
                app.storeString(PudahuelPrefs.PUSH_TOKEN, token);
                UserController.getInstance().registerPushToken(app);
            }
        } catch (Exception e) {
            Log.e(TAG_TOKEN, "Error saving new token in onNewToken", e);
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG_DEBUG, "====================================================");
        Log.d(TAG_DEBUG, ">> FCM MESSAGE RECEIVED!");
        Log.d(TAG_DEBUG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG_DEBUG, "Notification Title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG_DEBUG, "Notification Body: " + remoteMessage.getNotification().getBody());
        }
        Log.d(TAG_DEBUG, "Data Payload: " + remoteMessage.getData());
        Map<String, String> data = remoteMessage.getData();
        String flightId = null;
        if (data != null) {
            String[] candidateKeys = new String[]{
                    "npg_id", "npgId", "npg",
                    "flight_id", "flightId", "flight", "id",
                    "flight_code", "flightCode", "flight_number", "flightNumber",
                    "vuelo", "code"
            };
            for (String key : candidateKeys) {
                if (data.containsKey(key) && data.get(key) != null) {
                    String val = data.get(key).trim();
                    if (!val.isEmpty() && !val.equalsIgnoreCase("null")) {
                        flightId = val;
                        break;
                    }
                }
            }
        }

        String title = "";
        String message = "";

        if (remoteMessage.getNotification() != null) {
            if (remoteMessage.getNotification().getTitle() != null) {
                title = remoteMessage.getNotification().getTitle();
            }
            if (remoteMessage.getNotification().getBody() != null) {
                message = remoteMessage.getNotification().getBody();
            }
        }

        if (title.isEmpty() && data != null && data.containsKey("title")) {
            title = data.get("title");
        }
        if (message.isEmpty() && data != null && data.containsKey("message")) {
            message = data.get("message");
        }
        if (message.isEmpty() && data != null && data.containsKey("body")) {
            message = data.get("body");
        }
        if (title == null || title.isEmpty()) {
            title = getString(R.string.app_name);
        }

        if (flightId == null || flightId.isEmpty()) {
            flightId = parseFlightCodeFromText(title + " " + message);
        }

        Log.d(TAG_DEBUG, "Deep-link flight_id: " + flightId);
        Log.d(TAG_DEBUG, "====================================================");

        try {
            sendNotification(this, message, title, flightId);
        } catch (Exception e) {
            Log.e(TAG_DEBUG, "Error displaying notification", e);
        }
    }

    private String parseFlightCodeFromText(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        try {
            // Airline codes must contain at least one letter (e.g. LA, JA, AV, H2, 2A) and not plain digits
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\b([A-Za-z]{1,2}|[A-Za-z][0-9]|[0-9][A-Za-z])\\s?(\\d{2,4})\\b");
            java.util.regex.Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String code = matcher.group(0);
                Log.d(TAG_DEBUG, "Regex extracted flight code from text: " + code);
                return code;
            }
        } catch (Exception e) {
            Log.e(TAG_DEBUG, "Error parsing flight code regex", e);
        }
        return null;
    }

    public void sendNotification(Context context, String message, String title, String flightId) {
        Log.d(TAG_DEBUG, "=== Notification click payload summary ===");
        Log.d(TAG_DEBUG, "message=" + message);
        Log.d(TAG_DEBUG, "title=" + title);
        Log.d(TAG_DEBUG, "resolved flightId=" + flightId);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "nuevo_pudahuel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificacion";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, name, importance);
            notificationChannel.setDescription("Notificaciones Nuevo Pudahuel");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
        }

        int notificationId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);

        int flags;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE;
        } else {
            flags = PendingIntent.FLAG_UPDATE_CURRENT;
        }

        Intent intent;
        if (flightId != null && !flightId.isEmpty()) {
            intent = new Intent(context, AlertActivity.class);
            intent.putExtra("fromPush", true);
            intent.putExtra("npg_id", flightId);
            intent.putExtra("npgId", flightId);
            intent.putExtra("flight_id", flightId);
            intent.putExtra("flightId", flightId);
            intent.putExtra(AlertActivity.EXTRA_FLIGHT, flightId);
            Log.d(TAG_DEBUG, "Notification click will open AlertActivity directly for flight_id: " + flightId);
        } else {
            intent = new Intent(context, HomeActivity.class);
            intent.putExtra("fromPush", true);
            intent.putExtra("show_my_flights", true);
            Log.d(TAG_DEBUG, "Notification click will open HomeActivity (My Flights) because no flight id was resolved");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(context, notificationId, intent, flags);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.push_icon)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setColor(ContextCompat.getColor(context, R.color.blue))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message)
                .setContentIntent(contentIntent);

        if (mNotificationManager != null) {
            mNotificationManager.notify(notificationId, mBuilder.build());
        }
        Log.d(TAG_DEBUG, "Notification posted successfully with id: " + notificationId);
    }
}
