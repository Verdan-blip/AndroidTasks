package ru.kpfu.itis.bagaviev.model.user.usecase

data class UserUpdatePhoneModel(
    val userId: Int,
    val phone: String
)