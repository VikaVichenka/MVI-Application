package com.vikayarska.data.mapper

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserMapperModule {

    @Provides
    @Singleton
    fun provideUserUserMapper(): UserMappersFacade {
        return UserMappersFacade(::mapDBUser, ::mapNetworkUserToDBUser, ::mapUserToDBUser)
    }
}