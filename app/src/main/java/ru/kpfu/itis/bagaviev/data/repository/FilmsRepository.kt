package ru.kpfu.itis.bagaviev.data.repository

import ru.kpfu.itis.bagaviev.data.tuples.InsertFilmTuple
import ru.kpfu.itis.bagaviev.di.ServiceLocator
import ru.kpfu.itis.bagaviev.model.film.FilmModel
import ru.kpfu.itis.bagaviev.model.film.toFilmModel
import ru.kpfu.itis.bagaviev.model.film.usecase.FavouriteFilmModel
import ru.kpfu.itis.bagaviev.model.film.usecase.FilmRatingModel
import ru.kpfu.itis.bagaviev.model.film.usecase.toFavoriteFilmModel

object FilmsRepository {

    suspend fun insertFilm(insertFilmTuple: InsertFilmTuple) {
        ServiceLocator.database.filmDao.insert(
            insertFilmTuple.title,
            insertFilmTuple.releaseDate,
            insertFilmTuple.shortDescription
        )
    }

    suspend fun deleteFilm(filmId: Int) {
        ServiceLocator.database.filmDao.deleteById(filmId)
    }

    suspend fun getAllFilmsWithFavourStatusSortedByDate(userId: Int): List<FilmModel> {
        return ServiceLocator.database.usersWithFavouriteFilmsDao.getFilmsWithFavourStatusSortedByDate(
            userId = userId).map { it.toFilmModel() }
    }

    suspend fun getAllFavouriteFilms(userId: Int): List<FavouriteFilmModel> {
        return ServiceLocator.database.usersWithFavouriteFilmsDao.getFavouriteFilms(
            userId = userId).map { it.toFavoriteFilmModel() }
    }

    suspend fun getAverageRating(filmId: Int): Float? {
        return ServiceLocator.database.filmRatingsDao.getAverageRating(filmId)
    }

    suspend fun addRating(filmRatingModel: FilmRatingModel) {
        with (filmRatingModel) {
            ServiceLocator.database.filmRatingsDao.addRating(
                userId = userId, filmId = filmId, rating = rating
            )
        }
    }

}