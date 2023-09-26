package com.example.myapplication.utils

import android.content.Context
import com.example.myapplication.R

object Validator {
    fun validateUserName(text: String): String {
        if (text.isNotEmpty()) {
            if (text.length > 15) {
                return "Username is too long"
            } else if (!text.matches(Regex("^[a-zA-Z]+$"))) {
                return "Username must not contain any number or special character"
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
            if (!text.matches(Regex("[a-zA-Z0-9._-]+@(gmail\\.com|yahoo\\.com)$"))) {
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

    // Property validation
    fun checkEmpty(text: String, name: String): String {
        if (text.isEmpty()) {
            return "Please enter $name"
        } else if (name == "price" && text.length < 3) {
            return "Length should be greater than 3"
        } else if (text.length < 10 && name != "price") {
            return "Length should be greater than 10"
        }
        return ""
    }

    fun validateSpinner(text: String): String {
        if (text.contains("Select")) {
            return "Please enter something"
        }
        return ""
    }
}