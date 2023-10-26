package ru.kpfu.itis.bagaviev.utils.text_watchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import ru.kpfu.itis.bagaviev.utils.PhoneFormatMatcher

class PhoneNumberTextWatcher(
    private val etPhoneNumber: EditText,
    private val etQuestionsCount: EditText,
    private val buttonToControl: Button
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s?.also {
            val formatted = PhoneFormatMatcher.matchToFormat(it)
            etPhoneNumber.apply {
                removeTextChangedListener(this@PhoneNumberTextWatcher)
                setText(formatted)
                setSelection(formatted.length)
                addTextChangedListener(this@PhoneNumberTextWatcher)
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        if (etQuestionsCount.text.toString().isNotEmpty()) {
            buttonToControl.isEnabled =
                etPhoneNumber.text.toString().length == 18 && etQuestionsCount.text.toString().toInt() >= 9
        }
    }

}