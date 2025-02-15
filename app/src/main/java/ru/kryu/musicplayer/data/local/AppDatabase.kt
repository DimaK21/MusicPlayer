package ru.kryu.musicplayer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DownloadedTrack::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun downloadsDao(): DownloadsDao
}