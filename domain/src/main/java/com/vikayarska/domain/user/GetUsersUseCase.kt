package com.vikayarska.domain.user

import com.vikayarska.domain.model.User

interface GetUsersUseCase {
    suspend operator fun invoke(): List<User>
}