package mm.com.fairwaytech.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Random;
import mm.com.fairwaytech.MessageBoxActivity;
import mm.com.fairwaytech.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final String ADMIN_CHANNEL_ID = "admin_channel";
    private int notiId = new Random().nextInt(3000);

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        //
//        if (remoteMessage.getData().size() > 0){
//            Toast.makeText(this, "onMessageReceived", Toast.LENGTH_SHORT).show();
//            return;
//        }else {
//            Toast.makeText(this, "Not MessageReceived", Toast.LENGTH_SHORT).show();
//        }
//
//        if (remoteMessage.getNotification() != null){
//            Toast.makeText(this, "Message Notification Body: " + remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT).show();
//            return;
//        }

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);


        /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them.
      */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            setupChannel(notificationManager);
        }

        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");

        Intent intent = new Intent(this, MessageBoxActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putString("MESSAGE", message);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this,ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.fairway)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("message"))
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        // Set notification color to match your app color template
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            notiBuilder.setColor(getResources().getColor(R.color.colorPrimary));
        }

        notificationManager.notify(notiId, notiBuilder.build());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannel(NotificationManager notificationManager){

        CharSequence adminChannelName = "New notification";
        String adminChannelDescription = "Device to Device notification";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null){
            notificationManager.createNotificationChannel(adminChannel);
        }

    }

}
