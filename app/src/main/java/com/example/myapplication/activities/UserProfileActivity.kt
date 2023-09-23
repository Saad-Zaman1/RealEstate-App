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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sP = SharedPrefs(this)
        val userEmail = sP.getString(GlobalVariables.userEmail, "")

        val database = DataBaseBuilder.getInstance(this)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                userData = database.userDao().validateEmail(userEmail)!!
                withContext(Dispatchers.Main) {
                    binding.editTextUsername.setText(userData.username)
                    binding.editTextPhone.setText(userData.phone)
                }
            } catch (e: Exception) {
                Log.e("Database Error", "Error updating property: ${e.message}", e)
                // Handle the error as needed, e.g., display an error toast
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@UserProfileActivity,
                        "Error updating property",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.buttonUpdate.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val userPhone = binding.editTextPhone.text.toString()
            val oldPassword = binding.editTextOldPassword.text.toString()
            val newPassword = binding.editTextNewPassword.text.toString()
            if (oldPassword != userData.password) {
                binding.editTextOldPassword.error = "Password is not correct"
                return@setOnClickListener
            }
            if (oldPassword == newPassword) {
                binding.editTextNewPassword.error = "Password can not be same"
                return@setOnClickListener
            }
            val nameError = Validator.validateUserName(username)
            val phoneError = Validator.validatePhone(userPhone)
            val passwordError = Validator.validatePassword(newPassword)
            if (nameError.isNotEmpty()) {
                binding.editTextUsername.error = nameError
                return@setOnClickListener
            }

            if (phoneError.isNotEmpty()) {
                binding.editTextPhone.error = phoneError
                return@setOnClickListener
            }


            if (passwordError.isNotEmpty()) {
                binding.editTextNewPassword.error = passwordError
                return@setOnClickListener
            }

            val updatedUser = UserEntity(
                userData.id,
                username,
                userPhone,
                userEmail,
                newPassword
            )

            CoroutineScope(Dispatchers.IO).launch {

                database.userDao().updateUser(updatedUser)
            }
            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
            binding.editTextUsername.text.clear()
            binding.editTextPhone.text.clear()
            binding.editTextOldPassword.text.clear()
            binding.editTextNewPassword.text.clear()

        }
//        CoroutineScope(Dispatchers.IO).launch {
//            userData = database.userDao().validateEmail(userEmail)!!
//
//            withContext(Dispatchers.Main) {
//                // Update UI using data binding
//                binding.tvUsernamefromdb.text = userData?.username.toString()
//                binding.tvUseremailfromdb.text = userData?.email.toString()
//                binding.tvUserphonefromdb.text = userData?.phone.toString()
//            }
//        }
//
//        binding.ibUpdatename.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                userData.username = binding.etUsernameupdate.text.toString()
//                database.userDao().updateUser(userData)
//                Log.i("Username", "${userData.username}")
//            }
//        }
    }
}
