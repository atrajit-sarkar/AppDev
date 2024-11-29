package com.example.mycalculator.Data.HomeScreenData.hiltinjection

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mycalculator.Data.GroupScreenData.GroupDao
import com.example.mycalculator.Data.HomeScreenData.AppDataBase.AppDatabase
import com.example.mycalculator.Data.HomeScreenData.ContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add a new column named 'groupId' to the 'DummyContacts' table with default value -1
        database.execSQL(
            "ALTER TABLE `DummyContacts` ADD COLUMN `groupId` INTEGER NOT NULL DEFAULT -1"
        )

        // Ensure any additional setup (like indexes or constraints) happens here if needed
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
    fun provideContactDao(database: AppDatabase): ContactDao {
        return database.ContactDao()
    }

    @Provides
    fun provideGroupDao(database: AppDatabase): GroupDao {
        return database.GroupDao()
    }
}
