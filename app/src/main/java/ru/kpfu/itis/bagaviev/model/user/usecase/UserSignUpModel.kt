package ru.kpfu.itis.bagaviev.model.user.usecase

data class UserSignUpModel(
    val name: String,
    val phone: String,
    val email: String,
    val password: String
)