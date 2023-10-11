package ru.kpfu.itis.bagaviev.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FragmentThirdBinding
import ru.kpfu.itis.bagaviev.utils.SimpleMessageUtil

class ThirdFragment : Fragment(R.layout.fragment_third) {

    private var viewBinding: FragmentThirdBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentThirdBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.apply {
            val text = arguments?.getString(SimpleMessageUtil.MESSAGE_KEY)
            if (text.isNullOrEmpty()) {
                tvThirdScreen.text = getString(R.string.third_fragment_default_text)
            } else {
                tvThirdScreen.text = text
            }
        }
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(messageKey: String, message: String) =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(messageKey, message)
                }
            }
    }

}