package ru.kpfu.itis.bagaviev.util.handlers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.kpfu.itis.bagaviev.R

class CoroutinesNotificationsHandler(
    private val context: Context
) {

    private val manager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

    private fun createNotification() =
        NotificationCompat.Builder(context, context.getString(R.string.coroutines_notification_channel_id))
            .setContentTitle(context.getString(R.string.coroutines_notification_title))
            .setContentText(context.getString(R.string.coroutines_notification_text))
            .setSmallIcon(android.R.drawable.ic_popup_sync)
            .build()

    fun sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.getString(R.string.coroutines_notification_channel_id),
                context.getString(R.string.coroutines_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        manager.notify(0, createNotification())
    }

}