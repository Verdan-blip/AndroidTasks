package ru.kpfu.itis.bagaviev.data.dao

import androidx.room.Dao
import androidx.room.Query
import ru.kpfu.itis.bagaviev.data.tuples.FavouriteFilmTuple
import ru.kpfu.itis.bagaviev.data.tuples.FilmWithFavourStatusTuple

@Dao
interface UsersWithFavouriteFilmsDao {

    @Query("SELECT " +
            "films.film_id, " +
            "films.title, " +
            "films.release_date, " +
            "films.short_description, " +
            "(CASE WHEN favourite_films.user_id IS NULL then 0 ELSE 1 END) AS is_added_to_favourites " +
            "FROM films " +
            "LEFT JOIN (SELECT * FROM users_favourite_films WHERE users_favourite_films.user_id = :userId) AS favourite_films " +
            "ON favourite_films.film_id = films.film_id ORDER BY films.release_date DESC"
    )
    suspend fun getFilmsWithFavourStatusSortedByDate(userId: Int): List<FilmWithFavourStatusTuple>

    @Query("SELECT * FROM users_favourite_films " +
            "INNER JOIN films " +
            "ON users_favourite_films.film_id = films.film_id " +
            "WHERE users_favourite_films.user_id = :userId"
    )
    suspend fun getFavouriteFilms(userId: Int): List<FavouriteFilmTuple>

    @Query("DELETE FROM users_favourite_films WHERE user_id = :userId AND film_id = :filmId")
    suspend fun removeFromFavourites(userId: Int, filmId: Int)

    @Query("INSERT INTO users_favourite_films (user_id, film_id) VALUES (:userId, :filmId)")
    suspend fun addToFavourites(userId: Int, filmId: Int)

}