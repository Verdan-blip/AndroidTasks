package ru.kpfu.itis.bagaviev.util

import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.model.NewsFeedModel

object NewsFeedRepository {

    private val newsFeedList = listOf(
        NewsFeedModel(
            0,
            "Заседание общественной палаты",
            "Вчера прошло мальдивское заседание общественной палаты на берегу какого-то там залива",
            R.drawable.news_item_1,
            false
        ),
        NewsFeedModel(
            1,
            "В России готовятся к празднованию Хэллоуина",
            "Хэллоуин - cовременный международный праздник, восходящий к традициям древних кельтов " +
                    "Ирландии и Шотландии, история которого началась на территории современных Великобритании " +
                    "и Северной Ирландии",
            R.drawable.news_item_2,
            false
        ),
        NewsFeedModel(
            2,
            "Дополнительный выходной в Татарстане",
            "6 ноября мы отмечаем знаменательный праздник для нашей республики – День принятия " +
                    "Конституции Республики Татарстан",
            R.drawable.news_item_3,
            false
        )
    )

    fun getAll() = newsFeedList.toList()

}