package ru.kpfu.itis.bagaviev.model.user.usecase

data class UserUpdatePasswordModel(
    val userId: Int,
    val password: String
)