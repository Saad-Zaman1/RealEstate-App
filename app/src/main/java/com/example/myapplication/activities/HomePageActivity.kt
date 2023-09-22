package com.example.myapplication.activities


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.PropertyAdapter
import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.dataStorage.room.DataBaseBuilder
import com.example.myapplication.databinding.HomepageActivityBinding
import com.example.myapplication.global.GlobalVariables
import com.example.myapplication.models.PropertyWithDetails

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding: HomepageActivityBinding
    private lateinit var propertyList: List<PropertyWithDetails>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomepageActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val database = DataBaseBuilder.getInstance(this)
        val sharedPref = SharedPrefs(this@HomePageActivity)
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

        database.propertiesDao().getAllProperties().observe(this) {
            propertyList = it
            binding.recyclerView.adapter = PropertyAdapter(propertyList)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

}