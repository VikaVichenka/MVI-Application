package com.vikayarska.data.repository.user.repositoryimpl

import com.vikayarska.data.datasource.RandomDataSource
import com.vikayarska.data.db.user.dao.UserDao
import com.vikayarska.data.mapper.UserMappersFacade
import com.vikayarska.data.network.getResponse
import com.vikayarska.data.network.model.LoremType
import com.vikayarska.data.network.model.NameType
import com.vikayarska.data.network.model.NetworkUser
import com.vikayarska.data.network.model.TextType
import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.User
import com.vikayarska.domain.repository.ImageRepository
import com.vikayarska.domain.repository.UserRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: RandomDataSource,
    private val imageRepository: ImageRepository,
    private val userDao: UserDao,
    private val userMappersFacade: UserMappersFacade
) : UserRepository {

    override suspend fun getIntro(): String? {
        return api.getIntro(
            LoremType.Normal,
            TextType.Words,
            INTRO_LENGTH
        ).body()
    }

    override suspend fun addUsers(userNames: List<String>) {
        return coroutineScope {
            val users = userNames.map { fullName ->
                val intro = getIntro()
                val image = when (val imageBase = imageRepository.getImage()) {
                    is BaseResult.Success -> imageBase.data
                    is BaseResult.Empty, is BaseResult.Error -> null
                }
                userMappersFacade.mapNetworkToDbUser(NetworkUser(fullName, intro, image))
            }
            userDao.insertSuspend(users)
        }
    }

    override suspend fun getUserNames(): BaseResult<List<String>> {
        return api.getFullNames(
            NameType.FullName,
            USER_QUANTITY
        ).getResponse()
    }

    override suspend fun deleteUsers() {
        userDao.deleteAll()
    }

    override suspend fun updateUser(user: User) {
        userDao.update(userMappersFacade.mapUserToDBUser(user))
    }

    override suspend fun getUser(id: Int): User? {
        return userDao.getUserById(id)?.let {
            userMappersFacade.mapDbToUser(it)
        }
    }

    companion object {
        const val USER_QUANTITY = 15
        const val INTRO_LENGTH = 20
    }
}