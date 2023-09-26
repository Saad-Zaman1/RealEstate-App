package com.example.myapplication.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.R
import com.example.myapplication.dataStorage.room.user.UserEntity

class adapter(
    private val userList: List<UserEntity>,
    private val itemClickListner: ItemClickListnerInterface
) : Adapter<adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.views_layout, parent, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder(view: View) : ViewHolder(view) {

        var tvName: TextView = itemView.findViewById<TextView>(R.id.tv_name)
        var tvEmail: TextView = itemView.findViewById<TextView>(R.id.tv_email)

        init {
            itemView.setOnClickListener {
                val userposition = adapterPosition
                itemClickListner.onItemClick(userposition)
            }
//            itemView.setOnClickListener {
//                val intent = Intent(
//                    Intent.ACTION_DIAL,
//                    Uri.parse("tel:${userList[userposition].phone}")
//                )
//                itemView.context.startActivity(intent)
//            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = userList[position]
        holder.tvName.text = user.username
        holder.tvEmail.text = user.email
//        holder.itemView.setOnClickListener {
//            itemClickListner.onItemClick(position)
//        }
    }

    override fun getItemCount() = userList.size

    // Update data when it changes
//    fun updateData(newList: List<UserEntity>) {
//        userList.clear()
//        userList.addAll(newList)
//        notifyDataSetChanged()
//    }
}
