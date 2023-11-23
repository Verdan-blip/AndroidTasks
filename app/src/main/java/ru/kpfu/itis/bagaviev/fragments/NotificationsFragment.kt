package ru.kpfu.itis.bagaviev.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FragmentNotificationsBinding
import ru.kpfu.itis.bagaviev.util.*
import ru.kpfu.itis.bagaviev.util.settings.ReminderNotificationSettings

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private var viewBinding: FragmentNotificationsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNotificationsBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.apply {
            with(ReminderNotificationSettings) {

                //Init
                rgImportance.check(importanceToRadioButtonId(importance))
                rgVisibility.check(visibilityToRadioButtonId(visibility))
                cbShowTextFully.isChecked = showTextFully
                cbShowControlButtons.isChecked = showControlButtons

                //Listeners
                rgImportance.setOnCheckedChangeListener { _, checkedId ->
                    importance = radioButtonIdToImportance(checkedId)
                }

                rgVisibility.setOnCheckedChangeListener { _, checkedId ->
                    visibility = radioButtonIdToVisibility(checkedId)
                }

                cbShowTextFully.setOnCheckedChangeListener { _, isChecked ->
                    showTextFully = isChecked
                }

                cbShowControlButtons.setOnCheckedChangeListener { _, isChecked ->
                    showControlButtons = isChecked
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
