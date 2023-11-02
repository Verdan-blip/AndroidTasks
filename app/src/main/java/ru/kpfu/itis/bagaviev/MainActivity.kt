package ru.kpfu.itis.bagaviev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import ru.kpfu.itis.bagaviev.fragments.StartFragment
import ru.kpfu.itis.bagaviev.util.ActionType

class MainActivity : AppCompatActivity() {

    fun navigateTo(actionType: ActionType,
                   @IdRes container: Int,
                   destination: Fragment,
                   addToBackStack: Boolean,
                   tag: String? = null) {
        supportFragmentManager.beginTransaction().apply {
            when (actionType) {
                ActionType.REPLACE -> replace(container, destination)
                ActionType.ADD -> add(container, destination)
                ActionType.REMOVE -> remove(destination)
            }
            if (addToBackStack) addToBackStack(tag)
            commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateTo(ActionType.REPLACE, R.id.fv_container, StartFragment(), true)
    }

}