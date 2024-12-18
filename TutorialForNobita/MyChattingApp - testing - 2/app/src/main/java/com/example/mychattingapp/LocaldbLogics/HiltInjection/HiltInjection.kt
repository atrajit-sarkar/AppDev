package com.example.mychattingapp.LocaldbLogics.HiltInjection

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mychattingapp.LocaldbLogics.DAO.RoomDbConnection.AppDatabase
import com.example.mychattingapp.LocaldbLogics.DAO.daoMethods.MessageDao
import com.example.mychattingapp.LocaldbLogics.DAO.daoMethods.UserDao
import com.example.mychattingapp.LocaldbLogics.DAO.daoMethods.UserDocIdDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val MIGRATION_10_11 = object : Migration(11, 12) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add the `userDocId` column with a default value to the `user` table
//        database.execSQL("ALTER TABLE user ADD COLUMN userDocId TEXT NOT NULL DEFAULT ''")
        database.execSQL(
            """
        ALTER TABLE messages ADD COLUMN viewOnce TEXT NOT NULL DEFAULT ''
    """
        )

        database.execSQL(
            """
        CREATE TABLE IF NOT EXISTS user_doc_id (
            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            userdocid TEXT NOT NULL
        )
    """
        )

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
        ).addMigrations(MIGRATION_10_11)
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

    @Provides
    fun provideUserDocIdDao(database: AppDatabase): UserDocIdDao {
        return database.userDocIdDao()
    }

}