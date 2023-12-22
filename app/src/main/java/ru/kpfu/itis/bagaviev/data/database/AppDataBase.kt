package ru.kpfu.itis.bagaviev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kpfu.itis.bagaviev.data.dao.FilmDao
import ru.kpfu.itis.bagaviev.data.dao.FilmRatingsDao
import ru.kpfu.itis.bagaviev.data.dao.UserDao
import ru.kpfu.itis.bagaviev.data.dao.UsersWithFavouriteFilmsDao
import ru.kpfu.itis.bagaviev.data.entities.FilmDbEntity
import ru.kpfu.itis.bagaviev.data.entities.FilmRatingsDbEntity
import ru.kpfu.itis.bagaviev.data.entities.UserDbEntity
import ru.kpfu.itis.bagaviev.data.entities.UsersFavouriteFilmsDbEntity


@Database(entities = [
    UserDbEntity::class,
    FilmDbEntity::class,
    UsersFavouriteFilmsDbEntity::class,
    FilmRatingsDbEntity::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "film_manager"
    }

    abstract val userDao: UserDao

    abstract val filmDao: FilmDao

    abstract val usersWithFavouriteFilmsDao: UsersWithFavouriteFilmsDao

    abstract val filmRatingsDao: FilmRatingsDao

}