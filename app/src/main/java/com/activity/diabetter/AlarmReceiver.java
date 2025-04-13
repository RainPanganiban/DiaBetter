package com.activity.diabetter;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    public static MediaPlayer player;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (player == null) {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            player = MediaPlayer.create(context, alarmSound);
            player.setLooping(true);
            player.start();


            new Handler().postDelayed(() -> {
                if (player != null && player.isPlaying()) {
                    player.stop();
                    player.release();
                    player = null;
                }
            }, 15000);
        }

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            long[] pattern = {0, 3000, 2000};
            int[] amplitudes = {0, 255, 0};

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(android.os.VibrationEffect.createWaveform(pattern, amplitudes, 0));
            } else {
                vibrator.vibrate(pattern, 0);
            }


            new Handler().postDelayed(() -> {
                vibrator.cancel();
            }, 15000);
        }


        Intent repeatIntent = new Intent(context, RepeatReceiver.class);
        PendingIntent repeatPendingIntent = PendingIntent.getBroadcast(
                context, 1001, repeatIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent stopIntent = new Intent(context, StopReceiver.class);
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(
                context, 1002, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "alarm_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Alarm Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }


        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.ON_AFTER_RELEASE, "myApp:alarmWakeLock");
        wakeLock.acquire(3000);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle("⏰ Alarm Ringing!")
                .setContentText("Do you want to repeat or stop?")
                .setAutoCancel(true)
                .addAction(0, "Repeat", repeatPendingIntent)
                .addAction(0, "Stop", stopPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(1001, builder.build());



    }
}
