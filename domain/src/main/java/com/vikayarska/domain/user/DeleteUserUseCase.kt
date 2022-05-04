package com.vikayarska.domain.user

import com.vikayarska.domain.model.UpdateResult

interface DeleteUserUseCase {
    suspend operator fun invoke(): UpdateResult
}