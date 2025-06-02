package com.example.core_android.util


import android.content.Context
import android.content.Intent

fun Context.shareArticle(text: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    if (shareIntent.resolveActivity(packageManager) != null) {
        startActivity(Intent.createChooser(shareIntent, null))
    }
}
