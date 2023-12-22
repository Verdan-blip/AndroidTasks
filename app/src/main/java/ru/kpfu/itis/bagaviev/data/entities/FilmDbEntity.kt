package ru.kpfu.itis.bagaviev.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "films",
    indices = [
        Index("title", "release_date", unique = true)
    ]
)
data class FilmDbEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "film_id")
    val filmId: Int,

    val title: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: Long,

    @ColumnInfo(name = "short_description")
    val shortDescription: String,
)
