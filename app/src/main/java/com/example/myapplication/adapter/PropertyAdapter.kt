package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.databinding.PropertyItemBinding
import com.example.myapplication.interfaces.PropertyClickListnerInterface
import com.example.myapplication.models.PropertyWithDetails

class PropertyAdapter(
    private val propertyList: List<PropertyWithDetails>,
    private val itemclicklistner: PropertyClickListnerInterface
) :
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
        holder.binding.let {
            it.textPropertyName.text = property.propertyName
            it.textPropertyBathrooms.text = property.bathrooms
            it.textPropertyCity.text = property.city
            it.textPropertyFurnished.text = property.furnished
            it.textPropertyPrice.text = property.price
            it.textPropertyKitchens.text = property.kitchen
            it.textPropertyRooms.text = property.rooms
            it.textPropertySaleRent.text = property.sale
            it.textPropertyAddress.text = property.address
            it.textPropertySize.text = property.size
        }
        holder.itemView.setOnClickListener {
            itemclicklistner.onPropertyClick(position)
        }
    }

    override fun getItemCount(): Int {
        return propertyList.size
    }
}
