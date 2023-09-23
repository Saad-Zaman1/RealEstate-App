package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityUpdatePropertyBinding

class UpdatePropertyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdatePropertyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent: Intent = intent

        
        if (intent.getStringExtra("city")!!.isNotEmpty()) {
            binding.buttonUpdate.text = "App Property"
        }
        binding.buttonUpdate.setOnClickListener {

        }
    }
}