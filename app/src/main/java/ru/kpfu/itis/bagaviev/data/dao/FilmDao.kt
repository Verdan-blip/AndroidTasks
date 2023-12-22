package ru.kpfu.itis.bagaviev.data.dao

import androidx.room.Dao
import androidx.room.Query
import ru.kpfu.itis.bagaviev.data.tuples.FavouriteFilmTuple

@Dao
interface FilmDao {

    @Query("INSERT INTO films (title, release_date, short_description) " +
            "VALUES (:title, :releaseDate, :shortDescription)")
    suspend fun insert(title: String, releaseDate: Long, shortDescription: String)

    @Query("SELECT * FROM films WHERE film_id = :filmId")
    suspend fun getById(filmId: Int): FavouriteFilmTuple?

    @Query("DELETE FROM films WHERE film_id = :filmId")
    suspend fun deleteById(filmId: Int)

}