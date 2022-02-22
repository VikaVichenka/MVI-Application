package com.vikayarska.mvi.viewmodel

import androidx.lifecycle.*
import com.vikayarska.domain.intents.UserDetailsIntent
import com.vikayarska.domain.model.User
import com.vikayarska.domain.repository.UserRepository
import com.vikayarska.domain.viewstates.UserDetailsScreenState
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
    private val userRepository: UserRepository,
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

        _state.value = runCatching { userRepository.getUserById(userId) }.fold(
            onSuccess = { user ->
                if (user == null) {
                    UserDetailsScreenState.Empty
                } else {
                    UserDetailsScreenState.Preview(user)
                }
            }, onFailure = {
                UserDetailsScreenState.Error(it.localizedMessage)
            })
    }

    private fun updateUser(user: User) = viewModelScope.launch {
        _state.value = UserDetailsScreenState.Loading

        runCatching { userRepository.updateUser(user) }.fold(
            onSuccess = {
                _state.value = UserDetailsScreenState.Preview(user)
            }, onFailure = {
                _state.value = UserDetailsScreenState.Error(it.localizedMessage)
            })
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

