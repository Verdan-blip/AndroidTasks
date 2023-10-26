package ru.kpfu.itis.bagaviev.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.adapters.AnswerGroupAdapter
import ru.kpfu.itis.bagaviev.databinding.FragmentQuestionBinding
import ru.kpfu.itis.bagaviev.model.Question
import ru.kpfu.itis.bagaviev.utils.QuestionsUtil

class QuestionFragment : Fragment(R.layout.fragment_question) {

    private var viewBinding: FragmentQuestionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentQuestionBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArray(QUESTIONS_KEY, Question::class.java) as Array<Question?>
        } else {
            arguments?.getParcelableArray(QUESTIONS_KEY) as Array<Question?>
        }

        val isAllQuestionsAnsweredSkippingLast = arguments?.getBoolean(
            QUESTIONS_IS_ALL_QUESTIONS_ANSWERED_SKIPPING_LAST) ?: false
        val questionIndex = arguments?.getInt(QUESTIONS_INDEX_KEY) ?: 0
        val question = questions[questionIndex]

        question?.apply {
            viewBinding?.apply {
                tvPageNumber.text = question.number.toString()
                tvQuestion.text = question.text
                if (isAllQuestionsAnsweredSkippingLast) {
                    btnFinish.visibility = Button.VISIBLE
                }
                btnFinish.setOnClickListener {
                    Snackbar.make(root, "Questionnaire finished!", Snackbar.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fv_container, StartFragment())
                        .commit()
                }
                rvAnswers.adapter = AnswerGroupAdapter(
                    answerGroup = question.answerGroup,
                    onRadioButtonChecked = { adapter, viewHolder, answerGroup ->
                        adapter.notifyItemChanged(answerGroup.selectedAnswerIndex)
                        answerGroup.selectedAnswerIndex = viewHolder.adapterPosition
                        adapter.notifyItemChanged(answerGroup.selectedAnswerIndex)
                        if (QuestionsUtil.allQuestionsAnsweredSkippingLast(questions)) {
                            btnFinish.isVisible = true
                        }
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

    companion object {
        const val QUESTIONS_KEY = "questionsKey"
        const val QUESTIONS_INDEX_KEY = "questionsIndexKey"
        const val QUESTIONS_IS_ALL_QUESTIONS_ANSWERED_SKIPPING_LAST = "questionIsAllQuestionsAnsweredSkippingLast"
        fun newInstance(questions: Array<Question>, index: Int, isAllQuestionsAnsweredSkippingLast: Boolean) = QuestionFragment()
            .apply {
                arguments = Bundle().apply {
                    putParcelableArray(QUESTIONS_KEY, questions)
                    putInt(QUESTIONS_INDEX_KEY, index)
                    putBoolean(QUESTIONS_IS_ALL_QUESTIONS_ANSWERED_SKIPPING_LAST, isAllQuestionsAnsweredSkippingLast)
                }
            }
    }

}