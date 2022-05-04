package com.vikayarska.data.interactor

import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.User
import com.vikayarska.domain.repository.UserRepository
import com.vikayarska.domain.user.GetUserUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : GetUserUseCase {

    override suspend fun getUserById(id: Int): BaseResult<User> =
        runCatching { userRepository.getUser(id) }.fold(
            onSuccess = {
                if (it == null) {
                    BaseResult.Empty()
                } else {
                    BaseResult.Success(it)
                }
            },
            onFailure = { exception ->
                BaseResult.Error(
                    exception.localizedMessage,
                    exception as? Exception
                )
            })
}