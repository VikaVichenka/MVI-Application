package com.vikayarska.domain.repository

import com.vikayarska.domain.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun addUsers()
    suspend fun deleteUsers()
    suspend fun getUserById(id: Int): User?
    suspend fun updateUser(user: User)
}