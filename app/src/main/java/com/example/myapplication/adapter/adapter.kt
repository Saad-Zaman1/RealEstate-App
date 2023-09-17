package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.R

class adapter(private val songs: List<String>) : Adapter<adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.views_layout, parent, false)
        return MyViewHolder(view)
    }

    class MyViewHolder(view: View) : ViewHolder(view) {
        var tvSongs: TextView = itemView.findViewById<TextView>(R.id.textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvSongs.text = songs[position]
    }

    override fun getItemCount() = songs.size
}
