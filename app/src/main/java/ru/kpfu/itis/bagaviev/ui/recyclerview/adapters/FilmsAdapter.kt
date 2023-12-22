package ru.kpfu.itis.bagaviev.ui.recyclerview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FilmItemBinding
import ru.kpfu.itis.bagaviev.model.film.FilmModel
import ru.kpfu.itis.bagaviev.ui.recyclerview.diffutils.FilmsDiffUtil
import ru.kpfu.itis.bagaviev.ui.recyclerview.viewholders.FilmViewHolder

class FilmsAdapter(
    private val context: Context,
    private val onFilmItemRemove: (
        adapter: FilmsAdapter,
        filmModel: FilmModel
    ) -> Unit,
    private val onFilmAddToFavourites: (
        adapter: FilmsAdapter,
        position: Int,
        filmModel: FilmModel
    ) -> Unit,
    private val onFilmItemClick: (
        filmModel: FilmModel
    ) -> Unit
) : RecyclerView.Adapter<FilmViewHolder>() {

    private var filmsList: MutableList<FilmModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val viewBinding = FilmItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val popupMenu = PopupMenu(context, viewBinding.root)
        popupMenu.inflate(R.menu.film_popup_menu)
        return FilmViewHolder(this, viewBinding, popupMenu,
            onFilmItemRemove, onFilmAddToFavourites, onFilmItemClick
        )
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(position = position, filmModel = filmsList[position])
    }

    override fun getItemCount() = filmsList.size

    fun updateData(newFilmsList: List<FilmModel>) {
        val diffUtil = FilmsDiffUtil(oldList = filmsList, newList = newFilmsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        filmsList.clear()
        filmsList.addAll(newFilmsList)

        diffResult.dispatchUpdatesTo(this)
    }

    fun updateItem(position: Int, newFilmModel: FilmModel) {
        filmsList[position] = newFilmModel
        notifyItemChanged(position)
    }

}