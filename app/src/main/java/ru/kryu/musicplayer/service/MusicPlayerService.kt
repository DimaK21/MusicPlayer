package ru.kryu.musicplayer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.support.v4.media.session.MediaSessionCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kryu.musicplayer.ui.player.MusicPlayerManager
import javax.inject.Inject

@AndroidEntryPoint
class MusicPlayerService : LifecycleService() {

    @Inject
    lateinit var musicPlayerManager: MusicPlayerManager

    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var notificationManager: MusicNotificationManager

    private val mediaSessionCallback = object : MediaSessionCompat.Callback() {
        override fun onPlay() {
            musicPlayerManager.togglePlayPause()
        }

        override fun onPause() {
            musicPlayerManager.togglePlayPause()
        }

        override fun onSkipToNext() {
            musicPlayerManager.playNextTrack()
        }

        override fun onSkipToPrevious() {
            musicPlayerManager.playPreviousTrack()
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        mediaSession = MediaSessionCompat(this, "MusicPlayerService").apply {
            setCallback(mediaSessionCallback)
            isActive = true
        }
        notificationManager = MusicNotificationManager(this, mediaSession, musicPlayerManager)

        lifecycleScope.launch {
            musicPlayerManager.currentTrack.collect { track ->
                track?.let {
                    notificationManager.updateNotification(
                        it,
                        musicPlayerManager.isPlaying.value
                    )
                }
            }
        }

        lifecycleScope.launch {
            musicPlayerManager.isPlaying.collect { isPlaying ->
                notificationManager.updatePlayPause(isPlaying)
            }
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            MusicNotificationManager.CHANNEL_ID,
            "Музыкальный плеер",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Уведомления управления плеером"
            setShowBadge(false)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        handleIntent(intent)
        musicPlayerManager.currentTrack.value?.let { track ->
            notificationManager.updateNotification(track, musicPlayerManager.isPlaying.value)
            startForeground(MusicNotificationManager.NOTIFICATION_ID,
                notificationManager.buildNotification(track, musicPlayerManager.isPlaying.value))
        }
        return START_STICKY
    }

    private fun handleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_PLAY_PAUSE -> musicPlayerManager.togglePlayPause()
            ACTION_NEXT -> musicPlayerManager.playNextTrack()
            ACTION_PREVIOUS -> musicPlayerManager.playPreviousTrack()
            ACTION_STOP -> stopSelf()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayerManager.release()
        mediaSession.release()
    }

    companion object {
        const val ACTION_PLAY_PAUSE = "ACTION_PLAY_PAUSE"
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREVIOUS = "ACTION_PREVIOUS"
        const val ACTION_STOP = "ACTION_STOP"
    }
}