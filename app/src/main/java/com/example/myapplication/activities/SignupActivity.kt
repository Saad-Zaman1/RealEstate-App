package com.example.myapplication.activities


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.dataStorage.room.DataBaseBuilder
import com.example.myapplication.dataStorage.room.DataBaseHelper
import com.example.myapplication.dataStorage.room.UserEntity
//import androidx.room.Room
import com.example.myapplication.utils.Validator
//import com.example.myapplication.dataStorage.room.DataBaseHelper
//import com.example.myapplication.dataStorage.room.UserEntity
import com.example.myapplication.databinding.SignupActivityBinding
import com.example.myapplication.global.GlobalVariables
import com.example.myapplication.models.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
import java.util.Calendar


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: SignupActivityBinding
    private lateinit var userDatabase: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        title = "SignUp"

        val sP: SharedPrefs = SharedPrefs(this)

        Log.d("BeforeDb", "This is before db call")


        userDatabase = DataBaseBuilder.getInstance(this)

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

            sP.saveString(GlobalVariables.userName, obj.name)
            sP.saveString(GlobalVariables.userEmail, obj.email)
            sP.saveBoolean(GlobalVariables.isLoggedIn, true)
            GlobalScope.launch {
                userDatabase.userDao().insertUser(
                    UserEntity(
                        0,
                        obj.name,
                        obj.phone,
                        obj.email,
                        obj.password
                    )
                )
            }
            val intent = Intent(this@SignupActivity, HomePageActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.editText.setOnClickListener {
            showDatePickerDialog()
        }
        binding.gotoList.setOnClickListener {
            val intent = Intent(this, RecyclerActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Called when a date is selected by the user
                // Update the EditText with the selected date
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.editText.setText(selectedDate)

            },
            year, month, day
        )
        // Show the date picker dialog
        datePickerDialog.show()
    }
}