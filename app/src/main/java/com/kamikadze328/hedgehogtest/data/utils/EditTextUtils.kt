package com.kamikadze328.hedgehogtest.data.utils

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

class NumbersInputFilter : InputFilter {

    private val regex = Pattern.compile("^[0-9]*$")

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher = regex.matcher(source)
        return if (matcher.find()) {
            null
        } else {
            ""
        }
    }
}