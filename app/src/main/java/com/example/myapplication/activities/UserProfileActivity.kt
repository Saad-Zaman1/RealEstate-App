package com.example.myapplication.activities

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.dataStorage.room.DataBaseBuilder
import com.example.myapplication.dataStorage.room.user.UserEntity
import com.example.myapplication.global.GlobalVariables
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserProfileActivity : AppCompatActivity() {
    private lateinit var username: TextView
    private lateinit var useremail: TextView
    private lateinit var userPhone: TextView

    private lateinit var etUpdateUserName: EditText
    private lateinit var updatename: ImageView
    private lateinit var userName: String
    private lateinit var userData: UserEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.myapplication.R.layout.activity_user_profile)

        username = findViewById(com.example.myapplication.R.id.tv_usernamefromdb)
        useremail = findViewById(com.example.myapplication.R.id.tv_useremailfromdb)
        userPhone = findViewById(com.example.myapplication.R.id.tv_userphonefromdb)

        etUpdateUserName = findViewById(com.example.myapplication.R.id.et_usernameupdate)
        updatename = findViewById(com.example.myapplication.R.id.ib_updatename)

        val sP = SharedPrefs(this)
        val userEmail = sP.getString(GlobalVariables.userEmail, "")
        Log.d("userEmail", "$userEmail")

        val database = DataBaseBuilder.getInstance(this)


        CoroutineScope(Dispatchers.IO).launch {
            userData = database.userDao().validateEmail(userEmail)!!
            Log.i("Userdata", "$userData")
            username.text = userData?.username.toString()
            useremail.text = userData?.email.toString()
            userPhone.text = userData?.phone.toString()
        }

        updatename.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                userData.username = etUpdateUserName.text.toString()
                database.userDao().updateUser(userData)
                Log.i("Username", "${userData.username}")
            }
        }


//        Log.i("Username: ", "$userName")
//        val age: MutableList<String> = ArrayList()
//        for (i in 1..100) {
//            age.add(Integer.toString(i))
//        }
//
//        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
//            this, R.layout.simple_spinner_item, age
//        )
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_dropdown_item_1line)
//
//        val spinner = findViewById<View>(com.example.myapplication.R.id.spinner) as Spinner
//        spinner.adapter = spinnerArrayAdapter

    }
}