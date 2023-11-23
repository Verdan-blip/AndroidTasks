package ru.kpfu.itis.bagaviev.util.handlers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import ru.kpfu.itis.bagaviev.MainActivity
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.util.settings.ReminderNotificationSettings

class ReminderNotificationHandler(
    private val context: Context
) {

    private val notificationsIdList = mutableListOf(0)
    private var currentNotificationChannel: NotificationChannel? = null

    private val manager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

    //Main intent
    private val openNotificationSettingsIntent = Intent(context as MainActivity, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        putExtra(MainActivity.OPENING_PURPOSE, MainActivity.OPENING_WITH_SHOWING_NOTIFICATION_SETTINGS)
    }
    private val openApplicationWithMessageIntent = Intent(context as MainActivity, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        putExtra(MainActivity.OPENING_PURPOSE, MainActivity.OPENING_WITH_SHOWING_MESSAGE)
    }

    //Pending intents
    private val openNotificationSettingsPendingIntent = PendingIntent.getActivity(
        context, 0, openNotificationSettingsIntent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_ONE_SHOT
    )

    private val openApplicationWithMessagePendingIntent = PendingIntent.getActivity(
        context, 1, openApplicationWithMessageIntent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_ONE_SHOT
    )

    private fun createNotification(title: String, text: String) =
        NotificationCompat.Builder(context, context.getString(R.string.reminder_notification_channel_id))
            .run {
                setContentTitle(title)
                setContentText(text)
                setSmallIcon(android.R.drawable.checkbox_on_background)
                setCategory(Notification.CATEGORY_EVENT)
                currentNotificationChannel?.apply {
                    setChannelId(id)
                }

                ReminderNotificationSettings.apply {
                    setVisibility(visibility)
                    if (showTextFully) setStyle(NotificationCompat.BigTextStyle().bigText(text))
                    if (showControlButtons) {
                        addAction(
                            android.R.drawable.ic_menu_preferences,
                            "Настройки",
                            openNotificationSettingsPendingIntent
                        )
                        addAction(
                            android.R.drawable.ic_menu_preferences,
                            "Открыть",
                            openApplicationWithMessagePendingIntent
                        )
                    }
                }

                build()
            }

    fun sendNotification(title: String, text: String) {


        //Optimization for no changes case
        if (currentNotificationChannel?.importance == ReminderNotificationSettings.importance)
            return

        //Remove channel if it already exists
        currentNotificationChannel?.also {
            manager.deleteNotificationChannel(it.id)
        }

        ReminderNotificationSettings.apply {
            currentNotificationChannel = NotificationChannel(
                context.getString(R.string.reminder_notification_channel_id) + "_${importance}",
                context.getString(R.string.reminder_notification_channel_name),
                importance
            )

            //After removing it's needed to create new channel
            currentNotificationChannel?.also {
                manager.createNotificationChannel(it)
            }
        }

        manager.notify(notificationsIdList.last(), createNotification(title, text))
        notificationsIdList.add(notificationsIdList.last() + 1)
    }


}