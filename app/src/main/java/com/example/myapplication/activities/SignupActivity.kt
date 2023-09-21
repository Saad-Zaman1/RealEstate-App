package com.example.myapplication.activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.dataStorage.room.DataBaseBuilder
import com.example.myapplication.dataStorage.room.user.UserEntity
import com.example.myapplication.utils.Validator
import com.example.myapplication.databinding.SignupActivityBinding
import com.example.myapplication.global.GlobalVariables
import com.example.myapplication.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: SignupActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sP: SharedPrefs = SharedPrefs(this)

        Log.d("BeforeDb", "This is before db call")
        var userDatabase = DataBaseBuilder.getInstance(this)



        binding.btnSignup.setOnClickListener {


            val obj = User(
                binding.etName.text.toString().trim(),
                binding.etPhone.text.toString().trim(),
                binding.etEmail.text.toString().trim(),
                binding.etPass.text.toString().trim()

            )

            val nameError = Validator.validateUserName(obj.name)
            val phoneError = Validator.validatePhone(obj.phone)
            val emailError = Validator.validateEmail(obj.email)
            val passwordError = Validator.validatePassword(obj.password)

            if (nameError.isNotEmpty()) {
                binding.etName.error = nameError
                return@setOnClickListener
            }

            if (phoneError.isNotEmpty()) {
                binding.etPhone.error = phoneError
                return@setOnClickListener
            }

            if (emailError.isNotEmpty()) {
                binding.etEmail.error = emailError
                return@setOnClickListener
            }

            if (passwordError.isNotEmpty()) {
                binding.etPass.error = passwordError
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val isExistEmail = userDatabase.userDao().validateEmail(
                        binding.etEmail.text.toString().trim()
                    )
                    val isExistPhone = userDatabase.userDao().validatePhone(
                        binding.etPhone.text.toString().trim()
                    )
                    Log.i("isExist", "$isExistEmail")
                    CoroutineScope(Dispatchers.Main).launch {
                        if (isExistEmail != null) {
                            binding.etEmail.error = "Email already exists"
                        } else if (isExistPhone != null) {
                            binding.etPhone.error = "Phone already exists"
                        } else {
                            userDatabase.userDao().insertUser(
                                UserEntity(
                                    0,
                                    obj.name,
                                    obj.phone,
                                    obj.email,
                                    obj.password
                                )
                            )
                            Log.i("DataSaved", "Yo yo honey singh")
                            sP.saveString(GlobalVariables.userEmail, obj.email)
                            sP.saveBoolean(GlobalVariables.isLoggedIn, true)
                            val intent = Intent(this@SignupActivity, HomePageActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

                } catch (e: Exception) {
                    // Handle exceptions that might occur during the database operation.
                    Log.e("Database Error", "Error checking user existence: ${e.message}", e)
                    Toast.makeText(
                        this@SignupActivity,
                        "Error checking user existence",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.tvGotologin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.flo

    }

//    private fun showDatePickerDialog() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val datePickerDialog = DatePickerDialog(
//            this,
//            { _, selectedYear, selectedMonth, selectedDay ->
//                // Called when a date is selected by the user
//                // Update the EditText with the selected date
//                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
//                binding.editText.setText(selectedDate)
//
//            },
//            year, month, day
//        )
//        // Show the date picker dialog
//        datePickerDialog.show()
//    }
}