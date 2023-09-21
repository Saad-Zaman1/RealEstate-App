package com.example.myapplication.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.ItemClickListnerInterface
import com.example.myapplication.adapter.adapter
import com.example.myapplication.dataStorage.room.DataBaseBuilder
import com.example.myapplication.dataStorage.room.DataBaseHelper
import com.example.myapplication.dataStorage.room.user.UserEntity
import com.example.myapplication.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity(), ItemClickListnerInterface {
    private lateinit var binding: ActivityRecyclerBinding
    private lateinit var database: DataBaseHelper
    private lateinit var userList: List<UserEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        database = DataBaseBuilder.getInstance(this)
        database.userDao().getAllUsers().observe(this) {
            userList = it

            binding.recycleView.adapter = adapter(it, this)
            Log.d("UserLogindata", it.toString())
        }

        binding.recycleView.layoutManager = LinearLayoutManager(this)

    }

    override fun onItemClick(position: Int) {
        val clickedUser = userList[position]
        Log.d("datainuserlist", "${clickedUser.username}")
        val intent = Intent(
            Intent.ACTION_DIAL,
            Uri.parse("tel:${clickedUser.phone}")
        )
        Toast.makeText(this, "Dialing", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
}