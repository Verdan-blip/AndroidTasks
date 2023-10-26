package ru.kpfu.itis.bagaviev.model

import java.io.Serializable


data class AnswerGroup(
    val answers: List<String>,
    var selectedAnswerIndex: Int
) : Serializable {
    fun get(index: Int) = answers[index]
    fun getCount() = answers.size
}