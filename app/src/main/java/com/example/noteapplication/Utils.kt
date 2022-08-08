package com.example.noteapplication

import android.content.Context
import android.widget.Toast

class Utils {

    companion object {
        inline fun Context.toast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

}