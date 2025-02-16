package ru.kryu.musicplayer.service

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import ru.kryu.musicplayer.R
import ru.kryu.musicplayer.domain.model.Track
import ru.kryu.musicplayer.ui.player.MusicPlayerManager

class MusicNotificationManager(
    private val service: Service,
    private val mediaSession: MediaSessionCompat,
    private val musicPlayerManager: MusicPlayerManager
) {
    private val notificationManager = NotificationManagerCompat.from(service)

    fun updateNotification(track: Track, isPlaying: Boolean) {
        val notification = buildNotification(track, isPlaying)
        service.startForeground(NOTIFICATION_ID, notification)

    }

    fun updatePlayPause(isPlaying: Boolean) {
        val track = musicPlayerManager.currentTrack.value
        track?.let {
            val notification = buildNotification(it, isPlaying)
            if (ContextCompat.checkSelfPermission(
                    service,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notificationManager.notify(NOTIFICATION_ID, notification)
            }
        }
    }

    fun buildNotification(track: Track, isPlaying: Boolean): Notification {
        val playPauseAction = if (isPlaying) {
            NotificationCompat.Action(
                android.R.drawable.ic_media_pause,
                "Pause",
                getPendingIntent(MusicPlayerService.ACTION_PLAY_PAUSE)
            )
        } else {
            NotificationCompat.Action(
                android.R.drawable.ic_media_play,
                "Play",
                getPendingIntent(MusicPlayerService.ACTION_PLAY_PAUSE)
            )
        }

        return NotificationCompat.Builder(service, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle(track.title)
            .setContentText(track.artist)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    service.resources,
                    R.drawable.placeholder_album
                )
            )
            .setContentIntent(getPendingIntent(MusicPlayerService.ACTION_STOP))
            .addAction(
                NotificationCompat.Action(
                    android.R.drawable.ic_media_previous,
                    "Previous",
                    getPendingIntent(MusicPlayerService.ACTION_PREVIOUS)
                )
            )
            .addAction(playPauseAction)
            .addAction(
                NotificationCompat.Action(
                    android.R.drawable.ic_media_next,
                    "Next",
                    getPendingIntent(MusicPlayerService.ACTION_NEXT)
                )
            )
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOnlyAlertOnce(true)
            .build()
    }

    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(service, MusicPlayerService::class.java).setAction(action)
        return PendingIntent.getService(service, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    companion object {
        const val CHANNEL_ID = "MUSIC_PLAYER_CHANNEL"
        const val NOTIFICATION_ID = 1
    }
}
