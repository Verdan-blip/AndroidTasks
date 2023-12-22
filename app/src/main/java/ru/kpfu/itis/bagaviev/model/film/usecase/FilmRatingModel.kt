package ru.kpfu.itis.bagaviev.model.film.usecase

data class FilmRatingModel(
    val userId: Int,
    val filmId: Int,
    val rating: Int
)