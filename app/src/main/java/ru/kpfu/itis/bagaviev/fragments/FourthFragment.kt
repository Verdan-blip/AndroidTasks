package ru.kpfu.itis.bagaviev.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FragmentFourthBinding
import ru.kpfu.itis.bagaviev.utils.SimpleMessageUtil

class FourthFragment : Fragment(R.layout.fragment_fourth) {

    private var viewBinding: FragmentFourthBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentFourthBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newMessage = arguments?.getString(SimpleMessageUtil.MESSAGE_KEY) ?: ""
        if (newMessage.isNotEmpty()) {
            messagesDeque.addFirst(newMessage)
            messagesDeque.removeLast()
        }

        viewBinding?.apply {
            tvFirst.text = messagesDeque[0]
            tvSecond.text = messagesDeque[1]
            tvThird.text = messagesDeque[2]
        }

    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

    companion object {

        private const val DEFAULT_TEXT_MESSAGE = "Empty"
        private val messagesDeque = ArrayDeque(generateSequence { DEFAULT_TEXT_MESSAGE }
            .take(3)
            .toMutableList())

        fun newInstance(messageKey: String, nextMessage: String) =
            FourthFragment().apply {
                arguments = Bundle().apply {
                    putString(messageKey, nextMessage)
                }
            }
    }

}