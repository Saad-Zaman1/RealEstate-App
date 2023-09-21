package com.example.myapplication.activities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.databinding.HomepageActivityBinding
import com.example.myapplication.global.GlobalVariables

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding: HomepageActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomepageActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val sharedPref = SharedPrefs(this@HomePageActivity)
//        binding.tvNamePrefs.text = sharedPref.getString(GlobalVariables.userName, "")
//        binding.tvEmailPrefs.text = sharedPref.getString(GlobalVariables.userEmail, "")
        binding.ibLogout.setOnClickListener {
            sharedPref.clearPrefs()
            sharedPref.saveBoolean(GlobalVariables.isLoggedIn, false)

            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }
        binding.btnUsers.setOnClickListener {
            val intent = Intent(this, RecyclerActivity::class.java)
            startActivity(intent)
        }
        binding.btnAddproperty.setOnClickListener {
            val intent = Intent(this, AddPropertyActivity::class.java)
            startActivity(intent)
        }
    }

}