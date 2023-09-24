package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.dataStorage.room.DataBaseBuilder
import com.example.myapplication.dataStorage.room.properties.PropertyEntity
import com.example.myapplication.dataStorage.room.propertydetails.PropertyDetailsEntity
import com.example.myapplication.databinding.ActivityAddPropertyBinding
import com.example.myapplication.global.GlobalVariables
import com.example.myapplication.utils.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.Async


class AddPropertyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPropertyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intent: Intent = intent
        var database = DataBaseBuilder.getInstance(this)
        // Define predefined values for spinners
        val cities = arrayOf("Select City", "Lahore", "Islamabad", "Gujarat")
        val sizes = arrayOf("Select Size", "3 Marla", "5 Marla", "7 Marla", "10 Marla")
        val roomCounts =
            arrayOf(
                "Select Rooms",
                "1 Room",
                "2 Rooms",
                "3 Rooms",
                "4 Rooms",
                "5 Rooms",
                "6 Rooms",
                "7 Rooms",
                "8 Rooms",
                "9 Rooms",
                "10+ Rooms"
            )
        val kitchenCounts = arrayOf(
            "Select Kitchen",
            "1 Kitchen",
            "2 Kitchens",
            "3 Kitchens",
            "4 Kitchens",
            "5+ Kitchens",
        )
        val bathroomCounts = arrayOf(
            "Select BathRooms",
            "1 Bathroom",
            "2 Bathrooms",
            "3 Bathrooms",
            "4 Bathrooms",
            "5 Bathrooms",
            "6 Bathrooms",
            "7 Bathrooms",
            "8 Bathrooms",
            "9 Bathrooms",
            "10+ Bathrooms"
        )
        val floorCounts = arrayOf(
            "Select Floors",
            "1 Floors",
            "2 Floors",
            "3 Floors",
            "4 Floors",
            "5+ Floors",
        )

        // Set up ArrayAdapter for spinners
        val cityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        val sizeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sizes)
        val roomAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roomCounts)
        val kitchenAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kitchenCounts)
        val floorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, floorCounts)
        val bathroomAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, bathroomCounts)

        // Set dropdown view resource
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        kitchenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bathroomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set adapters to spinners
        binding.spinnerCity.adapter = cityAdapter
        binding.spinnerSize.adapter = sizeAdapter
        binding.spinnerRooms.adapter = roomAdapter
        binding.spinnerKitchens.adapter = kitchenAdapter
        binding.spinnerBathrooms.adapter = bathroomAdapter
        binding.spinnerFloor.adapter = floorAdapter
