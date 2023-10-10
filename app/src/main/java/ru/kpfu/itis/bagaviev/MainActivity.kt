package ru.kpfu.itis.bagaviev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import ru.kpfu.itis.bagaviev.databinding.ActivityMainBinding
import ru.kpfu.itis.bagaviev.fragments.FirstFragment
import ru.kpfu.itis.bagaviev.utils.ActionType

class MainActivity : AppCompatActivity() {

    private var viewBinding: ActivityMainBinding? = null
    private val fragmentContainerId
        get() = R.id.fragment_container

    fun navigateTo(actionType: ActionType, destination: Fragment, addToBackStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            if (addToBackStack) addToBackStack(null)
            when (actionType) {
                ActionType.ADD -> add(fragmentContainerId, destination)
                ActionType.REMOVE -> remove(destination)
                ActionType.REPLACE -> replace(fragmentContainerId, destination)
            }
            commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        if (savedInstanceState == null) {
            navigateTo(ActionType.ADD, FirstFragment(), false)
        }

    }

}