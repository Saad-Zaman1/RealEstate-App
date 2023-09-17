package com.example.myapplication.activities


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Validator
import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.databinding.SignupActivityBinding
import com.example.myapplication.global.GlobalVariables
import com.example.myapplication.models.User
import java.util.Calendar


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: SignupActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        val sP: SharedPrefs = SharedPrefs(this)
        val validate = Validator(this)

        binding.btnSignup.setOnClickListener {

            val userName = binding.etName.text.toString().trim()
            val userPhone = binding.etPhone.text.toString().trim()
            var userEmail = binding.etEmail.text.toString().trim()
            var userPassword = binding.etPass.text.toString().trim()

            val bundle = Bundle()


            val obj = User(
                userName,
                userPhone,
                userEmail,
                userPassword
            )
            val nameError = validate.validateUserName(obj.name)
            val phoneError = validate.validatePhone(obj.phone)
            val emailError = validate.validateEmail(obj.email)
            val passwordError = validate.validatePassword(obj.password)

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


//            sP.saveString(GlobalVariables.userName, obj.name)
//            sP.saveString(GlobalVariables.userEmail, obj.email)


//            sP.saveString("name", obj.name)
//                val myEdit: SharedPreferences.Editor = sPrefs.edit()
//                myEdit.putString("name", obj.name)
//                myEdit.putString("phone", userPhone)
//                myEdit.putString("email", userEmail)
//                myEdit.putString("password", userPassword)
//
//                myEdit.commit()
            //scope function
            bundle.apply {
                putString(GlobalVariables.userName, userName)
                putString(GlobalVariables.userEmail, userEmail)
                putString(GlobalVariables.userPhone, userPhone)
            }
            val intent = Intent(this@SignupActivity, DataReceiverActivity::class.java)
            intent.putExtras(bundle)
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
                val livedmonth = selectedMonth - month
                Log.d("months", "$selectedMonth")
            },
            year, month, day
        )

        // Show the date picker dialog
        datePickerDialog.show()
    }
}