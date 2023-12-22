package ru.kpfu.itis.bagaviev.data.tuples

import androidx.room.ColumnInfo

data class UserInfoTuple(
    @ColumnInfo(name = "user_id")
    val userId: Int,
    val name: String,
    val phone: String,
    val email: String
)