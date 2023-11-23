package ru.kpfu.itis.bagaviev.util

import android.app.Notification
import android.app.NotificationManager
import androidx.annotation.IdRes
import ru.kpfu.itis.bagaviev.R

fun radioButtonIdToImportance(@IdRes id: Int) =
    when (id) {
        R.id.rb_min_importance -> NotificationManager.IMPORTANCE_MIN
        R.id.rb_low_importance -> NotificationManager.IMPORTANCE_LOW
        R.id.rb_max_importance -> NotificationManager.IMPORTANCE_MAX
        else -> NotificationManager.IMPORTANCE_DEFAULT
    }

@IdRes
fun importanceToRadioButtonId(importance: Int) =
    when (importance) {
        NotificationManager.IMPORTANCE_MIN -> R.id.rb_min_importance
        NotificationManager.IMPORTANCE_LOW -> R.id.rb_low_importance
        NotificationManager.IMPORTANCE_MAX -> R.id.rb_max_importance
        else -> R.id.rb_default_importance
    }

fun radioButtonIdToVisibility(@IdRes id: Int) =
    when (id) {
        R.id.rb_visibility_private -> Notification.VISIBILITY_PRIVATE
        R.id.rb_visibility_secret -> Notification.VISIBILITY_SECRET
        else -> Notification.VISIBILITY_PUBLIC
    }

@IdRes
fun visibilityToRadioButtonId(visibility: Int) =
    when (visibility) {
        Notification.VISIBILITY_PUBLIC -> R.id.rb_visibility_public
        Notification.VISIBILITY_PRIVATE -> R.id.rb_visibility_private
        else -> R.id.rb_visibility_secret
    }