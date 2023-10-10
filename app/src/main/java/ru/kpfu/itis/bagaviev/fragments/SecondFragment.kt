package ru.kpfu.itis.bagaviev.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.itis.bagaviev.MainActivity
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FragmentSecondBinding
import ru.kpfu.itis.bagaviev.utils.ActionType
import ru.kpfu.itis.bagaviev.utils.SimpleMessageUtil

class SecondFragment : Fragment(R.layout.fragment_second) {

    private var viewBinding: FragmentSecondBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSecondBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.apply {
            var message = arguments?.getString(SimpleMessageUtil.MESSAGE_KEY)
            if (message.isNullOrEmpty())
                message = getString(R.string.second_fragment_default_text)
            tvSecondScreen.text = message
        }

        val activity = requireActivity() as MainActivity

        viewBinding?.apply {
            btnToFirstFragment.setOnClickListener {
                parentFragmentManager.popBackStack()
                activity.navigateTo(
                    ActionType.REMOVE,
                    this@SecondFragment,
                    false)
            }
            btnToThirdFragment.setOnClickListener {
                parentFragmentManager.popBackStack()
                activity.navigateTo(
                    ActionType.REPLACE,
                    ThirdFragment.newInstance(
                        SimpleMessageUtil.MESSAGE_KEY,
                        tvSecondScreen.text.toString()
                    ),
                    true
                )
            }
        }

    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(messageKey: String, message: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(messageKey, message)
                }
            }
    }


}