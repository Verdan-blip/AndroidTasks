package ru.kpfu.itis.bagaviev.utils

import java.lang.StringBuilder

object PhoneFormatMatcher {

    const val PHONE_NUMBER_FORMAT = "+7 (9XX) XXX XX-XX"
    const val NUMBER_DESIGNATION = 'X'

    private val changes = StringBuilder()

    fun matchToFormat(charSequence: CharSequence) : String {
        changes.clear()

        charSequence.onEach {
            var formatCursor = changes.length
            var formatChar = PHONE_NUMBER_FORMAT[formatCursor]

            while (formatChar != NUMBER_DESIGNATION && formatChar != it) {
                changes.append(formatChar)
                formatChar = PHONE_NUMBER_FORMAT[++formatCursor]
            }
            changes.append(it)
        }

        return changes.toString()
    }

}