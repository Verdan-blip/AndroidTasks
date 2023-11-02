package ru.kpfu.itis.bagaviev.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kpfu.itis.bagaviev.MainActivity
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FragmentStartBinding
import ru.kpfu.itis.bagaviev.textwatcher.NewsCountTextWatcher
import ru.kpfu.itis.bagaviev.util.ActionType

class StartFragment : Fragment(R.layout.fragment_start) {

    private var viewBinding: FragmentStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentStartBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.apply {
            etNewsCount.addTextChangedListener(
                NewsCountTextWatcher(editText = etNewsCount, actionButton = btnShow)
            )
            btnShow.setOnClickListener {
               (requireActivity() as MainActivity)
                    .navigateTo(
                        ActionType.REPLACE,
                        R.id.fv_container,
                        NewsFeedFragment.newInstance(etNewsCount.text.toString().toInt()),
                        true
                    )
            }
        }

    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

}