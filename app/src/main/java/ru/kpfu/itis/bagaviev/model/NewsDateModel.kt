package ru.kpfu.itis.bagaviev.model

import java.util.Date

data class NewsDateModel(
    val date: Date
) : NewsListItem {

    override fun getType() = NewsItemType.NEWS_DATE_ITEM

    override fun equals(other: Any?): Boolean {
        return when (other) {
            null -> false
            is NewsFeedModel -> date == this.date
            else -> false
        }
    }

}