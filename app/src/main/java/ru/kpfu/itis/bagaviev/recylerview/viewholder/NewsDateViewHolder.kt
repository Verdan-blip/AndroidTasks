package ru.kpfu.itis.bagaviev.recylerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.bagaviev.databinding.ItemNewsDateBinding
import ru.kpfu.itis.bagaviev.model.NewsDateModel
import ru.kpfu.itis.bagaviev.model.NewsFeedModel

class NewsDateViewHolder(
    private val viewBinding: ItemNewsDateBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(newsDateModel: NewsDateModel) {
        viewBinding.tvNewsDate.setText(newsDateModel.date.toString())
    }

}