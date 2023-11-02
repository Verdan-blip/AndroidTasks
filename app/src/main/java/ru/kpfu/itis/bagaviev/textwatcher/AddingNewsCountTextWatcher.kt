package ru.kpfu.itis.bagaviev.textwatcher

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class AddingNewsCountTextWatcher(
    private val editText: EditText
): TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s?.takeIf { it.isNotEmpty() }?.also { charSequence ->
            val numericValue = charSequence.toString().toInt().let { number ->
                if (number > 5) 5
                else number
            }
            val newText = numericValue.toString()
            with (editText) {
                removeTextChangedListener(this@AddingNewsCountTextWatcher)
                setText(newText)
                setSelection(newText.length)
                addTextChangedListener(this@AddingNewsCountTextWatcher)
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

}