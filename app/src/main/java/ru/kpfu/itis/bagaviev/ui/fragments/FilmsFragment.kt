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
import ru.kpfu.itis.bagaviev.data.repository.FilmsRepository
import ru.kpfu.itis.bagaviev.data.repository.UsersRepository
import ru.kpfu.itis.bagaviev.databinding.FragmentFilmsBinding
import ru.kpfu.itis.bagaviev.model.film.FilmModel
import ru.kpfu.itis.bagaviev.model.film.usecase.FavouriteFilmModel
import ru.kpfu.itis.bagaviev.model.film.usecase.FilmAddToFavouritesModel
import ru.kpfu.itis.bagaviev.model.film.usecase.FilmRemoveFromFavouritesModel
import ru.kpfu.itis.bagaviev.model.user.usecase.UserInfoModel
import ru.kpfu.itis.bagaviev.ui.recyclerview.adapters.FilmsAdapter
import ru.kpfu.itis.bagaviev.ui.fragments.base.BaseFragment
import ru.kpfu.itis.bagaviev.ui.recyclerview.adapters.FavouriteFilmsAdapter
import ru.kpfu.itis.bagaviev.ui.recyclerview.decorators.FilmsItemDecoration

class FilmsFragment : BaseFragment(R.layout.fragment_films) {

    private var viewBinding: FragmentFilmsBinding? = null
    private var userInfoModel: UserInfoModel? = null

    private var favouriteFilmsAdapter: FavouriteFilmsAdapter? = null

    private fun onFilmItemRemove(adapter: FilmsAdapter, filmModel: FilmModel) {
        userInfoModel?.also { userInfoModel ->
            lifecycleScope.launch {
                kotlin.runCatching {
                    withContext(Dispatchers.IO) {
                        FilmsRepository.deleteFilm(filmId = filmModel.filmId)
                        val filmModels = FilmsRepository.getAllFilmsWithFavourStatusSortedByDate(userInfoModel.userId)
                        withContext(Dispatchers.Main) {
                            adapter.updateData(filmModels)
                        }
                    }
                }.onSuccess {
                    showInformingDialog("Принято!", "Фильм успешно был удален")
                }.onFailure {
                    showInformingDialog("Произошла ошибка!", it.message.toString())
                }
            }
        }
    }

    private fun onFilmAddToFavourites(adapter: FilmsAdapter, position: Int, filmModel: FilmModel) {
        userInfoModel?.apply {
            lifecycleScope.launch {
                val newFavoritesModelList = mutableListOf<FavouriteFilmModel>()
                kotlin.runCatching {
                    withContext(Dispatchers.IO) {
                        if (filmModel.isAddedToFavourites) {
                            UsersRepository.removeFromFavourites(
                                FilmRemoveFromFavouritesModel(
                                    userId = userId, filmId = filmModel.filmId
                                )
                            )
                            newFavoritesModelList.addAll(FilmsRepository.getAllFavouriteFilms(userId))
                        } else {
                            UsersRepository.addToFavourites(
                                FilmAddToFavouritesModel(
                                    userId = userId, filmId = filmModel.filmId
                                )
                            )
                            newFavoritesModelList.addAll(FilmsRepository.getAllFavouriteFilms(userId))
                        }
                    }
                }.onSuccess {
                    withContext(Dispatchers.Main) {
                        adapter.updateItem(
                            position = position,
                            newFilmModel = filmModel.copy(isAddedToFavourites = !filmModel.isAddedToFavourites)
                        )
                        favouriteFilmsAdapter?.updateData(newFavoritesModelList)
                    }
                }.onFailure {
                    showInformingDialog("Произошла ошибка!", it.message.toString())
                }
            }
        }
    }

    private fun onFilmItemClick(filmModel: FilmModel) {
        val navController = requireActivity().findNavController(R.id.fv_root_container)
        navController.navigate(R.id.filmInfoFragment, FilmInfoFragment.bundleOf(filmModel))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentFilmsBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = UserTokenDatasource.getToken()
        if (token != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                userInfoModel = UsersRepository.getByToken(token)
                userInfoModel?.also { userInfoModel ->
                    withContext(Dispatchers.Main) {
                        val filmsAdapter = FilmsAdapter(
                            context = requireContext(),
                            this@FilmsFragment::onFilmItemRemove,
                            this@FilmsFragment::onFilmAddToFavourites,
                            this@FilmsFragment::onFilmItemClick
                        )
                        favouriteFilmsAdapter = FavouriteFilmsAdapter()
                        viewBinding?.apply {
                            rvAllFilms.adapter = filmsAdapter
                            rvAllFilms.addItemDecoration(FilmsItemDecoration(resources.displayMetrics))
                            rvFavouriteFilms.adapter = favouriteFilmsAdapter
                            rvFavouriteFilms.addItemDecoration(FilmsItemDecoration(resources.displayMetrics))
                        }
                        lifecycleScope.launch(Dispatchers.IO) {
                            val filmModelList = FilmsRepository.getAllFilmsWithFavourStatusSortedByDate(
                                userId = userInfoModel.userId
                            )
                            val favouriteFilmModelList = FilmsRepository.getAllFavouriteFilms(
                                userId = userInfoModel.userId
                            )
                            withContext(Dispatchers.Main) {
                                filmsAdapter.updateData(filmModelList)
                                favouriteFilmsAdapter?.updateData(favouriteFilmModelList)
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

}