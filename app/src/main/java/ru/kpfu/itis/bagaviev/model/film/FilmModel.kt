package ru.kpfu.itis.bagaviev.model.film

import ru.kpfu.itis.bagaviev.data.tuples.FilmWithFavourStatusTuple

data class FilmModel(
    val filmId: Int,
    val title: String,
    val releaseDate: Long,
    val shortDescription: String,
    val isAddedToFavourites: Boolean
)

fun FilmModel.toFilmWithFavourStatusTuple() = FilmWithFavourStatusTuple(
    filmId = filmId, title = title, releaseDate = releaseDate, shortDescription = shortDescription,
    isAddedToFavourites = if (isAddedToFavourites) 1 else 0
)

fun FilmWithFavourStatusTuple.toFilmModel() = FilmModel(
    filmId = filmId, title = title, releaseDate = releaseDate, shortDescription = shortDescription,
    isAddedToFavourites = isAddedToFavourites > 0
)