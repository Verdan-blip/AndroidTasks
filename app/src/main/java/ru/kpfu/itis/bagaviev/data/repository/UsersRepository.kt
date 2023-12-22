package ru.kpfu.itis.bagaviev.data.repository

import ru.kpfu.itis.bagaviev.data.exceptions.AuthException
import ru.kpfu.itis.bagaviev.data.exceptions.IncorrectTokenException
import ru.kpfu.itis.bagaviev.data.exceptions.SuchPhoneAlreadyExistsException
import ru.kpfu.itis.bagaviev.data.util.UserTokenUtil
import ru.kpfu.itis.bagaviev.di.ServiceLocator
import ru.kpfu.itis.bagaviev.model.film.usecase.FilmAddToFavouritesModel
import ru.kpfu.itis.bagaviev.model.film.usecase.FilmRemoveFromFavouritesModel
import ru.kpfu.itis.bagaviev.model.user.usecase.UserInfoModel
import ru.kpfu.itis.bagaviev.model.user.usecase.UserSignInModel
import ru.kpfu.itis.bagaviev.model.user.usecase.UserSignUpModel
import ru.kpfu.itis.bagaviev.model.user.usecase.UserUpdatePasswordModel
import ru.kpfu.itis.bagaviev.model.user.usecase.UserUpdatePhoneModel
import ru.kpfu.itis.bagaviev.model.user.usecase.toUserInfoModel

object UsersRepository {

    suspend fun signUp(userSignUpModel: UserSignUpModel): String {
        with(userSignUpModel) {
            val token = UserTokenUtil.get(email = email, phone = phone)
            ServiceLocator.database.userDao.insert(
                name = name, phone = phone, email = email, password = password, token = token
            )
            return token
        }
    }

    suspend fun getByToken(token: String): UserInfoModel {
        val userInfoModel = ServiceLocator.database.userDao.getByToken(token)?.toUserInfoModel()
        return userInfoModel ?: throw IncorrectTokenException("Неверный токен")
    }

    suspend fun signIn(userSignInModel: UserSignInModel): String {
        with (userSignInModel) {
            val userToken = ServiceLocator.database.userDao.getToken(
                email = email, password = password
            )
            return userToken ?: throw AuthException("Неверная электронная почта или пароль")
        }
    }

    suspend fun updatePhone(userUpdatePhoneModel: UserUpdatePhoneModel) {
        try {
            with (userUpdatePhoneModel) {
                ServiceLocator.database.userDao.updatePhone(
                    userId = userId,
                    phone = phone
                )
            }
        } catch (e: Exception) {
            throw SuchPhoneAlreadyExistsException("Данный номер телефона занят другим пользователем")
        }
    }

    suspend fun removeFromFavourites(removeFromFavouritesModel: FilmRemoveFromFavouritesModel) {
        with (removeFromFavouritesModel) {
            ServiceLocator.database.usersWithFavouriteFilmsDao.removeFromFavourites(
                userId = userId, filmId = filmId
            )
        }
    }

    suspend fun addToFavourites(addToFavouritesModel: FilmAddToFavouritesModel) {
        with (addToFavouritesModel) {
            ServiceLocator.database.usersWithFavouriteFilmsDao.addToFavourites(
                userId = userId, filmId = filmId
            )
        }
    }

    suspend fun updatePassword(userUpdatePasswordModel: UserUpdatePasswordModel) {
        with (userUpdatePasswordModel) {
                ServiceLocator.database.userDao.updatePassword(
                    userId = userId,
                    password = password
                )
        }
    }
    suspend fun deleteUser(token: String) {
        ServiceLocator.database.userDao.deleteUser(token)
    }

}