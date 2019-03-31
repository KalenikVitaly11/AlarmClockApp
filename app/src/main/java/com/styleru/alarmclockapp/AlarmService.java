package com.styleru.alarmclockapp;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmService extends IntentService {

    private static final String HOURS_KEY = "hours";
    private static final String MINUTES_KEY = "minutes";

    public AlarmService() {
        super("Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d("myLogs", "123");
        String CHANNEL_ID = "my_channel_01";

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(false);
            manager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentText("Будильник")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra(HOURS_KEY) + ":" + intent.getStringExtra(MINUTES_KEY));

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }

    public static Intent newIntent(Context context, int hours, int minutes) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(HOURS_KEY, String.valueOf(hours));
        intent.putExtra(MINUTES_KEY, String.valueOf(minutes));
        return intent;
    }
}
