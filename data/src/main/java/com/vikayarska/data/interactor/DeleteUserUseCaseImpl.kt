package com.vikayarska.data.interactor

import com.vikayarska.domain.model.UpdateResult
import com.vikayarska.domain.repository.UserRepository
import com.vikayarska.domain.user.DeleteUserUseCase
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : DeleteUserUseCase {
    override suspend fun invoke() =
        runCatching { userRepository.deleteUsers() }.fold(
            onSuccess = { UpdateResult.Success },
            onFailure = { exception ->
                UpdateResult.Error(
                    exception.localizedMessage,
                    exception as? Exception
                )
            })
}