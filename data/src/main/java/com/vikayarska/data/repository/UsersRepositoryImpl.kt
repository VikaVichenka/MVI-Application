package com.vikayarska.data.repository

import com.vikayarska.data.db.user.dao.UserDao
import com.vikayarska.data.db.user.entity.DbUser
import com.vikayarska.domain.model.User
import com.vikayarska.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

private const val MOCK_INTRO =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam mollis ligula sit amet sapien ornare facilisis. Integer vel cursus sem. Proin porta justo vitae egestas pulvinar. Quisque et euismod risus. Aenean nec purus enim. Nunc enim erat, laoreet sit amet mi at, molestie fermentum erat. Morbi fermentum, libero id ornare condimentum, leo ipsum varius nisi, vel vulputate massa ex eget elit."

@Singleton
class UsersRepositoryImpl @Inject constructor(private val userDao: UserDao) : UserRepository {

    override suspend fun getUsers(): List<User> = withContext(Dispatchers.IO) {
        userDao.getAll().map { it.toUser() }
    }

    override suspend fun addUsers() = withContext(Dispatchers.IO) {
        userDao.insertSuspend(
            listOf(
                DbUser(
                    name = "Anwen Curry",
                    intro = MOCK_INTRO,
                    imageUrl = "https://cdn2.thecatapi.com/images/cdi.jpg"
                ),
                DbUser(
                    name = "Haley Ware",
                    intro = MOCK_INTRO,
                    imageUrl = "https://cdn2.thecatapi.com/images/MTg0MTA4OA.jpg"
                ),
                DbUser(
                    name = "Ronan Healy",
                    intro = MOCK_INTRO,
                    imageUrl = "https://cdn2.thecatapi.com/images/e9e.jpg"
                ),
                DbUser(
                    name = "Rodney Mccullough",
                    intro = MOCK_INTRO,
                    imageUrl = "https://cdn2.thecatapi.com/images/MTkzNjg0Ng.jpg"
                ),
                DbUser(
                    name = "Peyton Hinton",
                    intro = MOCK_INTRO,
                    imageUrl = "https://cdn2.thecatapi.com/images/c2t.jpg"
                ),
                DbUser(
                    name = "Johnathon Pham",
                    intro = MOCK_INTRO,
                    imageUrl = "https://cdn2.thecatapi.com/images/cin.gif"
                ),
                DbUser(
                    name = "Shelbie Avery",
                    intro = MOCK_INTRO,
                    imageUrl = "https://cdn2.thecatapi.com/images/VsdKVsL_S.jpg"
                ),
                DbUser(
                    name = "Glenda Hensley",
                    intro = MOCK_INTRO,
                    imageUrl = "https://cdn2.thecatapi.com/images/ck5pPEKh-.png"
                ),
                DbUser(
                    name = "Diane Hurst",
                    intro = MOCK_INTRO,
                    imageUrl = "https://cdn2.thecatapi.com/images/6gh.jpg"
                ),
                DbUser(
                    name = "Jardel Guzman",
                    intro = MOCK_INTRO,
                    imageUrl = "https://cdn2.thecatapi.com/images/_rsG8aC-T.jpg"
                )
            )
        )
    }

    override suspend fun deleteUsers() = withContext(Dispatchers.IO) {
        userDao.deleteAll()
    }

    override suspend fun getUserById(id: Int) = withContext(Dispatchers.IO) {
        userDao.getUserById(id)?.toUser()
    }

    override suspend fun deleteUser(user: User) = withContext(Dispatchers.IO) {
        userDao.delete(user.toDbUser())
    }

    override suspend fun updateUser(user: User) = withContext(Dispatchers.IO) {
        userDao.update(user.toDbUser())
    }
}

private fun DbUser.toUser() = User(id, name, intro, imageUrl)
private fun User.toDbUser() = DbUser(id, name, intro, imageUrl)