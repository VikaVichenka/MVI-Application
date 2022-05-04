package com.vikayarska.data.interactor

import com.vikayarska.domain.user.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UsersUseCaseFacadeModule {

    @Provides
    @Singleton
    fun provideUserUseCaseFacade(
        addUser: AddUsersUseCase, deleteUser: DeleteUserUseCase, getUsers: GetUsersUseCase,
        updateUser: UpdateUserUseCase, getUser: GetUserUseCase
    ): UserUseCaseFacade {
        return UserUseCaseFacade(addUser, deleteUser, getUsers, updateUser, getUser)
    }
}