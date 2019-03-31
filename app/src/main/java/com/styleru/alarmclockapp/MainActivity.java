package com.styleru.alarmclockapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    private Button addAlarm;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        addAlarm = findViewById(R.id.add_alarm);
        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        addAlarm.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Calendar time = Calendar.getInstance();
                time.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                time.set(Calendar.MINUTE, timePicker.getMinute());

                manager.setExact(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(),
                        PendingIntent.getService(this, (int) System.currentTimeMillis(), AlarmService.newIntent(this, timePicker.getHour(), timePicker.getMinute()), 0));

                timePicker.setHour(0);
                timePicker.setMinute(0);
            }
        });
    }
}
