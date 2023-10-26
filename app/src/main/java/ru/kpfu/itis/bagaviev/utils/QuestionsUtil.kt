package ru.kpfu.itis.bagaviev.utils

import ru.kpfu.itis.bagaviev.model.Question

object QuestionsUtil {

    fun allQuestionsAnsweredSkippingLast(questions: Array<Question>): Boolean {
        questions.forEachIndexed {
            index, question ->
            if (index != questions.size - 2 && (question.isAnswered()))
                return false
        }
        return true
    }

}