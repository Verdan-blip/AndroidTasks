package ru.kpfu.itis.bagaviev.recylerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.kpfu.itis.bagaviev.databinding.ItemNewsAddBinding
import ru.kpfu.itis.bagaviev.databinding.ItemNewsDateBinding
import ru.kpfu.itis.bagaviev.databinding.ItemNewsFeedBinding
import ru.kpfu.itis.bagaviev.model.NewsAddModel
import ru.kpfu.itis.bagaviev.model.NewsDateModel
import ru.kpfu.itis.bagaviev.model.NewsFeedModel
import ru.kpfu.itis.bagaviev.recylerview.diffutils.NewsFeedDiffUtil
import ru.kpfu.itis.bagaviev.recylerview.viewholder.NewsAddViewHolder
import ru.kpfu.itis.bagaviev.recylerview.viewholder.NewsDateViewHolder
import ru.kpfu.itis.bagaviev.recylerview.viewholder.NewsFeedViewHolder
import ru.kpfu.itis.bagaviev.model.NewsItemType
import ru.kpfu.itis.bagaviev.model.NewsListItem

class NewsFeedAdapter(
    private val onNewsFeedItemClicked: (newsFeedModel: NewsFeedModel) -> Unit,
    private val onAddToFavouritesButtonClicked: (position: Int, newsFeedModel: NewsFeedModel) -> Unit,
    private val onAddNewsButtonClicked: () -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private var newsItemList = mutableListOf<NewsListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (viewType) {
            NewsItemType.NEWS_FEED_ITEM.value ->
                NewsFeedViewHolder(
                    viewBinding = ItemNewsFeedBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false),
                    onNewsFeedItemClicked = onNewsFeedItemClicked,
                    onAddToFavouritesButtonClicked = onAddToFavouritesButtonClicked)
            NewsItemType.NEWS_DATE_ITEM.value ->
                NewsDateViewHolder(
                    viewBinding = ItemNewsDateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false)
                )
            else ->
                NewsAddViewHolder(
                    viewBinding = ItemNewsAddBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false),
                    onAddNewsButtonClicked = onAddNewsButtonClicked
                )
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is NewsFeedViewHolder -> holder.bind(newsItemList[position] as NewsFeedModel)
            is NewsDateViewHolder -> holder.bind(newsItemList[position] as NewsDateModel)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (holder is NewsFeedViewHolder) {
            if (payloads.isNotEmpty()) {
                (payloads.first() as? Boolean)?.let {
                    holder.setAddToFavouritesButtonStatus(it)
                }
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return newsItemList[position].getType().value
    }

    override fun getItemCount() = newsItemList.size

    fun setItems(outItemsList: MutableList<NewsListItem>) {
        val diffUtil = NewsFeedDiffUtil(newsItemList, outItemsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        newsItemList = outItemsList
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateItem(position: Int, newsFeedModel: NewsFeedModel) {
        newsItemList[position] = newsFeedModel
        notifyItemChanged(position, newsFeedModel.isAddedToFavourites)
    }

    fun addItems(itemsList: List<NewsFeedModel>) {
        val diffUtil = NewsFeedDiffUtil(newsItemList, itemsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        itemsList.forEach {
            val index = (1 until itemsList.size).random()
            newsItemList.add(index, it)
        }
        diffResult.dispatchUpdatesTo(this)
    }

}