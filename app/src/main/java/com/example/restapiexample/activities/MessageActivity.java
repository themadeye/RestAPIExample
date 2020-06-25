package com.example.restapiexample.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restapiexample.R;
import com.example.restapiexample.sqlite.UserDBHelper;

public class MessageActivity extends AppCompatActivity {

    private Button profile;
    private Button message;
    private Button exit;

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String ACTION_UPDATE_NOTIFICATION = "com.example.myapplication.ACTION_UPDATE_NOTIFICATION";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;
    private NotificationReceiver mReceiver = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        findViews();
        createNotificationChannel();
        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 30Sec
                sendNotification();
            }
        }, 30000);
    }

    private void findViews(){
        profile = (Button)findViewById(R.id.profile);
        message = (Button)findViewById(R.id.message);
        exit = (Button)findViewById(R.id.exit);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDBHelper dbHelp = new UserDBHelper(MessageActivity.this);
                dbHelp.updateCredential("false");
                Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void sendNotification(){
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.addAction(R.drawable.ic_update, "Update Notification", updatePendingIntent);
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }



    public void createNotificationChannel(){
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager
                    .IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder(){
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                        .setContentTitle("You've been notified")
                        .setContentText("This is your notification test.")
                        .setSmallIcon(R.drawable.ic_android)
                        .setContentIntent(notificationPendingIntent)
                        .setAutoCancel(true)
                        .setSound(alarmSound);
        return notifyBuilder;
    }


    public class NotificationReceiver extends BroadcastReceiver {
        public NotificationReceiver(){

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            //update
        }
    }
}
