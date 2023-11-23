package ru.kpfu.itis.bagaviev.util.settings

import android.app.Notification
import android.app.NotificationManager

object ReminderNotificationSettings {
    var visibility: Int = Notification.VISIBILITY_PUBLIC
    var importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    var showTextFully: Boolean = false
    var showControlButtons: Boolean = false
}