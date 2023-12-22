package ru.kpfu.itis.bagaviev.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "film_ratings",
    indices = [Index("user_id", "film_id", unique = true)]
)
data class FilmRatingsDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "rating_id")
    val ratingId: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "film_id")
    val filmId: Int,
    val rating: Int
)
