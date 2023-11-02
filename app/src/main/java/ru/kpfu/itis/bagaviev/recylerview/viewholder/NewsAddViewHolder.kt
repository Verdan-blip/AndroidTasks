package ru.kpfu.itis.bagaviev.recylerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.bagaviev.databinding.ItemNewsAddBinding

class NewsAddViewHolder(
    private val viewBinding: ItemNewsAddBinding,
    private val onAddNewsButtonClicked: () -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {

    init {
        viewBinding.btnAddNews.setOnClickListener {
            onAddNewsButtonClicked()
        }
    }

}