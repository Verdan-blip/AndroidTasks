package ru.kpfu.itis.bagaviev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FragmentWelcomeBinding
import ru.kpfu.itis.bagaviev.ui.MainActivity
import ru.kpfu.itis.bagaviev.ui.fragments.base.BaseFragment

class WelcomeFragment : BaseFragment(R.layout.fragment_welcome) {

    private var viewBinding: FragmentWelcomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentWelcomeBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = requireActivity().findNavController(R.id.fv_root_container)

        viewBinding?.apply {
            btnSignIn.setOnClickListener {
                navController.navigate(R.id.signInFragment)
            }
            btnSignUp.setOnClickListener {
                navController.navigate(R.id.signUpFragment)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

}