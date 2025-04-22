package com.activity.diabetter;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.os.CountDownTimer;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class AlarmMenu extends AppCompatActivity {

    EditText intervalInput;
    Spinner unitSpinner;
    Button cancelAlarmBtn;
    TextView countdownText;
    CountDownTimer countDownTimer;

    ImageButton setAlarmBtn, AlarmButtonBack;


    public static long intervalMillis;

    PendingIntent pendingIntent;

    private final BroadcastReceiver countdownUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long triggerTime = getSharedPreferences("AlarmPrefs", MODE_PRIVATE)
                    .getLong("triggerTime", 0);
            long timeRemaining = triggerTime - System.currentTimeMillis();

            if (countDownTimer != null) countDownTimer.cancel();

            countDownTimer = new CountDownTimer(timeRemaining, 1000) {
                public void onTick(long millisUntilFinished) {
                    long totalSeconds = millisUntilFinished / 1000;
                    long hours = totalSeconds / 3600;
                    long minutes = (totalSeconds % 3600) / 60;
                    long seconds = totalSeconds % 60;
                    countdownText.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                }

                public void onFinish() {
                    countdownText.setText("Alarm ringing!");
                }
            }.start();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_menu);

        intervalInput = findViewById(R.id.intervalInput);
        unitSpinner = findViewById(R.id.unitSpinner);
        setAlarmBtn = findViewById(R.id.setAlarmBtn);
        cancelAlarmBtn = findViewById(R.id.cancelAlarmBtn);
       AlarmButtonBack = findViewById(R.id.AlarmButtonBack);
        countdownText = findViewById(R.id.countdownText);




        AlarmButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
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

            getSharedPreferences("AlarmPrefs", MODE_PRIVATE)
                    .edit()
                    .putLong("triggerTime", triggerAt)
                    .apply();

            Toast.makeText(this, "Alarm set every " + value + " " + unit.toLowerCase(), Toast.LENGTH_SHORT).show();

            // Cancel old timer if running
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            // Start countdown
            countDownTimer = new CountDownTimer(intervalMillis, 1000) {
                public void onTick(long millisUntilFinished) {
                    long totalSeconds = millisUntilFinished / 1000;
                    long hours = totalSeconds / 3600;
                    long minutes = (totalSeconds % 3600) / 60;
                    long seconds = totalSeconds % 60;
                    countdownText.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                }

                public void onFinish() {
                    countdownText.setText("Alarm ringing!");
                }
            }.start();
        });


        cancelAlarmBtn.setOnClickListener(v -> {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

            if (countDownTimer != null) {
                countDownTimer.cancel();
                countdownText.setText("");

            }

            getSharedPreferences("AlarmPrefs", MODE_PRIVATE)
                    .edit()
                    .remove("triggerTime")
                    .apply();

            Toast.makeText(this, "Alarm canceled.", Toast.LENGTH_SHORT).show();

        });

        long savedTriggerTime = getSharedPreferences("AlarmPrefs", MODE_PRIVATE)
                .getLong("triggerTime", -1);


        if (savedTriggerTime > System.currentTimeMillis()) {
            long remainingTime = savedTriggerTime - System.currentTimeMillis();
            startCountdown(remainingTime);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        long triggerTime = getSharedPreferences("AlarmPrefs", MODE_PRIVATE)
                .getLong("triggerTime", 0);

        long currentTime = System.currentTimeMillis();
        long timeRemaining = triggerTime - currentTime;

        if (timeRemaining > 0) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            countDownTimer = new CountDownTimer(timeRemaining, 1000) {
                public void onTick(long millisUntilFinished) {
                    long totalSeconds = millisUntilFinished / 1000;
                    long hours = totalSeconds / 3600;
                    long minutes = (totalSeconds % 3600) / 60;
                    long seconds = totalSeconds % 60;
                    countdownText.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                }

                public void onFinish() {
                    countdownText.setText("Alarm ringing!");
                }
            }.start();
        } else {
            countdownText.setText("");
        }

        registerReceiver(countdownUpdateReceiver, new IntentFilter("com.activity.diabetter.UPDATE_COUNTDOWN"));
    }






    private void startCountdown(long millis) {
        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(millis, 1000) {
            public void onTick(long millisUntilFinished) {
                long totalSeconds = millisUntilFinished / 1000;
                long hours = totalSeconds / 3600;
                long minutes = (totalSeconds % 3600) / 60;
                long seconds = totalSeconds % 60;
                countdownText.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            }

            public void onFinish() {
                countdownText.setText("Alarm ringing!");
            }
        }.start();
    }



    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(countdownUpdateReceiver);
    }


}
