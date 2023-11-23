package ru.kpfu.itis.bagaviev.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.bagaviev.MainActivity
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FragmentCoroutinesBinding
import ru.kpfu.itis.bagaviev.util.handlers.CoroutinesNotificationsHandler
import ru.kpfu.itis.bagaviev.util.settings.CoroutinesSettings

class CoroutinesFragment : Fragment(R.layout.fragment_coroutines) {

    private var viewBinding: FragmentCoroutinesBinding? = null

    private var notificationsHandler: CoroutinesNotificationsHandler? = null

    private var job: Job? = null

    private fun setViewDefaultValues(viewBinding: FragmentCoroutinesBinding) {
        viewBinding.apply {
            sbCoroutinesCount.progress = CoroutinesSettings.DEFAULT_COROUTINES_COUNT
            sbCoroutinesCount.max = CoroutinesSettings.MAX_COROUTINES_COUNT
            cbAsync.isChecked = CoroutinesSettings.DEFAULT_ASYNC_STATE
            cbStopOnBackground.isChecked = CoroutinesSettings.DEFAULT_STOP_ON_BACKGROUND_STATE
        }
    }

    private fun launchCoroutinesWithSettings(button: Button) {

        //If user presses "Back" button, context will be missed
        val context = requireContext()

        job = requireActivity().lifecycleScope.launch(Dispatchers.IO) {
            repeat(CoroutinesSettings.count) {
                if (CoroutinesSettings.async) {
                    launch { coroutineJob() }
                } else {
                    coroutineJob()
                }
            }
        }
        job?.invokeOnCompletion {
            (context as MainActivity).runOnUiThread {
                button.isEnabled = true
            }
            if (job?.isCancelled == false) {
                notificationsHandler?.sendNotification()
            }
        }
    }

    private suspend fun coroutineJob() {
        delay(CoroutinesSettings.COROUTINES_DURATION_IN_MILLIS)
        Log.d("COROUTINE", "Completed")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_STOP && CoroutinesSettings.stopOnBackground) {
                    job?.also {
                        if (it.isActive) {
                            job?.cancel()
                            Log.d("COROUTINE", "REMOVED")
                        }
                    }
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        notificationsHandler = CoroutinesNotificationsHandler(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCoroutinesBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.apply {
            setViewDefaultValues(this)

            sbCoroutinesCount.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    CoroutinesSettings.count = progress
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) { }

                override fun onStopTrackingTouch(seekBar: SeekBar?) { }

            })

            cbAsync.setOnCheckedChangeListener { _, isChecked ->
                CoroutinesSettings.async = isChecked
            }

            cbStopOnBackground.setOnCheckedChangeListener { _, isChecked ->
                CoroutinesSettings.stopOnBackground = isChecked
            }

            btnRun.setOnClickListener {
                it.isEnabled = false
                launchCoroutinesWithSettings(it as Button)
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

}