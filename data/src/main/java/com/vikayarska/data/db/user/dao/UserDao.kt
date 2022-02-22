package com.vikayarska.data.db.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vikayarska.data.db.dao.BaseDao
import com.vikayarska.data.db.user.entity.DbUser

@Dao
interface UserDao : BaseDao<DbUser> {

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<DbUser>

    @Query("SELECT * FROM users WHERE id LIKE :id")
    suspend fun getUserById(id: Int): DbUser?

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<DbUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuspend(obj: List<DbUser>)

    @Query("DELETE FROM users")
    fun deleteAll()
}