package ru.kpfu.itis.bagaviev

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.kpfu.itis.bagaviev.databinding.ActivityMainBinding
import ru.kpfu.itis.bagaviev.fragments.FirstFragment
import ru.kpfu.itis.bagaviev.fragments.FourthFragment
import ru.kpfu.itis.bagaviev.utils.ActionType

class MainActivity : AppCompatActivity() {

    private var viewBinding: ActivityMainBinding? = null

    var fragmentContainerId = R.id.fragment_container
    var additionalFragmentContainerId = R.id.additional_fragment_container

    fun doTransaction(actionType: ActionType, container: Int, destination: Fragment, addToBackStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            if (addToBackStack) addToBackStack(null)
            when (actionType) {
                ActionType.ADD -> add(container, destination)
                ActionType.REMOVE -> remove(destination)
                ActionType.REPLACE -> replace(container, destination)
            }
            commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        if (savedInstanceState == null) {
            doTransaction(ActionType.ADD, fragmentContainerId, FirstFragment(), false)
        }

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            doTransaction(ActionType.ADD, additionalFragmentContainerId, FourthFragment(), false)
        }


    }

}