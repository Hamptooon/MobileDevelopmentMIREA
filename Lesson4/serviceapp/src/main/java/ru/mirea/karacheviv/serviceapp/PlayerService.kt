package ru.mirea.karacheviv.serviceapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class PlayerService : Service() {
    private lateinit var mediaPlayer : MediaPlayer
    var CHANNEL_ID = "ForegroundServiceChannel"
    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented");
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            stopForeground(true)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentText("It's My Life")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("best player..."))
            .setContentTitle("Bon Jovi")
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(CHANNEL_ID, "Karachev I.V Notification", importance)
        notificationChannel.description = "MIREA CHANNEL"
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(notificationChannel)
        startForeground(1,  notificationBuilder.build())

        mediaPlayer = MediaPlayer.create(this, R.raw.audio_its_my_life)
    }

    override fun onDestroy() {
        stopForeground(true)
        mediaPlayer.stop()
    }
}
