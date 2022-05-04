package com.vikayarska.data.interactor

import com.vikayarska.domain.user.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UsersUseCaseModule {

    @Binds
    @Singleton
    abstract fun bindAddUseCase(
        addUserUseCaseImpl: AddUserUseCaseImpl
    ): AddUsersUseCase


    @Binds
    @Singleton
    abstract fun bindDeleteUseCase(
        deleteUserUseCaseImpl: DeleteUserUseCaseImpl
    ): DeleteUserUseCase

    @Binds
    @Singleton
    abstract fun bindGetUsersUseCase(
        getUsersUseCaseImpl: GetUsersUseCaseImpl
    ): GetUsersUseCase

    @Binds
    @Singleton
    abstract fun bindGetUserUseCase(
        getUserUseCaseImpl: GetUserUseCaseImpl
    ): GetUserUseCase

    @Binds
    @Singleton
    abstract fun bindUpdateUserUseCase(
        updateUserUseCaseImpl: UpdateUserUseCaseImpl
    ): UpdateUserUseCase

}