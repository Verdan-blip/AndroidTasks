package ru.kpfu.itis.bagaviev.utils

import ru.kpfu.itis.bagaviev.model.AnswerGroup
import ru.kpfu.itis.bagaviev.model.Question

object QuestionGenerator {

    private fun generateAnswerGroup(count: Int) = AnswerGroup(
        selectedAnswerIndex = -1,
        answers = List(count) {
            index -> "Ответ номер ${index}"
        }
    )

    fun generateQuestions(count: Int) = Array(count) {
            index: Int -> Question(
                number = index + 1,
                text = "Вопрос",
                answerGroup = generateAnswerGroup((5..10).random())
            )
    }

}