package ru.kpfu.itis.bagaviev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.data.datasource.UserTokenDatasource
import ru.kpfu.itis.bagaviev.data.repository.FilmsRepository
import ru.kpfu.itis.bagaviev.data.repository.UsersRepository
import ru.kpfu.itis.bagaviev.databinding.FragmentFilmInfoBinding
import ru.kpfu.itis.bagaviev.model.film.FilmModel
import ru.kpfu.itis.bagaviev.model.film.usecase.FilmRatingModel
import ru.kpfu.itis.bagaviev.model.user.usecase.UserInfoModel
import ru.kpfu.itis.bagaviev.ui.fragments.base.BaseFragment

class FilmInfoFragment : BaseFragment(R.layout.fragment_film_info) {

    private var viewBinding: FragmentFilmInfoBinding? = null
    private var userInfoModel: UserInfoModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentFilmInfoBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {

            val token = UserTokenDatasource.getToken()

            token?.also { token ->
                lifecycleScope.launch(Dispatchers.IO) {
                    userInfoModel = UsersRepository.getByToken(token)
                    userInfoModel?.apply {
                        val filmId = getInt(FILM_ID)
                        val filmTitle = getString(FILM_TITLE)
                        val filmShortDescription = getString(FILM_SHORT_DESCRIPTION)
                        viewBinding?.apply {
                            tvFilmTitle.text = filmTitle
                            tvFilmDescription.text = filmShortDescription
                            lifecycleScope.launch(Dispatchers.IO) {
                                val averageRating = FilmsRepository.getAverageRating(filmId)
                                withContext(Dispatchers.Main) {
                                    if (averageRating == null) {
                                        tvAverageRating.text = "Нет оценок"
                                    } else {
                                        tvAverageRating.text = averageRating.toString()
                                    }
                                }
                            }
                            btnRate.setOnClickListener {
                                val rating = sbRating.progress
                                lifecycleScope.launch(Dispatchers.IO) {
                                    kotlin.runCatching {
                                        FilmsRepository.addRating(
                                            FilmRatingModel(
                                                filmId = filmId, userId = userId, rating = rating
                                            )
                                        )
                                    }.onSuccess {
                                        withContext(Dispatchers.Main) {
                                            showInformingDialog(
                                                "Спасибо!",
                                                "Вы успешно оставили рейтинг"
                                            )
                                        }
                                    }.onFailure {
                                        withContext(Dispatchers.Main) {
                                            showInformingDialog(
                                                "Ну уж нет!",
                                                "Вы уже ставили оценку"
                                            )
                                        }
                                    }
                                }
                            }
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

    companion object {
        private const val FILM_ID = "film_id"
        private const val FILM_TITLE = "film_title"
        private const val FILM_SHORT_DESCRIPTION = "film_short_description"
        fun bundleOf(filmModel: FilmModel) = bundleOf(
                FILM_ID to filmModel.filmId,
                FILM_TITLE to filmModel.title,
                FILM_SHORT_DESCRIPTION to filmModel.shortDescription
            )

    }

}