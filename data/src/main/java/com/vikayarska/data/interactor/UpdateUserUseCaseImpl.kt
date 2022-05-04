package com.vikayarska.data.interactor

import com.vikayarska.domain.model.UpdateResult
import com.vikayarska.domain.model.User
import com.vikayarska.domain.repository.UserRepository
import com.vikayarska.domain.user.UpdateUserUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : UpdateUserUseCase {
    override suspend fun updateUser(user: User): UpdateResult =
        runCatching { userRepository.updateUser(user) }.fold(
            onSuccess = { UpdateResult.Success },
            onFailure = { exception ->
                UpdateResult.Error(
                    exception.localizedMessage,
                    exception as? Exception
                )
            })
}