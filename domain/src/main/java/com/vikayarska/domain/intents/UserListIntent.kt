package com.vikayarska.domain.intents

import com.vikayarska.domain.model.User

sealed class UserListIntent {
    object FetchUsers : UserListIntent()
    object DeleteUsers : UserListIntent()
    object AddUsers : UserListIntent()
    data class DeleteUser(val user: User) : UserListIntent()
}