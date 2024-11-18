package com.example.mychattingapp.LocaldbLogics.HiltInjection

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mychattingapp.LocaldbLogics.DAO.RoomDbConnection.AppDatabase
import com.example.mychattingapp.LocaldbLogics.DAO.daoMethods.MessageDao
import com.example.mychattingapp.LocaldbLogics.DAO.daoMethods.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add or modify tables and columns here
        database.execSQL("ALTER TABLE messages ADD COLUMN reaction TEXT NOT NULL DEFAULT ''")
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).addMigrations(MIGRATION_2_3)
            .build()
    }

    @Provides
    fun provideMessageDao(database: AppDatabase): MessageDao {
        return database.messageDao()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}