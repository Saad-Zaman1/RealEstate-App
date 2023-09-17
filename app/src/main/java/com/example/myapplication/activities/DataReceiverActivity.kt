package com.example.myapplication.activities


import android.content.Intent
import android.net.Uri

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.databinding.DatareceiverActivityBinding
import com.example.myapplication.global.GlobalVariables

class DataReceiverActivity : AppCompatActivity() {
    private lateinit var binding: DatareceiverActivityBinding

    //    private val sharedPref = SharedPrefs(this@DataReceiverActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DatareceiverActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val bundle = intent.extras

        binding.tvNameMainActivity.text = bundle?.getString(GlobalVariables.userName) ?: ""
        binding.tvEmailMainActivity.text = bundle?.getString(GlobalVariables.userEmail) ?: ""
        binding.tvPhone.text = bundle?.getString(GlobalVariables.userPhone) ?: ""
        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${binding.tvPhone.text}"))
            Toast.makeText(this, "Dialing", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
//        binding.tvNamePrefs.text = sharedPref.getString(GlobalVariables.userName, "")
//        binding.tvEmailPrefs.text = sharedPref.getString(GlobalVariables.userEmail, "")
        binding.btnLogout.setOnClickListener {
//            sharedPref.clearPrefs()
//            sharedPref.saveBoolean(GlobalVariables.isLoggedIn, false)
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

}