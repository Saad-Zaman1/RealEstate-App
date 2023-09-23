import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.myapplication.dataStorage.room.DataBaseBuilder
import com.example.myapplication.databinding.BottomSheetFilterBinding
import com.example.myapplication.interfaces.FilterListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = DataBaseBuilder.getInstance(requireContext())
        // Initialize and set up your filter UI elements and logic here
        val applyFiltersButton = binding.btnApplyFilters
        val spinnerSize = binding.spinnerSize
        val spinnerCity = binding.spinnerCity

        // Create an ArrayAdapter for the spinners and populate them with values
        val sizeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrayOf("3 Marla", "5 Marla", "7 Marla", "10 Marla")
        )

        val cityAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrayOf("Lahore", "Islamabad", "Gujarat") // Add your city names here
        )

        // Set the dropdown layout style
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapters for the spinners
        spinnerSize.adapter = sizeAdapter
        spinnerCity.adapter = cityAdapter

        applyFiltersButton.setOnClickListener {
            val selectedSize = spinnerSize.selectedItem.toString()
            val selectedCity = spinnerCity.selectedItem.toString()
            val isFurnished = binding.radioButtonFurnished.isChecked
            val isForSale = binding.radioButtonSale.isChecked
            val furnished = if (isFurnished) "Furnished" else "Non Furnished"
            val saleRent = if (isForSale) "Sale" else "Rent"
            val filteredData = database.propertiesDao()
                .getFilteredProperties(furnished, saleRent, selectedSize, selectedCity)
            filteredData.observe(viewLifecycleOwner) {
                (requireActivity() as FilterListener).filterApplied(it)
            }
            // Apply the selected filters and close the bottom sheet
            dismiss()
        }
    }
}
