package com.cleverox.nuevopudahuel.push;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.bzutils.LogBZ;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by Adrian on 17/5/15.
 */
public class GcmIntentService extends IntentService {

    public GcmIntentService() {

        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);
        try {
            if (!extras.isEmpty()) {

                if (GoogleCloudMessaging.
                        MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                    LogBZ.d("PUSH SEND ERROR");
                } else if (GoogleCloudMessaging.
                        MESSAGE_TYPE_DELETED.equals(messageType)) {
                    LogBZ.d("PUSH TYPE DELETED");
                } else if (GoogleCloudMessaging.
                        MESSAGE_TYPE_MESSAGE.equals(messageType)) {

//                    if (extras.getString(Constants.EXTRA_MESSAGE).contains(Constants.PUSH_IDMATCH)) {
//                        JSONObject jsonPush = null;
//                        int idMatch = -99;
//                        String message = "";
//                        try {
//                            jsonPush = new JSONObject(extras.getString(Constants.EXTRA_MESSAGE));
//                            idMatch = jsonPush.getInt(Constants.PUSH_IDMATCH);
//                            message = jsonPush.getString(Constants.PUSH_PUSHMESSAGE);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        ((GetaFinderApplication) getApplicationContext()).refreshMatchesAndMessages(new RequestDone() {
//                            @Override
//                            public void onRequestDone() {
//                                if (UtilsPushData.getInstance().getListChatActivity() != null) {
//                                    ((ListChatActivity) UtilsPushData.getInstance().getListChatActivity()).refresh();
//                                    LogBZ.d("REFRESH MATCHES & CHAT");
//                                }
//                            }
//                        });
//
//                        if (jsonPush != null && UtilsPushData.getInstance().getMatchChat() == idMatch &&
//                                UtilsPushData.getInstance().getChatActivity() != null && UtilsPushData.getInstance().isOpenChatScreen()) {
//                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                            v.vibrate(400);
//                            LogBZ.d("REFRESH CHAT");
//                                    ((ChatMessagesActivity) UtilsPushData.getInstance().getChatActivity()).refreshChat();
//                        } else {
//                            sendNotification(message);
//                            LogBZ.d("NOTIFICATION!!");
//                        }
//                    } else {
//                        sendNotification(extras.getString(Constants.EXTRA_MESSAGE));
//                        LogBZ.d("NOTIFICATION2!!");
//                    }

                }
            }

            NPPushReceiver.completeWakefulIntent(intent);
        }catch (Exception e){
            LogBZ.printStackTrace(e);
        }
    }

    private void sendNotification(String message) {
//        NotificationManager mNotificationManager = (NotificationManager)
//                this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, ListChatActivity.class)
//                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra(
//                        Constants.EXTRA_MESSAGE, message), 0);
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setAutoCancel(true)
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
//                                R.mipmap.ic_launcher))
//                        .setDefaults(Notification.DEFAULT_ALL)
//                        .setContentTitle("Getafinder")
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                        .setContentText(message)
//                        .setColor(getResources().getColor(R.color.bluedarkapp));
//
//        mBuilder.setContentIntent(contentIntent);
//        mNotificationManager.notify(Constants.NOTIFICATION, mBuilder.build());


    }
}