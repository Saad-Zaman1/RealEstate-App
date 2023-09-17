package com.example.myapplication.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.adapter
import com.example.myapplication.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val songList =
            listOf(
                "India", "China", "australia", "Pakistan", "America", "NewZealand",
                "India", "China", "australia", "Pakistan", "America", "NewZealand",
                "India", "China", "australia", "Pakistan", "America", "NewZealand"
            )

        binding.recycleView.adapter = adapter(songList)

        binding.recycleView.layoutManager = LinearLayoutManager(this)

    }
}