//        // Handle the submit button click event here

        if (intent.getStringExtra("city") != null) {
            binding.buttonSubmit.text = "Update Property"
            binding.tvaddproperty.text = "Update Property"
            binding.spinnerCity.setSelection(
                (binding.spinnerCity.adapter as ArrayAdapter<String>).getPosition(
                    intent.getStringExtra(GlobalVariables.city)
                )
            )

            //Editable is an interface in Android used to represent text that can be modified (editable).
            binding.editTextAddress.setText(intent.getStringExtra(GlobalVariables.address))


            binding.spinnerSize.setSelection(
                (binding.spinnerSize.adapter as ArrayAdapter<String>).getPosition(
                    intent.getStringExtra(GlobalVariables.size)
                )
            )
            binding.editTextPropertyName.setText(intent.getStringExtra(GlobalVariables.propertyName))

            binding.spinnerRooms.setSelection(
                (binding.spinnerRooms.adapter as ArrayAdapter<String>).getPosition(
                    intent.getStringExtra(GlobalVariables.rooms)
                )
            )
            binding.spinnerKitchens.setSelection(
                (binding.spinnerKitchens.adapter as ArrayAdapter<String>).getPosition(
                    intent.getStringExtra(GlobalVariables.kitchen)
                )
            )
            binding.spinnerFloor.setSelection(
                (binding.spinnerFloor.adapter as ArrayAdapter<String>).getPosition(
                    intent.getStringExtra(GlobalVariables.floors)
                )
            )
            binding.spinnerBathrooms.setSelection(
                (binding.spinnerBathrooms.adapter as ArrayAdapter<String>).getPosition(
                    intent.getStringExtra(GlobalVariables.bathrooms)
                )
            )
            binding.radioButtonFurnished.isChecked =
                intent.getStringExtra(GlobalVariables.isFurnished) == "Furnished"
            binding.radioButtonNonFurnished.isChecked =
                intent.getStringExtra(GlobalVariables.isFurnished) != "Furnished"

            binding.radioButtonSale.isChecked =
                intent.getStringExtra(GlobalVariables.isSale) == "Sale"
            binding.radioButtonRent.isChecked =
                intent.getStringExtra(GlobalVariables.isSale) == "Rent"

            binding.editTextPrice.setText(intent.getStringExtra(GlobalVariables.price))

            val propertyID = intent.getStringExtra(GlobalVariables.propertyID)!!.toLong()
            val propertyDetailsId =
                intent.getStringExtra(GlobalVariables.propertyDetailsId)!!.toLong()
            binding.buttonSubmit.setOnClickListener {

                val selectedCity = binding.spinnerCity.selectedItem.toString()
                val address = binding.editTextAddress.text.toString()
                val selectedSize = binding.spinnerSize.selectedItem.toString()
                val propertyName = binding.editTextPropertyName.text.toString()
                val selectedFloor = binding.spinnerFloor.selectedItem.toString()
                val selectedRooms = binding.spinnerRooms.selectedItem.toString()
                val selectedKitchens = binding.spinnerKitchens.selectedItem.toString()
                val selectedBathrooms = binding.spinnerBathrooms.selectedItem.toString()
                val isFurnished = binding.radioButtonFurnished.isChecked
                val isForSale = binding.radioButtonSale.isChecked
                val price = binding.editTextPrice.text.toString()
                // Validate fields
                if (Validator.validateSpinner(selectedCity).isNotEmpty()) {
                    (binding.spinnerCity.selectedView as TextView).error =
                        Validator.validateSpinner(selectedCity)
                    return@setOnClickListener
                }
                if (Validator.checkEmpty(address, "Address").isNotEmpty()) {
                    binding.editTextAddress.error = Validator.checkEmpty(address, "Address")
                    return@setOnClickListener
                }
                if (Validator.validateSpinner(selectedSize).isNotEmpty()) {
                    (binding.spinnerSize.selectedView as TextView).error =
                        Validator.validateSpinner(selectedSize)
                    return@setOnClickListener
                }
                if (Validator.checkEmpty(propertyName, "Property Name").isNotEmpty()) {
                    binding.editTextPropertyName.error =
                        Validator.checkEmpty(propertyName, "Property Name")
                    return@setOnClickListener
                }
                if (Validator.validateSpinner(selectedRooms).isNotEmpty()) {
                    (binding.spinnerRooms.selectedView as TextView).error =
                        Validator.validateSpinner(selectedRooms)
                    return@setOnClickListener
                }
                if (Validator.validateSpinner(selectedKitchens).isNotEmpty()) {
                    (binding.spinnerKitchens.selectedView as TextView).error =
                        Validator.validateSpinner(selectedRooms)
                    return@setOnClickListener
                }
                if (Validator.validateSpinner(selectedFloor).isNotEmpty()) {
                    (binding.spinnerFloor.selectedView as TextView).error =
                        Validator.validateSpinner(selectedFloor)
                    return@setOnClickListener
                }
                if (Validator.validateSpinner(selectedBathrooms).isNotEmpty()) {
                    (binding.spinnerBathrooms.selectedView as TextView).error =
                        Validator.validateSpinner(selectedBathrooms)
                    return@setOnClickListener
                }
                if (Validator.checkEmpty(price, "price").isNotEmpty()) {
                    binding.editTextPrice.error = Validator.checkEmpty(price, "price")
                    return@setOnClickListener
                }

                val furnished = if (isFurnished) "Furnished" else "Non Furnished"
                val saleRent = if (isForSale) "Sale" else "Rent"

                val userEmail = SharedPrefs(this).getString(GlobalVariables.userEmail, "")

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val updatedProperty = PropertyEntity(
                            propertyID, // The ID of the property you want to update
                            address, // Updated address
                            selectedCity, // Updated city
                            userEmail // User email remains the same
                        )
                        database.propertiesDao().updateProperty(updatedProperty)

                        val updatedPropertyDetails = PropertyDetailsEntity(
                            propertyDetailsId,
                            propertyID,
                            selectedSize,
                            propertyName,
                            price,
                            selectedRooms,
                            selectedKitchens,
                            selectedFloor,
                            selectedBathrooms,
                            furnished,
                            saleRent
                        )
                        database.propertyDetails().updateProperty(updatedPropertyDetails)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@AddPropertyActivity,
                                "Property updated successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Log.e("Database Error", "Error updating property: ${e.message}", e)
                        // Handle the error as needed, e.g., display an error toast
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@AddPropertyActivity,
                                "Error updating property",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                binding.spinnerCity.setSelection(0)
                binding.editTextAddress.text.clear()
                binding.spinnerSize.setSelection(0)
                binding.editTextPropertyName.text.clear()
                binding.spinnerRooms.setSelection(0)
                binding.spinnerKitchens.setSelection(0)
                binding.spinnerFloor.setSelection(0)
                binding.spinnerBathrooms.setSelection(0)
                binding.editTextPrice.text.clear()
                binding.radioButtonFurnished.isChecked =
                    true
                binding.radioButtonSale.isChecked = true
            }
        } else {

            binding.buttonSubmit.setOnClickListener {

                // Retrieving user-selected values
                val selectedCity = binding.spinnerCity.selectedItem.toString()
                val address = binding.editTextAddress.text.toString()
                val selectedSize = binding.spinnerSize.selectedItem.toString()
                val propertyName = binding.editTextPropertyName.text.toString()
                val selectedFloor = binding.spinnerFloor.selectedItem.toString()
                val selectedRooms = binding.spinnerRooms.selectedItem.toString()
                val selectedKitchens = binding.spinnerKitchens.selectedItem.toString()
                val selectedBathrooms = binding.spinnerBathrooms.selectedItem.toString()
                val isFurnished = binding.radioButtonFurnished.isChecked
                val isForSale = binding.radioButtonSale.isChecked
                val price = binding.editTextPrice.text.toString()


                // Validate fields
                if (Validator.validateSpinner(selectedCity).isNotEmpty()) {
                    (binding.spinnerCity.selectedView as TextView).error =
                        Validator.validateSpinner(selectedCity)
                    return@setOnClickListener
                }
                if (Validator.checkEmpty(address, "Address").isNotEmpty()) {
                    binding.editTextAddress.error = Validator.checkEmpty(address, "Address")
                    return@setOnClickListener
                }
                if (Validator.validateSpinner(selectedSize).isNotEmpty()) {
                    (binding.spinnerSize.selectedView as TextView).error =
                        Validator.validateSpinner(selectedSize)
                    return@setOnClickListener
                }
                if (Validator.checkEmpty(propertyName, "Property Name").isNotEmpty()) {
                    binding.editTextPropertyName.error =
                        Validator.checkEmpty(propertyName, "Property Name")
                    return@setOnClickListener
                }
                if (Validator.validateSpinner(selectedRooms).isNotEmpty()) {
                    (binding.spinnerRooms.selectedView as TextView).error =
                        Validator.validateSpinner(selectedRooms)
                    return@setOnClickListener
                }
                if (Validator.validateSpinner(selectedKitchens).isNotEmpty()) {
                    (binding.spinnerKitchens.selectedView as TextView).error =
                        Validator.validateSpinner(selectedRooms)
                    return@setOnClickListener
                }
                if (Validator.validateSpinner(selectedFloor).isNotEmpty()) {
                    (binding.spinnerFloor.selectedView as TextView).error =
                        Validator.validateSpinner(selectedFloor)
                    return@setOnClickListener
                }
                if (Validator.validateSpinner(selectedBathrooms).isNotEmpty()) {
                    (binding.spinnerBathrooms.selectedView as TextView).error =
                        Validator.validateSpinner(selectedBathrooms)
                    return@setOnClickListener
                }
                if (Validator.checkEmpty(price, "price").isNotEmpty()) {
                    binding.editTextPrice.error = Validator.checkEmpty(price, "price")
                    return@setOnClickListener

                }

                val furnished = if (isFurnished) "Furnished" else "Non Furnished"
                val saleRent = if (isForSale) "Sale" else "Rent"

                val userEmail = SharedPrefs(this).getString(GlobalVariables.userEmail, "")

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Insert data into PropertyEntity
                        val propertyId = database.propertiesDao().insertProperty(
                            PropertyEntity(
                                0,
                                address,
                                selectedCity,
                                userEmail
                            )
                        )

                        // Insert data into PropertyDetailsEntity with the obtained propertyId
                        database.propertyDetails().insertProperty(
                            PropertyDetailsEntity(
                                0,
                                propertyId,
                                selectedSize,
                                propertyName,
                                price,
                                selectedRooms,
                                selectedKitchens,
                                selectedFloor,
                                selectedBathrooms,
                                furnished,
                                saleRent
                            )
                        )

                        // Displaying a toast message on the main thread
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@AddPropertyActivity,
                                "Property added",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Log.e("Database Error", "Error inserting property: ${e.message}", e)
                        // Handle the error as needed, e.g., display an error toast
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@AddPropertyActivity,
                                "Error inserting property",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                binding.spinnerCity.setSelection(0)
                binding.editTextAddress.text.clear()
                binding.spinnerSize.setSelection(0)
                binding.editTextPropertyName.text.clear()
                binding.spinnerRooms.setSelection(0)
                binding.spinnerKitchens.setSelection(0)
                binding.spinnerFloor.setSelection(0)
                binding.spinnerBathrooms.setSelection(0)
                binding.editTextPrice.text.clear()
                binding.radioButtonFurnished.isChecked =
                    true
                binding.radioButtonSale.isChecked = true // S

            }

        }
    }
}