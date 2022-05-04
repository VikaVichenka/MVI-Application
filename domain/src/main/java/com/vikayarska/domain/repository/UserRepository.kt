package com.vikayarska.domain.repository

import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.User

interface UserRepository {

    suspend fun getIntro(): String?

    suspend fun addUsers(userNames: List<String>)

    suspend fun getUserNames(): BaseResult<List<String>>

    suspend fun deleteUsers()

    suspend fun updateUser(user: User)

    suspend fun getUser(id: Int): User?
}