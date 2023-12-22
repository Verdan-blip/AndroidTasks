package ru.kpfu.itis.bagaviev.ui.fragments.base

import android.app.AlertDialog
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected fun showInformingDialog(
        title: String,
        message: String,
        onDismiss: () -> Unit = { }
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setOnDismissListener { onDismiss() }
            .create()
            .show()
    }

}