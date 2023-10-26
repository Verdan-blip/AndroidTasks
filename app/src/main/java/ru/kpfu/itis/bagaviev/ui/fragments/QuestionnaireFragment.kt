package ru.kpfu.itis.bagaviev.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.adapters.QuestionnaireAdapter
import ru.kpfu.itis.bagaviev.utils.QuestionGenerator
import ru.kpfu.itis.bagaviev.utils.callbacks.OnQuestionPageChangeCallback


class QuestionnaireFragment : Fragment(R.layout.fragment_questionnaire) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_questionnaire, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questionsCount = arguments?.getInt(QUESTIONS_COUNT) ?: 0
        val questions = QuestionGenerator.generateQuestions(questionsCount)

        //Для сохранения плавных анимаций при цикличной прокрутке добавляем фейковые страницы
        val fakeFirstQuestion = questions.first()
        val fakeLastQuestion = questions.last()

        val questionsWithFakes = Array(questionsCount + 2) {
            when (it) {
                0 -> fakeLastQuestion
                questionsCount + 1 -> fakeFirstQuestion
                else -> questions[it - 1]
            }
        }

        val viewPager2 = requireActivity().findViewById<ViewPager2>(R.id.vp2_questionnaire)
        val questionnaireAdapter = QuestionnaireAdapter(questions = questionsWithFakes, onQuestionnaireCompleted = {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fv_container, StartFragment())
                .commit()
        })
        viewPager2.adapter = questionnaireAdapter
        viewPager2.registerOnPageChangeCallback(OnQuestionPageChangeCallback(viewPager2))
    }

    companion object {
        const val QUESTIONS_COUNT = "questionsCount"
        fun newInstance(questionsCount: Int) = QuestionnaireFragment().apply {
            arguments = Bundle().apply {
                putInt(QUESTIONS_COUNT, questionsCount)
            }
        }
    }

}