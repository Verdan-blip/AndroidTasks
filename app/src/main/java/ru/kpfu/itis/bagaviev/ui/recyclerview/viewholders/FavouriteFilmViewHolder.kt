package ru.kpfu.itis.bagaviev.ui.recyclerview.viewholders

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.bagaviev.databinding.FavouriteFilmItemBinding
import ru.kpfu.itis.bagaviev.model.film.usecase.FavouriteFilmModel

class FavouriteFilmViewHolder(
    private val viewBinding: FavouriteFilmItemBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var currentFavouriteFilmModel: FavouriteFilmModel? = null

    fun onBind(favouriteFilmModel: FavouriteFilmModel) {
        currentFavouriteFilmModel = favouriteFilmModel
        viewBinding.apply {
            tvFilmTitle.text = favouriteFilmModel.title
        }
    }

}