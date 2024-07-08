package com.example.contanctmanagerapp.util

import android.graphics.PorterDuff
import android.widget.ImageView

fun ImageView.setProfileImage(color: Int) {
    this.setColorFilter(
        color,
        PorterDuff.Mode.SRC_IN
    )
}