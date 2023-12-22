package ru.kpfu.itis.bagaviev.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index("email", unique = true),
        Index("phone", unique = true),
        Index("token", unique = true)
    ]
)
data class UserDbEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int,

    val name: String,

    val phone: String,

    val email: String,

    val password: String,

    val token: String

)
