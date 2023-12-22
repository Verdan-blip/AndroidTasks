package ru.kpfu.itis.bagaviev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.data.datasource.UserTokenDatasource
import ru.kpfu.itis.bagaviev.data.repository.FilmsRepository
import ru.kpfu.itis.bagaviev.data.tuples.InsertFilmTuple
import ru.kpfu.itis.bagaviev.databinding.FragmentAddFilmBinding
import ru.kpfu.itis.bagaviev.ui.fragments.base.BaseFragment

class AddFilmFragment : BaseFragment(R.layout.fragment_add_film) {

    private var viewBinding: FragmentAddFilmBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddFilmBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.apply {
            btnAddFilm.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    FilmsRepository.insertFilm(
                        (InsertFilmTuple(
                            etFilmTitle.text.toString(),
                            etFilmReleaseDate.text.toString().toLong(),
                            etFilmShortDescription.text.toString()
                        ))
                    )
                    withContext(Dispatchers.Main) {
                        showInformingDialog("Поздраыляем!", "Вы успешно добавили фильм!") {}
                    }
                }
            }
            if (UserTokenDatasource.getToken() == null) {
                btnAddFilm.isEnabled = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

}