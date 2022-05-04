package com.vikayarska.data.interactor

import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.UpdateResult
import com.vikayarska.domain.repository.UserRepository
import com.vikayarska.domain.user.AddUsersUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : AddUsersUseCase {

    override suspend fun invoke() = runCatching { userRepository.getUserNames() }.fold(
        onSuccess = { result ->
            when (result) {
                is BaseResult.Success -> {
                    runCatching { userRepository.addUsers(result.data) }.fold(
                        onSuccess = { UpdateResult.Success },
                        onFailure = { UpdateResult.Error(it.localizedMessage, it as? Exception) }
                    )
                }
                is BaseResult.Error -> UpdateResult.Error(result.message, result.exception)
                is BaseResult.Empty -> UpdateResult.Empty
            }
        },
        onFailure = {
            UpdateResult.Error(it.localizedMessage, it as? Exception)
        })
}