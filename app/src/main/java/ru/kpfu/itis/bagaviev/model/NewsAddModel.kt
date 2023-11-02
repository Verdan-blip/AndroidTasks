package ru.kpfu.itis.bagaviev.model

class NewsAddModel : NewsListItem {
    override fun getType() = NewsItemType.NEWS_ADD_ITEM
}