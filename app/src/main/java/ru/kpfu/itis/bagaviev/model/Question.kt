package ru.kpfu.itis.bagaviev.model

import android.os.Parcel
import android.os.Parcelable

data class Question(
    val number: Int,
    val text: String,
    val answerGroup: AnswerGroup,
) : Parcelable {

    fun isAnswered() = answerGroup.selectedAnswerIndex != -1

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeStringList(answerGroup.answers)
        dest.writeInt(answerGroup.selectedAnswerIndex)
        dest.writeInt(number)
        dest.writeString(text)
    }

    companion object {

        @JvmField
        val CREATOR = object : Parcelable.Creator<Question?> {

            override fun createFromParcel(source: Parcel?): Question? {
                var question: Question? = null
                source?.apply {
                    val answers = listOf<String>()
                    readStringList(answers)
                    val selectedAnswerIndex = readInt()
                    val number = readInt()
                    val text = readString() ?: ""
                    question = Question(number, text, AnswerGroup(answers, selectedAnswerIndex))
                }
                return question
            }

            override fun newArray(size: Int) = arrayOfNulls<Question?>(size)

        }
    }

}