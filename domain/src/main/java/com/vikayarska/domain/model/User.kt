package com.vikayarska.domain.model

import java.util.*

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val birth: Date,
    val intro: String,
    val image: UserImage
) {
    data class UserImage(val imageId: String, val imageUrl: String)
}
