package com.creatorflow.ai.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.creatorflow.ai.data.model.*

@Database(
    entities = [
        ContentIdea::class,
        ContentScript::class,
        ContentCaption::class,
        ThumbnailConcept::class,
        CalendarPost::class,
        AnalyticsRecord::class,
        ChatMessage::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ideaDao(): IdeaDao
    abstract fun scriptDao(): ScriptDao
    abstract fun captionDao(): CaptionDao
    abstract fun thumbnailDao(): ThumbnailDao
    abstract fun calendarDao(): CalendarDao
    abstract fun analyticsDao(): AnalyticsDao
    abstract fun chatDao(): ChatDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "creatorflow_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
