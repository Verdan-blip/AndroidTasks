package ru.kpfu.itis.bagaviev.recylerview.diffutils

import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.itis.bagaviev.model.NewsFeedModel
import ru.kpfu.itis.bagaviev.model.NewsListItem

class NewsFeedDiffUtil(
    private val oldItemsList: List<NewsListItem>,
    private val newItemsList: List<NewsListItem>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItemsList.size

    override fun getNewListSize(): Int = newItemsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]
        return oldItem.getType() == newItem.getType()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]

        return (oldItem == newItem)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

        val oldItem = oldItemsList[oldItemPosition] as? NewsFeedModel
        val newItem = newItemsList[newItemPosition] as? NewsFeedModel

        oldItem?.also { old ->
            newItem?.also { new ->
                return old.isAddedToFavourites != new.isAddedToFavourites
            }
        }
        return null
    }
}