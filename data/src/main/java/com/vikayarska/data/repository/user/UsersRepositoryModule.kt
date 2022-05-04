package com.vikayarska.data.repository.user

import com.vikayarska.data.repository.user.repositoryimpl.ImageRepositoryImpl
import com.vikayarska.data.repository.user.repositoryimpl.UserRepositoryImpl
import com.vikayarska.domain.repository.ImageRepository
import com.vikayarska.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UsersRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository


    @Binds
    @Singleton
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository
}