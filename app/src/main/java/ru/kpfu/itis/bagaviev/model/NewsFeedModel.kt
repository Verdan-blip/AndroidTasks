package ru.kpfu.itis.bagaviev.model

import androidx.annotation.DrawableRes

data class NewsFeedModel(
    val id: Int,
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
    val isAddedToFavourites: Boolean
) : NewsListItem {

    override fun getType() = NewsItemType.NEWS_FEED_ITEM

    override fun equals(other: Any?): Boolean {
        return if (other == null) false
        else {
            if (other is NewsFeedModel) {
                id == other.id &&
                        title == other.title &&
                        description == other.description &&
                        image == other.image &&
                        isAddedToFavourites == other.isAddedToFavourites
            } else false
        }
    }
}