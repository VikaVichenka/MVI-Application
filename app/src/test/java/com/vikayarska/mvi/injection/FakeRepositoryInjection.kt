package com.vikayarska.mvi.injection

import com.vikayarska.data.repository.UsersRepositoryModule
import com.vikayarska.domain.model.User
import com.vikayarska.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Inject
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UsersRepositoryModule::class]
)
abstract class TestAppModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: FakeRepository
    ): UserRepository

}

class FakeRepository @Inject constructor() : UserRepository {
    private val userList: ArrayList<User> = ArrayList()

    private val testUser = User(id = 0, name = "Test User", intro = "Test Intro")
    override suspend fun getUsers(): List<User> {
        return userList
    }

    override suspend fun addUsers() {
        userList.add(
            testUser
        )
    }

    override suspend fun deleteUsers() {
        userList.clear()
    }

    override suspend fun getUserById(id: Int): User? {
        return testUser
    }

    override suspend fun updateUser(user: User) {
    }

}