package com.vikayarska.mvi.viewmodel

import androidx.lifecycle.*
import com.vikayarska.data.mapper.mapUserToApplicationUser
import com.vikayarska.data.viewstate.UserDetailsScreenState
import com.vikayarska.domain.intents.UserDetailsIntent
import com.vikayarska.domain.model.BaseResult
import com.vikayarska.domain.model.UpdateResult
import com.vikayarska.domain.model.User
import com.vikayarska.domain.user.UserUseCaseFacade
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserDetailsViewModel @AssistedInject constructor(
    private val userUseCaseFacade: UserUseCaseFacade,
    @Assisted val userId: Int
) : ViewModel() {

    private val userIntent =
        MutableStateFlow<UserDetailsIntent>(UserDetailsIntent.FetchUser(userId))

    private val _state = MutableLiveData<UserDetailsScreenState>(UserDetailsScreenState.Empty)
    val state: LiveData<UserDetailsScreenState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() = viewModelScope.launch {
        userIntent.collect { intent ->
            when (intent) {
                is UserDetailsIntent.FetchUser -> getUser(intent.id)
                is UserDetailsIntent.EditUser -> _state.value = UserDetailsScreenState.Editing
                is UserDetailsIntent.SaveUser -> updateUser(intent.user)
            }
        }
    }

    fun sendIntent(intent: UserDetailsIntent) = viewModelScope.launch {
        userIntent.value = intent
    }


    private fun getUser(userId: Int) = viewModelScope.launch {
        _state.value = UserDetailsScreenState.Loading

        _state.value = when (val user = userUseCaseFacade.getUser.getUserById(userId)) {
            is BaseResult.Success -> UserDetailsScreenState.Preview(mapUserToApplicationUser(user.data))
            is BaseResult.Empty -> UserDetailsScreenState.Empty
            is BaseResult.Error -> UserDetailsScreenState.Error(user.message)
        }
    }

    private fun updateUser(user: User) = viewModelScope.launch {
        _state.value = UserDetailsScreenState.Loading

        _state.value = when (val result = userUseCaseFacade.updateUser.updateUser(user)) {
            is UpdateResult.Success -> UserDetailsScreenState.Preview(mapUserToApplicationUser(user))
            is UpdateResult.Empty -> UserDetailsScreenState.Empty
            is UpdateResult.Error -> UserDetailsScreenState.Error(result.message)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(userId: Int): UserDetailsViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            userId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(userId) as T
            }
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AssistedInjectModule

