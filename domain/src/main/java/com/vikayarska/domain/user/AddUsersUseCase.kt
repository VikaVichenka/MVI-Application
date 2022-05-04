package com.vikayarska.domain.user

import com.vikayarska.domain.model.UpdateResult

interface AddUsersUseCase {
    suspend operator fun invoke(): UpdateResult
}