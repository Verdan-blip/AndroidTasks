package ru.kpfu.itis.bagaviev.adapters

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.kpfu.itis.bagaviev.model.Question
import ru.kpfu.itis.bagaviev.ui.fragments.QuestionFragment
import ru.kpfu.itis.bagaviev.utils.QuestionsUtil

class QuestionnaireAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val questions: Array<Question>,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int) =
        QuestionFragment.newInstance(questions, position, QuestionsUtil.allQuestionsAnsweredSkippingLast(questions))

    override fun getItemCount() = questions.size

}