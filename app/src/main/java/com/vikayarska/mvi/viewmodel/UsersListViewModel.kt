package com.vikayarska.mvi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikayarska.data.mapper.mapDBUser
import com.vikayarska.data.mapper.mapUserToApplicationUser
import com.vikayarska.data.model.AppUser
import com.vikayarska.domain.intents.UserListIntent
import com.vikayarska.domain.model.User
import com.vikayarska.domain.user.UserUseCaseFacade
import com.vikayarska.domain.viewstates.BaseScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias UserList = List<AppUser>

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val userUseCase: UserUseCaseFacade
) : ViewModel() {

    private val userIntent = MutableStateFlow<UserListIntent>(UserListIntent.FetchUsers)

    private val _state = MutableLiveData<BaseScreenState<UserList>>(BaseScreenState.Empty())
    val state: LiveData<BaseScreenState<UserList>> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() = viewModelScope.launch {
        userIntent.collect { intent ->
            when (intent) {
                is UserListIntent.FetchUsers -> getUsers()
                is UserListIntent.AddUsers -> addUsers()
                is UserListIntent.DeleteUsers -> deleteUsers()
            }
        }
    }

    fun sendIntent(intent: UserListIntent) = viewModelScope.launch {
        userIntent.value = intent
    }


    private fun getUsers() = viewModelScope.launch {
        _state.value = BaseScreenState.Loading()

        _state.value = runCatching { userUseCase.getUsers() }.fold(
            onSuccess = { userList ->
                if (userList.isEmpty()) {
                    BaseScreenState.Empty()
                } else {
                    BaseScreenState.Loaded(userList.map { mapUserToApplicationUser(it) })
                }
            }, onFailure = {
                BaseScreenState.Error(it.localizedMessage)
            })
    }

    private fun addUsers() = viewModelScope.launch {
        performRequest { userUseCase.addUser() }
    }

    private fun deleteUsers() = viewModelScope.launch {
        performRequest { userUseCase.deleteUser() }
    }

    private suspend fun performRequest(request: suspend () -> Unit) {
        _state.value = BaseScreenState.Loading()

        runCatching { request.invoke() }.fold(
            onSuccess = {
                userIntent.value = UserListIntent.FetchUsers
            }, onFailure = {
                _state.value = BaseScreenState.Error(it.localizedMessage)
            })
    }

}
