package ru.kpfu.itis.bagaviev.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.itis.bagaviev.databinding.FragmentQuestionBinding
import ru.kpfu.itis.bagaviev.model.Question
import ru.kpfu.itis.bagaviev.utils.QuestionsUtil

class QuestionnaireAdapter(
    private val questions: Array<Question>,
    private val onQuestionnaireCompleted: () -> Unit
) : RecyclerView.Adapter<QuestionnaireAdapter.QuestionViewHolder>() {

    private var canFinish = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            FragmentQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions, position)
    }

    override fun getItemCount() = questions.size

    inner class QuestionViewHolder(
        private val viewBinding: FragmentQuestionBinding,
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        init {
            viewBinding.apply {
                btnFinish.setOnClickListener {
                    Snackbar.make(root, "Опрос пройден!", Snackbar.LENGTH_SHORT).show()
                    onQuestionnaireCompleted()
                }
            }
        }

        fun bind(questions: Array<Question>, position: Int) {
            val question = questions[position]
            viewBinding.apply {
                tvPageNumber.text = question.number.toString()
                tvQuestion.text = question.text
                if (canFinish) btnFinish.visibility = Button.VISIBLE
                rvAnswers.adapter = AnswerGroupAdapter(
                    answerGroup = question.answerGroup,
                    onRadioButtonChecked = {
                        if (!canFinish && questions.all { question -> question.isAnswered() }) {
                            canFinish = true
                            this@QuestionnaireAdapter.notifyDataSetChanged()
                        }
                    }
                )
            }
        }

    }

}