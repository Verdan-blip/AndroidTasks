package ru.kpfu.itis.bagaviev.model

import java.io.Serializable

interface NewsListItem : Serializable {
    fun getType(): NewsItemType
}