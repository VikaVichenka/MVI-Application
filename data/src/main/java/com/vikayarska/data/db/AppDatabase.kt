package com.vikayarska.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vikayarska.data.db.user.dao.UserDao
import com.vikayarska.data.db.user.entity.DbUser

@Database(entities = [DbUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}