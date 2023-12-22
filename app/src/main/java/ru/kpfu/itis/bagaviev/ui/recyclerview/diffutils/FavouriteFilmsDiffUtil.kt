package ru.kpfu.itis.bagaviev.ui.recyclerview.diffutils

import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.itis.bagaviev.model.film.usecase.FavouriteFilmModel

class FavouriteFilmsDiffUtil(
    private val oldList: List<FavouriteFilmModel>,
    private val newList: List<FavouriteFilmModel>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].filmId == newList[newItemPosition].filmId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}