package com.example.myapplication.activities

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.myapplication.R.layout.activity_user_profile)
        title = "Profile"
        val age: MutableList<String> = ArrayList()
        for (i in 1..100) {
            age.add(Integer.toString(i))
        }

        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, R.layout.simple_spinner_item, age
        )
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_dropdown_item_1line)

        val spinner = findViewById<View>(com.example.myapplication.R.id.spinner) as Spinner
        spinner.adapter = spinnerArrayAdapter

    }
}