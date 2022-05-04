package com.vikayarska.domain.user

class UserUseCaseFacade(
    val addUser: AddUsersUseCase,
    val deleteUser: DeleteUserUseCase,
    val getUsers: GetUsersUseCase,
    val updateUser: UpdateUserUseCase,
    val getUser: GetUserUseCase
)