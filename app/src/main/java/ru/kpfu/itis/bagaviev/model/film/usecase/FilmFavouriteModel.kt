package ru.kpfu.itis.bagaviev.model.film.usecase

import ru.kpfu.itis.bagaviev.data.tuples.FavouriteFilmTuple

data class FavouriteFilmModel(
    val filmId: Int,
    val title: String,
    val releaseDate: Long,
    val shortDescription: String
)

fun FavouriteFilmModel.toFavoriteFilmTuple() = FavouriteFilmTuple(
    filmId = filmId, title = title, releaseDate = releaseDate, shortDescription = shortDescription
)

fun FavouriteFilmTuple.toFavoriteFilmModel() = FavouriteFilmModel(
    filmId = filmId, title = title, releaseDate = releaseDate, shortDescription = shortDescription
)