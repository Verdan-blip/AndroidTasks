package ru.kpfu.itis.bagaviev

import android.app.Application
import ru.kpfu.itis.bagaviev.di.ServiceLocator

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.provideApplicationContext(this)
    }
}