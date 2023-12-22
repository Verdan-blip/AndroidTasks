package ru.kpfu.itis.bagaviev.ui.recyclerview.viewholders

import android.util.Log
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FilmItemBinding
import ru.kpfu.itis.bagaviev.model.film.FilmModel
import ru.kpfu.itis.bagaviev.ui.recyclerview.adapters.FilmsAdapter
import java.text.DateFormat
import java.util.Date

class FilmViewHolder(
    private val adapter: FilmsAdapter,
    private val viewBinding: FilmItemBinding,
    private val popupMenu: PopupMenu,
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
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var currentFilmModel: FilmModel? = null
    private var currentItemPosition: Int? = null

    init {
        viewBinding.apply {
            root.setOnLongClickListener {
                popupMenu.show()
                true
            }
            root.setOnClickListener {
                currentFilmModel?.also { filmModel ->
                    onFilmItemClick(filmModel)
                }
            }
            ivFavourites.setOnClickListener {
                currentFilmModel?.also {  filmModel ->
                    currentItemPosition?.also {  itemPosition ->
                        onFilmAddToFavourites(adapter, itemPosition, filmModel)
                    }
                }
            }
        }
        popupMenu.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.item_delete_film) {
                currentFilmModel?.also { filmModel ->
                    onFilmItemRemove(adapter, filmModel)
                }
            }
            true
        }
    }

    fun bind(position: Int, filmModel: FilmModel) {
        currentFilmModel = filmModel
        currentItemPosition = position
        viewBinding.apply {
            tvFilmTitle.text = filmModel.title
            tvFilmReleaseDate.text = Date(filmModel.releaseDate).toString()
            if (filmModel.isAddedToFavourites)
                ivFavourites.setImageResource(R.drawable.favourite_on)
            else
                ivFavourites.setImageResource(R.drawable.favourite_off)
        }
    }

}