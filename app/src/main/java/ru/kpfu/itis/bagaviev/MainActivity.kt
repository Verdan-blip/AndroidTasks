package ru.kpfu.itis.bagaviev

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.kpfu.itis.bagaviev.util.handlers.NotificationsPermissionRequestHandler

class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null
    private var bnvMain: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationsPermissionRequestHandler = NotificationsPermissionRequestHandler(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationsPermissionRequestHandler.requestNotificationsPermission()
        }

        //Navigation controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        navController?.also {
            bnvMain = findViewById(R.id.bnv_main)
            bnvMain?.setupWithNavController(it)
        }
    }

    override fun onStart() {
        super.onStart()

        val openingPurpose = intent.getStringExtra(OPENING_PURPOSE)

        if (openingPurpose == OPENING_WITH_SHOWING_MESSAGE) {
            Toast.makeText(this, getString(R.string.toast_text_welcome), Toast.LENGTH_SHORT)
                .show()
        } else if (openingPurpose == OPENING_WITH_SHOWING_NOTIFICATION_SETTINGS) {
            bnvMain?.selectedItemId = R.id.notificationsFragment
        }

    }

    companion object {
        const val OPENING_PURPOSE = "opening_purpose"
        const val OPENING_WITH_SHOWING_MESSAGE = "opening_purpose_with_message"
        const val OPENING_WITH_SHOWING_NOTIFICATION_SETTINGS = "opening_purpose_notification_settings"
    }

}