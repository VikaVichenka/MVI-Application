package com.vikayarska.data.viewstate

import com.vikayarska.data.model.AppUser

sealed class UserDetailsScreenState {
    object Loading : UserDetailsScreenState()
    object Empty : UserDetailsScreenState()
    object Editing : UserDetailsScreenState()
    data class Preview(val user: AppUser) : UserDetailsScreenState()
    data class Error(val message: String?) : UserDetailsScreenState()
}