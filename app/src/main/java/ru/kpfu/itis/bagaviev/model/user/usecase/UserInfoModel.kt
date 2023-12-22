package ru.kpfu.itis.bagaviev.model.user.usecase

import ru.kpfu.itis.bagaviev.data.tuples.UserInfoTuple

data class UserInfoModel(
    val userId: Int,
    val name: String,
    val phone: String,
    val email: String
)

fun UserInfoModel.toUserInfoTuple() =
    UserInfoTuple(userId = userId, name = name, phone = phone, email = email)

fun UserInfoTuple.toUserInfoModel() =
    UserInfoModel(userId = userId, name = name, phone = phone, email = email)