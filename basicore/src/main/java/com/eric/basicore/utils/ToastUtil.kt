package com.eric.basicore.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun showToast(context: Context?, text: CharSequence?) {
    context?.let {
        if (!text.isNullOrEmpty()) {
            Toast.makeText(it, text, Toast.LENGTH_SHORT).show()
        }
    }
}

fun showToast(context: Context?, @StringRes resId: Int) {
    context?.let {
        Toast.makeText(it, resId, Toast.LENGTH_SHORT).show()
    }
}

fun showToastLong(context: Context?, text: CharSequence?) {
    context?.let {
        if (!text.isNullOrEmpty()) {
            Toast.makeText(it, text, Toast.LENGTH_LONG).show()
        }
    }
}

fun showToastLong(context: Context?, @StringRes resId: Int) {
    context?.let {
        Toast.makeText(it, resId, Toast.LENGTH_LONG).show()
    }
}

