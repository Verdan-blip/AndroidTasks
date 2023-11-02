package ru.kpfu.itis.bagaviev.util

import ru.kpfu.itis.bagaviev.model.NewsAddModel
import ru.kpfu.itis.bagaviev.model.NewsDateModel
import ru.kpfu.itis.bagaviev.model.NewsFeedModel
import ru.kpfu.itis.bagaviev.model.NewsListItem
import java.util.Calendar

object NewsFeedHandler {

    fun take(newsList: List<NewsFeedModel>, count: Int) = MutableList(count) {
        newsList[(newsList.indices).random()]
    }

    fun structurize(newsItemList: List<NewsListItem>) : MutableList<NewsListItem> {
        val groupSize = 8
        val changed = mutableListOf<NewsListItem>()
        changed.addAll(newsItemList)
        for (i in newsItemList.indices step (groupSize + 1)) {
            changed.add(i, NewsDateModel(Calendar.getInstance().time))
        }
        changed.add(0, NewsAddModel())
        return changed
    }

}