package ru.kpfu.itis.bagaviev.recylerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.bagaviev.databinding.ItemNewsFeedBinding
import ru.kpfu.itis.bagaviev.model.NewsFeedModel

class NewsFeedViewHolder(
    private val viewBinding: ItemNewsFeedBinding,
    private val onNewsFeedItemClicked: (newsFeedModel: NewsFeedModel) -> Unit,
    private val onAddToFavouritesButtonClicked: (position: Int, newsFeedModel: NewsFeedModel) -> Unit,
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var newsFeedModel: NewsFeedModel? = null

    init {
        viewBinding.root.setOnClickListener {
            this.newsFeedModel?.let(onNewsFeedItemClicked)
        }
        viewBinding.ibAddToFavourites.setOnClickListener {
            this.newsFeedModel?.let {
                val data = it.copy(isAddedToFavourites = !it.isAddedToFavourites)
                onAddToFavouritesButtonClicked(adapterPosition, data)
            }
        }
    }

    fun bind(newsFeedModel: NewsFeedModel) {
        this.newsFeedModel = newsFeedModel
        viewBinding.apply {
            tvNewsTitle.setText(newsFeedModel.title)
            ivNewsImage.setImageResource(newsFeedModel.image)
        }
        setAddToFavouritesButtonStatus(newsFeedModel.isAddedToFavourites)
    }

    fun setAddToFavouritesButtonStatus(isAddedToFavourites: Boolean) {
        viewBinding.ibAddToFavourites.setImageResource(
            if (isAddedToFavourites)
                android.R.drawable.btn_star_big_on
            else
                android.R.drawable.btn_star_big_off
        )
    }

}