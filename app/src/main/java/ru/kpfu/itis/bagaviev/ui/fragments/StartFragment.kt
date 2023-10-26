package ru.kpfu.itis.bagaviev.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FragmentStartBinding
import ru.kpfu.itis.bagaviev.utils.listeners.PhoneNumberFocusChangeListener
import ru.kpfu.itis.bagaviev.utils.text_watchers.PhoneNumberTextWatcher
import ru.kpfu.itis.bagaviev.utils.text_watchers.QuestionsCountTextWatcher

class StartFragment : Fragment(R.layout.fragment_start) {

    private var viewBinding: FragmentStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentStartBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.apply {
            etPhoneNumber.addTextChangedListener(
                PhoneNumberTextWatcher(etPhoneNumber, etQuestionsNumber, btnStart)
            )
            etPhoneNumber.onFocusChangeListener = PhoneNumberFocusChangeListener(etPhoneNumber)
            etQuestionsNumber.addTextChangedListener(
                QuestionsCountTextWatcher(etPhoneNumber, etQuestionsNumber, btnStart)
            )
            btnStart.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fv_container,
                        QuestionnaireFragment.newInstance(
                            etQuestionsNumber.text.toString().toInt())
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

}