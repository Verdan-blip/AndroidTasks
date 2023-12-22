package ru.kpfu.itis.bagaviev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.data.datasource.UserTokenDatasource
import ru.kpfu.itis.bagaviev.data.exceptions.AuthException
import ru.kpfu.itis.bagaviev.data.repository.UsersRepository
import ru.kpfu.itis.bagaviev.databinding.FragmentSignInBinding
import ru.kpfu.itis.bagaviev.model.user.usecase.UserSignInModel
import ru.kpfu.itis.bagaviev.ui.fragments.base.BaseFragment

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private var viewBinding: FragmentSignInBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSignInBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = requireActivity().findNavController(R.id.fv_root_container)

        viewBinding?.apply {
            btnSignIn.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val token = UsersRepository.signIn(UserSignInModel(
                            email = etEmail.text.toString(), password = etPassword.text.toString())
                        )
                        withContext(Dispatchers.Main) {
                            UserTokenDatasource.saveToken(token)
                            navController.navigate(R.id.homeFragment)
                        }
                    } catch (e: AuthException) {
                        withContext(Dispatchers.Main) {
                            showInformingDialog("Произошла ошибка", e.message.toString()) { }
                        }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

}