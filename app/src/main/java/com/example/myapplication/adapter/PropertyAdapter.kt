package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.databinding.PropertyItemBinding
import com.example.myapplication.models.PropertyWithDetails

class PropertyAdapter(private val propertyList: List<PropertyWithDetails>) :
    Adapter<PropertyAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: PropertyItemBinding) : ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            PropertyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val property = propertyList[position]
        holder.binding.textPropertyName.text = property.propertyName
        holder.binding.textPropertyBathrooms.text = property.bathrooms
        holder.binding.textPropertyCity.text = property.city
        holder.binding.textPropertyFurnished.text = property.furnished
        holder.binding.textPropertyPrice.text = property.price
        holder.binding.textPropertyKitchens.text = property.kitchen
        holder.binding.textPropertyRooms.text = property.rooms
        holder.binding.textPropertySaleRent.text = property.sale
        holder.binding.textPropertyAddress.text = property.address

    }

    override fun getItemCount(): Int {
        return propertyList.size
    }
}
