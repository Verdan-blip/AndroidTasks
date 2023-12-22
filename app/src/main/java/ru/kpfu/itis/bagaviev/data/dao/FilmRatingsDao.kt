package ru.kpfu.itis.bagaviev.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FilmRatingsDao {

    @Query("INSERT INTO film_ratings (user_id, film_id, rating) VALUES (:userId, :filmId, :rating)")
    suspend fun addRating(userId: Int, filmId: Int, rating: Int)

    @Query("SELECT AVG(rating) FROM film_ratings WHERE film_id = :filmRating")
    suspend fun getAverageRating(filmRating: Int): Float?

}