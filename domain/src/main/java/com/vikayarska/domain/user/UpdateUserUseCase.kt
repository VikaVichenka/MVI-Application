package com.vikayarska.domain.user

import com.vikayarska.domain.model.UpdateResult
import com.vikayarska.domain.model.User

interface UpdateUserUseCase {
    suspend fun updateUser(user: User): UpdateResult
}