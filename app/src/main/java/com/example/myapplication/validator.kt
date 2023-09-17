package com.example.myapplication

import android.content.Context
import android.widget.EditText

class Validator(private val context: Context) {
    fun validateUserName(text: String): String {
        if (text.isNotEmpty()) {
            if (text.length > 15) {
                return "Username is too long"
            } else if (!text.matches(Regex("^[a-zA-Z]+$"))) {
                return context.resources.getString(R.string.Uname_noNum)
            }
        } else {
            return "Please enter Username"
        }
        return ""
    }

    fun validatePhone(text: String): String {
        if (text.isNotEmpty()) {
            if (text.length > 12) {
                return "Please enter a valid number"
            }
        } else {
            return "Please enter phone number"
        }
        return ""
    }

    fun validateEmail(text: String): String {
        if (text.isNotEmpty()) {
            if (!text.matches(Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"))) {
                return "Please enter valid email"
            }
        } else {
            return "Please enter email"
        }
        return ""
    }

    fun validatePassword(text: String): String {
        if (text.isNotEmpty()) {
            if (text.length < 9) {
                return "Password should be greater than 8"
            }
        } else {
            return "Please set a password"
        }
        return ""
    }
}