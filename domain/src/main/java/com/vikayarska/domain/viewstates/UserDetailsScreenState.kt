package com.vikayarska.domain.viewstates

import com.vikayarska.domain.model.User

sealed class UserDetailsScreenState {
    object Loading : UserDetailsScreenState()
    object Empty : UserDetailsScreenState()
    object Editing : UserDetailsScreenState()
    data class Preview(val user: User) : UserDetailsScreenState()
    data class Error(val message: String?) : UserDetailsScreenState()
}