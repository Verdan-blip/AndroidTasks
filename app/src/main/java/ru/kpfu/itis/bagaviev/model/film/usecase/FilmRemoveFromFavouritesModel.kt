package ru.kpfu.itis.bagaviev.model.film.usecase

data class FilmRemoveFromFavouritesModel(
    val userId: Int,
    val filmId: Int
)