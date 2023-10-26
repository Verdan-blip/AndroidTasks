package ru.kpfu.itis.bagaviev.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.bagaviev.databinding.ItemAnswerBinding
import ru.kpfu.itis.bagaviev.model.AnswerGroup

class AnswerGroupAdapter(
    private val answerGroup: AnswerGroup,
    private val onRadioButtonChecked: () -> Unit
) : RecyclerView.Adapter<AnswerGroupAdapter.AnswerViewHolder>() {

    //Используется во избежании проблем с использованием notifyItemChanged до биндинга вьюшки
    private var viewHolderBinded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AnswerViewHolder(
        ItemAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        viewHolderBinded = false
        holder.bind(position)
        viewHolderBinded = true
    }

    override fun getItemCount() = answerGroup.getCount()

    inner class AnswerViewHolder(
        private val viewBinding: ItemAnswerBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        init {
            viewBinding.apply {
                rbItemAnswer.setOnCheckedChangeListener { _, isChecked ->
                    if (viewHolderBinded && isChecked) {
                        notifyItemChanged(answerGroup.selectedAnswerIndex)
                        answerGroup.selectedAnswerIndex = adapterPosition
                        notifyItemChanged(adapterPosition)
                        onRadioButtonChecked()
                    }
                }
            }
        }

        fun bind(position: Int) {
            viewBinding.apply {
                rbItemAnswer.isChecked = answerGroup.selectedAnswerIndex == position
                rbItemAnswer.isEnabled = answerGroup.selectedAnswerIndex != position
                rbItemAnswer.text = answerGroup.get(position)
            }
        }

    }

}