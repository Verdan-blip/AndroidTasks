package ru.kpfu.itis.bagaviev.model.user

data class UserModel(
    val userId: Int,
    val name: String,
    val phone: String,
    val email: String,
    val password: String,
    val token: String
)