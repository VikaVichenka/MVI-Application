package com.vikayarska.domain.intents

sealed class UserListIntent {
    object FetchUsers : UserListIntent()
    object DeleteUsers : UserListIntent()
    object AddUsers : UserListIntent()
}