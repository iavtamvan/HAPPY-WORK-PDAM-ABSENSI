package co.id.pdamkotasmg.pekerjaanteknik.utils.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pdamkotasmg.goodday.fitur.splash.SplashScreenActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import co.id.pdamkotasmg.pekerjaanteknik.R;
import com.pdamkotasmg.goodday.utils.Config;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "firebase";
    public static final String NOTIFICATION_CHANNEL_ID = "1002";

    private NotificationUtils notificationUtils;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }
        String getTitle = remoteMessage.getNotification().getTitle();
        String getMessage = remoteMessage.getNotification().getBody();
//        String getMessage= remoteMessage.getNotification().getBody();
//        Uri getImageURL = remoteMessage.getNotification().getImageUrl();
        Log.d(TAG, "remoteMessage: " + remoteMessage);
        Log.d(TAG, "getNotification: " + remoteMessage.getNotification());
        Log.d(TAG, "data: " + remoteMessage.getData());
        Log.d(TAG, "dataRaw: " + remoteMessage.getRawData());
        Log.d(TAG, "dataTitle: " + getTitle);
        Log.d(TAG, "dataBody: " + getMessage);

        if (NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            Log.d(TAG, "Masuk:  IF");
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.FIREBASE_PUSH_NOTIFICATION);
//            pushNotification.putExtra("title", getTitle);
            pushNotification.putExtra("message", getMessage);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
            showNotification(getApplicationContext(), getTitle, getMessage);
        } else {
            Log.d(TAG, "Masuk:  Else");
            // app is in background, show the notification in notification tray
            Intent resultIntent = new Intent(getApplicationContext(), SplashScreenActivity.class);
            resultIntent.putExtra("message", getMessage);

            // check for image attachment
//            if (TextUtils.isEmpty((CharSequence) getImageURL)) {
//                    showNotificationMessage(getApplicationContext(), "Biasa > " + title, message);
            showNotification(getApplicationContext(), getTitle, getMessage);
//            } else {
            // image is present, show notification with image
//                showNotificationMessageWithBigImage(getApplicationContext(), "Big > " + getTitle, getMessage, timestamp, resultIntent, imageUrl);
//            }
        }


//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
//
//            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
//                handleDataMessage(json);
//                Log.d(TAG, "onMessageReceived: " + json);
//            } catch (Exception e) {
//                Log.e(TAG, "Exception: " + e.getMessage());
//            }
//        } else {
//            Log.d(TAG, "onMessageReceived: failed " + remoteMessage.getData().size());
//            Log.d(TAG, "onMessageReceived: failed " + remoteMessage.getRawData());
//        }
    }

    private void handleNotification(String message) {
        if (NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.FIREBASE_PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.d("debug", "title: " + title);
            Log.d("debug", "message: " + message);
            Log.d("debug", "isBackground: " + isBackground);
            Log.d("debug", "payload: " + payload.toString());
            Log.d("debug", "imageUrl: " + imageUrl);
            Log.d("debug", "timestamp: " + timestamp);


            if (NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.FIREBASE_PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
//                    showNotificationMessage(getApplicationContext(), "Biasa > " + title, message);
                    showNotification(getApplicationContext(), title, message);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), "Big > " + title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    @RequiresApi(api = Build.VERSION_CODES.S)
    public void showNotificationMessage(Context context, String title, String message) {
        /**Creates an explicit intent for an Activity in your app**/
        Intent resultIntent = new Intent(context, SplashScreenActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String channelId = "notification_channel_112";

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_IMMUTABLE);

        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/" + R.raw.notif);
        mBuilder = new NotificationCompat.Builder(
                context.getApplicationContext(), channelId
        );
        mBuilder.setSmallIcon(com.pdamkotasmg.goodday.R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(alarmSound)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, Config.FIREBASE_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            notificationChannel.setSound(alarmSound, audioAttributes);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void showNotification(Context context, String title, String content) {
        int noificationId = new Random().nextInt(100);
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.notif);
        Log.d("debug", "showNotification: " + sound);
        String channelId = "notification_channel_3";
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context.getApplicationContext(), SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),
                0, intent, PendingIntent.FLAG_MUTABLE);

        // Create bubble metadata
        Notification.BubbleMetadata bubbleData =
                new Notification.BubbleMetadata.Builder(pendingIntent,
                        Icon.createWithResource(context, com.pdamkotasmg.goodday.R.drawable.ic_launcher_foreground))
                        .setDesiredHeight(600)
                        .build();

        // Create notification, referencing the sharing shortcut
        Notification.Builder builder =
                new Notification.Builder(context, channelId)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(com.pdamkotasmg.goodday.R.drawable.ic_launcher_foreground)
                        .setBubbleMetadata(bubbleData)
//                        .setShortcutId()
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(false)
                        .setSound(sound)
                        .setPriority(Notification.PRIORITY_MAX);

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(
//                context.getApplicationContext(), channelId
//        );
//
//        builder.setSmallIcon(com.pdamkotasmg.goodday.R.drawable.ic_launcher_foreground);
//        builder.setDefaults(NotificationCompat.PRIORITY_MAX);
//        builder.setContentTitle(title); // make suer change the channel for image
//        builder.setContentText(content);
//        builder.setSound(sound);
//        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
//
//        builder.setContentIntent(pendingIntent);
//        builder.setAutoCancel(true);
//        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.
                    getNotificationChannel(channelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        channelId, "Notification channel 2",
                        NotificationManager.IMPORTANCE_HIGH
                );
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();
                notificationChannel.setDescription(content);
                notificationChannel.enableVibration(true);
                notificationChannel.enableLights(true);
                notificationChannel.setSound(sound, attributes); // This is IMPORTANT
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(noificationId, notification);
        }
    }


    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

}
