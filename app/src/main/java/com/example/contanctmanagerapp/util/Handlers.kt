package com.example.contanctmanagerapp.util

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color

object Handlers {
    fun isDark(context: Context): Boolean {
        val nightModeFlags =
            context.resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    fun generateRandomProfilePicture(): Int {
        val colors = listOf(
            "#4A249D", "#009FBD", "#3FA2F6", "#FFC7ED", "#939185",
            "#597445", "#FF4191", "#E6B9A6", "#36BA98", "#F4A261"
        )
        val randomIndex = colors.indices.random()
        return Color.parseColor(colors[randomIndex])
    }

}