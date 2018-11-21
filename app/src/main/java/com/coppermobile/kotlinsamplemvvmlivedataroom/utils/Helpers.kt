package com.coppermobile.kotlinsamplemvvmlivedataroom.utils

import android.text.format.DateFormat
import java.util.*

object Helpers {

    fun formatDate(): String {
        return DateFormat.format("MMM d yy", Date()) as String
    }
}