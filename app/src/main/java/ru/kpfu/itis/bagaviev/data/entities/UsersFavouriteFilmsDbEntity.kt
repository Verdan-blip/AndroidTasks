package ru.kpfu.itis.bagaviev.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users_favourite_films",
    primaryKeys = ["user_id", "film_id"]
)
data class UsersFavouriteFilmsDbEntity(

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "film_id")
    val filmId: Int

)