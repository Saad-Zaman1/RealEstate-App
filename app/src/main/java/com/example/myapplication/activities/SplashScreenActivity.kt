package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.myapplication.R
import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.global.GlobalVariables

class SplashScreenActivity : AppCompatActivity() {
    lateinit var iv_home: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val sp = SharedPrefs(this)
        iv_home = findViewById(R.id.iv_home)
        iv_home.alpha = 0f
        iv_home.animate().setDuration(1000).alpha(1f).withEndAction {
            if (!sp.getBoolean(GlobalVariables.isLoggedIn)) {
                val i = Intent(this, SignupActivity::class.java)
                startActivity(i)
                finish()

            } else {
                val i = Intent(this, HomePageActivity::class.java)
                startActivity(i)
                finish()

            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

    }
}