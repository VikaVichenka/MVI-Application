package com.vikayarska.domain.user

import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.User

interface GetUserUseCase {
    suspend fun getUserById(id: Int): BaseResult<User>
}