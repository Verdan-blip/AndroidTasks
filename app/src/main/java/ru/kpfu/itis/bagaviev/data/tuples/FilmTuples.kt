package ru.kpfu.itis.bagaviev.data.tuples

import androidx.room.ColumnInfo

data class InsertFilmTuple(
    val title: String,
    @ColumnInfo(name = "release_date") val releaseDate: Long,
    @ColumnInfo(name = "short_description") val shortDescription: String
)

data class FavouriteFilmTuple(
    @ColumnInfo(name = "film_id") val filmId: Int,
    val title: String,
    @ColumnInfo(name = "release_date") val releaseDate: Long,
    @ColumnInfo(name = "short_description") val shortDescription: String
)

data class FilmWithFavourStatusTuple(
    @ColumnInfo(name = "film_id") val filmId: Int,
    val title: String,
    @ColumnInfo(name = "release_date") val releaseDate: Long,
    @ColumnInfo(name = "short_description") val shortDescription: String,
    @ColumnInfo(name = "is_added_to_favourites") val isAddedToFavourites: Int

)
