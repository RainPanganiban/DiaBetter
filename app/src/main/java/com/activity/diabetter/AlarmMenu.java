package com.activity.diabetter;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmMenu extends AppCompatActivity {

    EditText intervalInput;
    Spinner unitSpinner;
    Button setAlarmBtn, cancelAlarmBtn;

    public static long intervalMillis;

    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_menu);

        intervalInput = findViewById(R.id.intervalInput);
        unitSpinner = findViewById(R.id.unitSpinner);
        setAlarmBtn = findViewById(R.id.setAlarmBtn);
        cancelAlarmBtn = findViewById(R.id.cancelAlarmBtn);
        Button AlarmButtonBack = findViewById(R.id.AlarmButtonBack);

        AlarmButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StatisticsMenu.class));
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                new String[]{"Minutes", "Hours"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);

        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent exactIntent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(exactIntent);
            }
        }

        setAlarmBtn.setOnClickListener(v -> {
            String input = intervalInput.getText().toString();
            if (input.isEmpty()) {
                Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
                return;
            }

            int value = Integer.parseInt(input);
            String unit = unitSpinner.getSelectedItem().toString();

            switch (unit) {
                case "Minutes":
                    intervalMillis = value * 60L * 1000L;
                    break;
                case "Hours":
                    intervalMillis = value * 60L * 60L * 1000L;
                    break;
            }

            long triggerAt = System.currentTimeMillis() + intervalMillis;

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent);

            Toast.makeText(this, "Alarm set every " + value + " " + unit.toLowerCase(), Toast.LENGTH_SHORT).show();
        });

        cancelAlarmBtn.setOnClickListener(v -> {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm canceled.", Toast.LENGTH_SHORT).show();
        });
    }
}
