package com.example.contanctmanagerapp.util

import android.content.Context
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.contanctmanagerapp.R

fun ImageView.styleState(isActivate: Boolean, context: Context) {
    val isDark = Handlers.isDark(context)
    if (isActivate) {
        if (isDark)
            activateDarkStyle(this, context)
        else
            activateLightStyle(this, context)
    } else {
        if (isDark)
            disableDarkStyle(this, context)
        else
            disableLightStyle(this, context)
    }
}

private fun disableLightStyle(imageView: ImageView, context: Context) {
    imageView.setBackgroundResource(R.color.light_background)
    imageView.setColorFilter(
        ContextCompat.getColor(context, R.color.light_secondary_lighter),
        PorterDuff.Mode.SRC_IN
    )
}

private fun disableDarkStyle(imageView: ImageView, context: Context) {
    imageView.setBackgroundResource(R.color.dark_background)
    imageView.setColorFilter(
        ContextCompat.getColor(context, R.color.dark_secondary_darker),
        PorterDuff.Mode.SRC_IN
    )
}

private fun activateLightStyle(imageView: ImageView, context: Context) {
    imageView.setBackgroundResource(R.color.light_background)
    imageView.setColorFilter(
        ContextCompat.getColor(context, R.color.light_primary),
        PorterDuff.Mode.SRC_IN
    )
}

private fun activateDarkStyle(imageView: ImageView, context: Context) {
    imageView.setBackgroundResource(R.color.dark_background)
    imageView.setColorFilter(
        ContextCompat.getColor(context, R.color.dark_primary),
        PorterDuff.Mode.SRC_IN
    )
}

