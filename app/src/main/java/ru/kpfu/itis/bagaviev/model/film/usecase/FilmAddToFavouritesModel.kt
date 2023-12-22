package ru.kpfu.itis.bagaviev.model.film.usecase

data class FilmAddToFavouritesModel(
    val userId: Int,
    val filmId: Int
)