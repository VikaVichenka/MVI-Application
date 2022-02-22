package com.vikayarska.domain.intents

import com.vikayarska.domain.model.User

sealed class UserDetailsIntent {
    data class FetchUser(val id: Int) : UserDetailsIntent()
    object EditUser : UserDetailsIntent()
    data class SaveUser(val user: User) : UserDetailsIntent()
}