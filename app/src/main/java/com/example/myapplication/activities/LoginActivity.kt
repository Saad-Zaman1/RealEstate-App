package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.dataStorage.room.DataBaseBuilder
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.global.GlobalVariables
import com.example.myapplication.utils.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val userDb = DataBaseBuilder.getInstance(this)

        val sP = SharedPrefs(this)


        binding.btnLogin.setOnClickListener {

            val emailError = Validator.validateEmail(binding.etEmailLogin.text.toString().trim())
            if (emailError.isNotEmpty()) {
                binding.etEmailLogin.error = emailError
                return@setOnClickListener
            }

            GlobalScope.launch {
                try {
                    val isExistEmail = userDb.userDao().validateEmail(
                        binding.etEmailLogin.text.toString().trim()
                    )
                    val isExistPass = userDb.userDao().findPassWithEmail(
                        binding.etEmailLogin.text.toString().trim()
                    )
                    Log.i("isExist", "$isExistEmail")
                    CoroutineScope(Dispatchers.Main).launch {
                        if (isExistEmail == null) {
                            binding.etEmailLogin.error = "Email not exists"
                        } else if (isExistPass != binding.etPassLogin.text.toString()) {
                            binding.etPassLogin.error = "Wrong password"
                        } else {
                            sP.saveBoolean(GlobalVariables.isLoggedIn, true)
                            sP.saveString(
                                GlobalVariables.userEmail,
                                "${binding.etEmailLogin.text.toString().trim()}"
                            )
                            val intent = Intent(this@LoginActivity, HomePageActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

                } catch (e: Exception) {
                    // Handle exceptions that might occur during the database operation.
                    Log.e("Database Error", "Error checking user existence: ${e.message}", e)
                    Toast.makeText(
                        this@LoginActivity,
                        "Error checking user existence",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        binding.tvGotosignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}