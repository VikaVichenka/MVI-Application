package com.vikayarska.data.mapper

import com.vikayarska.data.db.user.entity.DbUser
import com.vikayarska.data.model.AppUser
import com.vikayarska.data.network.model.NetworkUser
import com.vikayarska.domain.model.User
import dagger.Provides
import java.util.*
import javax.inject.Inject


fun mapDBUser(user: DbUser): User {
    return User(
        id = user.id,
        firstName = user.firstName,
        lastName = user.lastName,
        birth = user.birth,
        intro = user.intro,
        image = User.UserImage(user.imageId, user.imageUrl)
    )
}

fun mapDBUserToAppUser(user: DbUser): AppUser {
    return AppUser(
        id = user.id,
        firstName = user.firstName,
        lastName = user.lastName,
        intro = user.intro,
        imageUrl = user.imageUrl
    )
}

fun mapUserToDBUser(user: User): DbUser {
    return DbUser(
        id = user.id,
        firstName = user.firstName,
        lastName = user.lastName,
        birth = user.birth,
        intro = user.intro,
        imageId = user.image.imageId,
        imageUrl = user.image.imageUrl
    )
}


fun mapUserToApplicationUser(user: User): AppUser {
    return AppUser(
        id = user.id,
        firstName = user.firstName,
        lastName = user.lastName,
        intro = user.intro,
        imageUrl = user.image.imageUrl
    )
}


fun mapNetworkUserToDBUser(user: NetworkUser): DbUser {
    return DbUser(
        firstName = user.fullName.orEmpty().substringBefore(" "),
        lastName = user.fullName.orEmpty().substringAfter(" "),
        birth = Calendar.getInstance().generateRandomDate(),
        intro = user.intro.orEmpty(),
        imageId = user.image?.id ?: "",
        imageUrl = user.image?.url ?: ""
    )
}


private fun Calendar.generateRandomDate(): Date {
    val previousYear = Calendar.getInstance().get(Calendar.YEAR) - 1
    this.set((1950..previousYear).random(), (1..12).random(), (1..28).random())

    return this.time
}


class UserMappersFacade constructor(
    val mapDbToUser: (DbUser) -> User,
    val mapNetworkToDbUser: (NetworkUser) -> DbUser,
    val mapUserToDBUser: (User) -> DbUser
)
