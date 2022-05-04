package com.vikayarska.data.interactor

import com.vikayarska.data.db.user.dao.UserDao
import com.vikayarska.data.mapper.mapDBUser
import com.vikayarska.domain.model.User
import com.vikayarska.domain.user.GetUsersUseCase
import javax.inject.Inject

class GetUsersUseCaseImpl @Inject constructor(
    private val userDao: UserDao
) : GetUsersUseCase {
    override suspend fun invoke(): List<User> = userDao.getAll().map {
        mapDBUser(it)
    }
}