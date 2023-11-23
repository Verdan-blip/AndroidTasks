package ru.kpfu.itis.bagaviev.util.settings

object CoroutinesSettings {

    const val DEFAULT_COROUTINES_COUNT = 5
    const val DEFAULT_ASYNC_STATE = false
    const val MAX_COROUTINES_COUNT = 10
    const val DEFAULT_STOP_ON_BACKGROUND_STATE = false
    const val COROUTINES_DURATION_IN_MILLIS = 3_000L

    var count: Int = DEFAULT_COROUTINES_COUNT
    var async: Boolean = DEFAULT_ASYNC_STATE
    var stopOnBackground: Boolean = DEFAULT_STOP_ON_BACKGROUND_STATE
}