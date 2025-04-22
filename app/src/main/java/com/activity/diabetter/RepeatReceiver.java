package com.activity.diabetter;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.widget.Toast;

public class RepeatReceiver extends BroadcastReceiver {
    @SuppressLint("ScheduleExactAlarm")
    @Override
    public void onReceive(Context context, Intent intent) {
        // 1. Stop alarm sound if playing
        if (AlarmReceiver.player != null && AlarmReceiver.player.isPlaying()) {
            AlarmReceiver.player.stop();
            AlarmReceiver.player.release();
            AlarmReceiver.player = null;
        }

        // 2. Stop vibration
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.cancel();
        }

        // 3. Dismiss notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1001);

        // 4. Set new alarm
        long interval = AlarmMenu.intervalMillis;
        long triggerAt = System.currentTimeMillis() + interval;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent);

        // 5. Save new trigger time
        context.getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE)
                .edit()
                .putLong("triggerTime", triggerAt)
                .apply();

        // 6. Let user know
        Toast.makeText(context, "Alarm repeated. Restarting countdown...", Toast.LENGTH_SHORT).show();

        Intent updateUIIntent = new Intent("com.activity.diabetter.UPDATE_COUNTDOWN");
        context.sendBroadcast(updateUIIntent);

    }



}
