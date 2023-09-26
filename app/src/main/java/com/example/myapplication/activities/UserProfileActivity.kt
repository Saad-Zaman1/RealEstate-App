package com.example.myapplication.activities

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.dataStorage.room.DataBaseBuilder
import com.example.myapplication.dataStorage.room.user.UserEntity
import com.example.myapplication.databinding.ActivityUserProfileBinding
import com.example.myapplication.global.GlobalVariables
import com.example.myapplication.utils.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserProfileActivity : AppCompatActivity() {
    private lateinit var userData: UserEntity
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var username: String
    private lateinit var userPhone: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sP = SharedPrefs(this)
        val userEmail = sP.getString(GlobalVariables.userEmail, "")

        val database = DataBaseBuilder.getInstance(this)

        database.userDao().validateEmail(userEmail).observe(this) {
            userData = it
            username = it.username
            userPhone = it.phone
            binding.editTextUsername.setText(username)
            binding.editTextPhone.setText(userPhone)
        }


//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                userData = database.userDao().validateEmail(userEmail)!!
//                withContext(Dispatchers.Main) {
//                    binding.editTextUsername.setText(userData.username)
//                    binding.editTextPhone.setText(userData.phone)
//                }
//            } catch (e: Exception) {
//                Log.e("Database Error", "Error updating property: ${e.message}", e)
//                // Handle the error as needed, e.g., display an error toast
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                        this@UserProfileActivity,
//                        "Error updating property",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
        binding.buttonUpdate.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val userPhone = binding.editTextPhone.text.toString()

            val nameError = Validator.validateUserName(username)
            val phoneError = Validator.validatePhone(userPhone)

            if (nameError.isNotEmpty()) {
                binding.editTextUsername.error = nameError
                return@setOnClickListener
            }

            if (phoneError.isNotEmpty()) {
                binding.editTextPhone.error = phoneError
                return@setOnClickListener
            }

            val updatedUser = UserEntity(
                userData.id,
                username,
                userPhone,
                userEmail,
                userData.password
            )

            CoroutineScope(Dispatchers.IO).launch {

                database.userDao().updateUser(updatedUser)
            }
            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
            binding.editTextUsername.setText("")
            binding.editTextPhone.setText("")
        }
    }
}
