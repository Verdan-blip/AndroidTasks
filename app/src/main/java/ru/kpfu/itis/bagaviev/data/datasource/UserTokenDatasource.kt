package ru.kpfu.itis.bagaviev.data.datasource

import androidx.core.content.edit
import ru.kpfu.itis.bagaviev.di.ServiceLocator

object UserTokenDatasource {

    private const val USER_TOKEN_KEY = "user_token"

    fun getToken() =
        ServiceLocator.sharedPreferences.getString(USER_TOKEN_KEY, null)

    fun saveToken(token: String?) {
        ServiceLocator.sharedPreferences.edit {
            putString(USER_TOKEN_KEY, token)
        }
    }

}
