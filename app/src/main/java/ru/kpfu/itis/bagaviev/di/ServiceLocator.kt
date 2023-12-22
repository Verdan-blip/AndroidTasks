package ru.kpfu.itis.bagaviev.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import ru.kpfu.itis.bagaviev.data.database.AppDataBase
import ru.kpfu.itis.bagaviev.data.exceptions.ContextNotProvidedException

object ServiceLocator {

    private const val SHARED_PREFERENCE_NAME = "user_session_pref"

    val database: AppDataBase by lazy {
        val context = applicationContext ?: throw ContextNotProvidedException()
        Room.databaseBuilder(
            context, AppDataBase::class.java, AppDataBase.DATABASE_NAME
        ).build()
    }

    val sharedPreferences: SharedPreferences by lazy {
        val context = applicationContext ?: throw ContextNotProvidedException()
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private var applicationContext: Context? = null

    fun provideApplicationContext(context: Context) {
        applicationContext = context
    }

}