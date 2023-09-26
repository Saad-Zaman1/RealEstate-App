package com.example.myapplication.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.contextaware.withContextAvailable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.ItemClickListnerInterface
import com.example.myapplication.adapter.adapter
import com.example.myapplication.dataStorage.room.DataBaseBuilder
import com.example.myapplication.dataStorage.room.DataBaseHelper
import com.example.myapplication.dataStorage.room.user.UserEntity
import com.example.myapplication.databinding.ActivityRecyclerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllUsersActivity : AppCompatActivity(), ItemClickListnerInterface {
    private lateinit var binding: ActivityRecyclerBinding
    private lateinit var database: DataBaseHelper
    private lateinit var userList: List<UserEntity>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        title = "All Sellers"
        database = DataBaseBuilder.getInstance(this)
//        database.userDao().getAllUsers().observe(this) {
//            userList = it
//
//            binding.recycleView.adapter = adapter(it, this)
//            Log.d("UserLogindata", it.toString())
//        }
        CoroutineScope(Dispatchers.IO).launch {
            userList = database.userDao().getAllUsers()
//            Log.i("All users", "$allusers")
            withContext(Dispatchers.Main) {
                binding.recycleView.adapter = adapter(userList, this@AllUsersActivity)
                binding.recycleView.adapter?.notifyDataSetChanged()
            }
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