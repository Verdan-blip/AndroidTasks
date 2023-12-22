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
import ru.kpfu.itis.bagaviev.data.exceptions.SuchPhoneAlreadyExistsException
import ru.kpfu.itis.bagaviev.data.repository.UsersRepository
import ru.kpfu.itis.bagaviev.databinding.FragmentProfileBinding
import ru.kpfu.itis.bagaviev.model.user.usecase.UserUpdatePasswordModel
import ru.kpfu.itis.bagaviev.model.user.usecase.UserUpdatePhoneModel
import ru.kpfu.itis.bagaviev.ui.fragments.base.BaseFragment

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private var viewBinding: FragmentProfileBinding? = null

    private fun updatePhone(
        userUpdatePhoneModel: UserUpdatePhoneModel,
        onSuccess: () -> Unit,
        onFail: (reason: String) -> Unit
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                UsersRepository.updatePhone(userUpdatePhoneModel)
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (exception: SuchPhoneAlreadyExistsException) {
                withContext(Dispatchers.Main) {
                    onFail(exception.message ?: "Нераспознанное исключение")
                }
            }
        }
    }

    private fun updatePassword(
        userUpdatePasswordModel: UserUpdatePasswordModel,
        onSuccess: () -> Unit,
        onFail: (reason: String) -> Unit
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                UsersRepository.updatePassword(userUpdatePasswordModel)
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (exception: SuchPhoneAlreadyExistsException) {
                withContext(Dispatchers.Main) {
                    onFail(exception.message ?: "Нераспознанное исключение")
                }
            }
        }
    }

    private fun setDataToProfile(name: String, phone: String, email: String) {
        viewBinding?.apply {
            tvName.text = name
            etPhoneNumber.setText(phone)
            etEmail.setText(email)
            etEmail.isEnabled = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentProfileBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = UserTokenDatasource.getToken()
        val navController = requireActivity().findNavController(R.id.fv_container)

        if (token == null) {
            navController.navigate(R.id.welcomeFragment)
        } else {
            viewBinding?.apply {
                lifecycleScope.launch(Dispatchers.IO) {
                    val userInfoTuple = UsersRepository.getByToken(token)
                    withContext(Dispatchers.Main) {
                        setDataToProfile(
                            name = userInfoTuple.name,
                            phone = userInfoTuple.phone,
                            email = userInfoTuple.email
                        )
                        btnSavePhone.setOnClickListener {
                            updatePhone(UserUpdatePhoneModel(
                                userId = userInfoTuple.userId,
                                phone = etPhoneNumber.text.toString()),
                                onSuccess = {
                                    showInformingDialog("Успех", "Вы успешно обновили номер телефона")
                                },
                                onFail = { reason ->
                                    showInformingDialog("Произошла ошибка", reason)
                                }
                            )
                        }
                        btnSavePassword.setOnClickListener {
                            updatePassword(UserUpdatePasswordModel(
                                userId = userInfoTuple.userId,
                                password = etPassword.text.toString()),
                                onSuccess = {
                                    showInformingDialog("Успех", "Вы успешно обновили пароль")
                                },
                                onFail = { reason ->
                                    showInformingDialog("Произошла ошибка", reason)
                                }
                            )
                        }
                    }
                }
                btnLogOut.setOnClickListener {
                    UserTokenDatasource.saveToken(null)
                    navController.navigate(R.id.welcomeFragment)
                }
                btnDeleteAccount.setOnClickListener {
                    lifecycleScope.launch(Dispatchers.IO) {
                        kotlin.runCatching {
                            UsersRepository.deleteUser(token)
                        }.onSuccess {
                            withContext(Dispatchers.Main) {
                                UserTokenDatasource.saveToken(null)
                                showInformingDialog("Эх :(", "Аккаунт был успешно удален") {
                                    navController.navigate(R.id.welcomeFragment)
                                }
                            }
                        }.onFailure {
                            showInformingDialog("Произошла ошибка", it.message.toString())
                        }
                    }
                    UserTokenDatasource.saveToken(null)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

}