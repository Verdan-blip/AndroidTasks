package ru.kpfu.itis.bagaviev.textwatcher

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

class NewsCountTextWatcher(
    private val editText: EditText,
    private val actionButton: Button
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s?.takeIf { it.isNotEmpty() }?.also { charSequence ->
            val numericValue = charSequence.toString().toInt().let { number ->
                if (number > 45) 45
                else number
            }
            val newText = numericValue.toString()
            with (editText) {
                removeTextChangedListener(this@NewsCountTextWatcher)
                setText(newText)
                setSelection(newText.length)
                addTextChangedListener(this@NewsCountTextWatcher)
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        actionButton.isEnabled = editText.text.isNotEmpty()
    }
}