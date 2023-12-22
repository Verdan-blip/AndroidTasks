package ru.kpfu.itis.bagaviev.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.data.tuples.UserInfoTuple
import ru.kpfu.itis.bagaviev.ui.fragments.base.BaseFragment

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.fv_container)
                as NavHostFragment
        val navController = navHostFragment.navController
        val bnvMain = requireActivity().findViewById<BottomNavigationView>(R.id.bnv_main)
        bnvMain.setupWithNavController(navController)
    }

}