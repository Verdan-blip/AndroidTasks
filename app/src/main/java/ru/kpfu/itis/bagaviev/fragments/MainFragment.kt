package ru.kpfu.itis.bagaviev.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.kpfu.itis.bagaviev.MainActivity
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FragmentMainBinding
import ru.kpfu.itis.bagaviev.util.handlers.ReminderNotificationHandler

class MainFragment : Fragment(R.layout.fragment_main) {

    private var viewBinding: FragmentMainBinding? = null

    private var notificationsHandler: ReminderNotificationHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        notificationsHandler = ReminderNotificationHandler(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.apply {
            btnReminderCreate.setOnClickListener {
                val title = etReminderDatetime.text.toString()
                val text = etReminderText.text.toString()
                notificationsHandler?.sendNotification(title, text)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

}