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

        val activity = requireActivity() as MainActivity

        viewBinding?.apply {

            val message = arguments?.getString(SimpleMessageUtil.MESSAGE_KEY)

            if (message.isNullOrEmpty()) {
                tvSecondScreen.text = getString(R.string.second_fragment_default_text)
            } else {
                tvSecondScreen.text = message
            }

            btnToFirstFragment.setOnClickListener {
                parentFragmentManager.popBackStack()
                activity.doTransaction(
                    ActionType.REMOVE,
                    activity.fragmentContainerId,
                    this@SecondFragment,
                    false)
            }

            btnToThirdFragment.setOnClickListener {
                parentFragmentManager.popBackStack()
                activity.doTransaction(
                    ActionType.REPLACE,
                    activity.fragmentContainerId,
                    ThirdFragment.newInstance(SimpleMessageUtil.MESSAGE_KEY, message ?: ""),
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