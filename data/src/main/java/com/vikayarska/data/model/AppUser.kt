package com.vikayarska.data.model

import java.io.Serializable

data class AppUser(
    val id: Int = 0,
    val firstName: String = "Name",
    val lastName: String = "Surname",
    val intro: String = "",
    val imageUrl: String = ""
) : Serializable {
    fun fullName() = "$firstName $lastName"
}
