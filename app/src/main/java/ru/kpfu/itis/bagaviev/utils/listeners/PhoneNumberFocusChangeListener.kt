package ru.kpfu.itis.bagaviev.utils.listeners

import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import ru.kpfu.itis.bagaviev.utils.PhoneFormatMatcher

class PhoneNumberFocusChangeListener(
    private val etPhoneNumber: EditText
    ) : OnFocusChangeListener {

    companion object {
        const val NUMBER_INIT_CHARS_COUNT = 5
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            etPhoneNumber.setText(
                PhoneFormatMatcher.PHONE_NUMBER_FORMAT.take(
                    NUMBER_INIT_CHARS_COUNT
                ))
            etPhoneNumber.setSelection(NUMBER_INIT_CHARS_COUNT)
        } else {
            if (etPhoneNumber.length() == NUMBER_INIT_CHARS_COUNT) {
                etPhoneNumber.text.clear()
            }
        }
    }
}