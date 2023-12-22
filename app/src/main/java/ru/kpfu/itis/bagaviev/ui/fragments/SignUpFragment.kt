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
import ru.kpfu.itis.bagaviev.data.repository.UsersRepository
import ru.kpfu.itis.bagaviev.databinding.FragmentSignUpBinding
import ru.kpfu.itis.bagaviev.model.user.usecase.UserSignUpModel
import ru.kpfu.itis.bagaviev.ui.fragments.base.BaseFragment
import java.lang.Exception

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private var viewBinding: FragmentSignUpBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSignUpBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = requireActivity().findNavController(R.id.fv_root_container)

        viewBinding?.apply {
            btnSignUp.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        UsersRepository.signUp(UserSignUpModel(
                            name = etUserName.text.toString(),
                            phone = etPhoneNumber.text.toString(),
                            email = etEmail.text.toString(),
                            password = etPassword.text.toString()
                        ))
                        withContext(Dispatchers.Main) {
                            showInformingDialog("Успех", "Аккаунт был успешно зарегистрирован") {
                                navController.navigate(R.id.signInFragment)
                            }
                        }
                    } catch (e: Exception) {
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