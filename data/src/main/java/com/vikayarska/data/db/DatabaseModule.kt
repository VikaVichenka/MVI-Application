package com.vikayarska.data.db

import android.content.Context
import androidx.room.Room
import com.vikayarska.data.db.user.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "Users.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(dataBase: AppDatabase): UserDao {
        return dataBase.userDao()
    }
}