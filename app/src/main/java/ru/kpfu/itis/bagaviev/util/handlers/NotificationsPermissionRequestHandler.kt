package ru.kpfu.itis.bagaviev.util.handlers

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import ru.kpfu.itis.bagaviev.MainActivity
import ru.kpfu.itis.bagaviev.R

class NotificationsPermissionRequestHandler(
    private val activity: MainActivity
) {

    private val notificationsPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        this::onNotificationsPermission
    )

    private fun onNotificationsPermission(isGranted: Boolean) {
        if (!isGranted) {
            if (activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                AlertDialog.Builder(activity)
                    .setTitle(activity.getString(R.string.notifications_after_permission_denied_forever_dialog_title))
                    .setMessage(activity.getString(R.string.notifications_after_permission_denied_forever_dialog_message))
                    .setNeutralButton(activity.getString(R.string.notifications_after_permission_denied_forever_dialog_button_text), null)
                    .create()
                    .show()
            } else {
                AlertDialog.Builder(activity)
                    .setTitle(activity.getString(R.string.notifications_after_permission_denied_dialog_title))
                    .setMessage(activity.getString(R.string.notifications_after_permission_denied_dialog_message))
                    .setPositiveButton(R.string.notifications_after_permission_denied_dialog_button_text) {
                            _, _ -> openApplicationSettings()
                    }
                    .create()
                    .show()
            }
        }
    }

    private fun openApplicationSettings() {
        val action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        val intent = Intent(action)
        intent.putExtra("android.provider.extra.APP_PACKAGE", activity.packageName);
        activity.startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun requestNotificationsPermission() {
        notificationsPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

}