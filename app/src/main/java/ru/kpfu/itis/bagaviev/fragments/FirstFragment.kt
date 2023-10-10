package ru.kpfu.itis.bagaviev.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kpfu.itis.bagaviev.MainActivity
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FragmentFirstBinding
import ru.kpfu.itis.bagaviev.utils.ActionType
import ru.kpfu.itis.bagaviev.utils.SimpleMessageUtil

class FirstFragment : Fragment(R.layout.fragment_first) {

    private var viewBinding: FragmentFirstBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentFirstBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity

        viewBinding?.run {
            btnToThirdFragment.setOnClickListener {

                val message = etMessage.text.toString()

                activity.navigateTo(
                    ActionType.REPLACE,
                    SecondFragment.newInstance(
                        SimpleMessageUtil.MESSAGE_KEY,
                        message
                ), true)

                activity.navigateTo(
                    ActionType.REPLACE,
                    ThirdFragment.newInstance(
                        SimpleMessageUtil.MESSAGE_KEY,
                        message),
                    true)

            }
        }

    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

}