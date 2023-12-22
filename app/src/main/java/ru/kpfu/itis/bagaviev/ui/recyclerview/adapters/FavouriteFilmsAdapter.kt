package ru.kpfu.itis.bagaviev.ui.recyclerview.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.bagaviev.databinding.FavouriteFilmItemBinding
import ru.kpfu.itis.bagaviev.model.film.usecase.FavouriteFilmModel
import ru.kpfu.itis.bagaviev.ui.recyclerview.diffutils.FavouriteFilmsDiffUtil
import ru.kpfu.itis.bagaviev.ui.recyclerview.viewholders.FavouriteFilmViewHolder

class FavouriteFilmsAdapter(
) : RecyclerView.Adapter<FavouriteFilmViewHolder>() {

    private var favouriteFilmModelList: MutableList<FavouriteFilmModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteFilmViewHolder {
        val viewBinding = FavouriteFilmItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavouriteFilmViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: FavouriteFilmViewHolder, position: Int) {
        holder.onBind(favouriteFilmModelList[position])
    }

    override fun getItemCount() =
        favouriteFilmModelList.size

    fun updateData(newFavouriteFilmModelList: List<FavouriteFilmModel>) {
        val diffUtil = FavouriteFilmsDiffUtil(
            oldList = favouriteFilmModelList, newList = newFavouriteFilmModelList
        )
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        favouriteFilmModelList.clear()
        favouriteFilmModelList.addAll(newFavouriteFilmModelList)

        diffResult.dispatchUpdatesTo(this)
    }

}