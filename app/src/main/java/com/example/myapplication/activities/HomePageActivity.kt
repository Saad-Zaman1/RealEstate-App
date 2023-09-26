package com.example.myapplication.activities


import FilterBottomSheetFragment
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.PropertyAdapter
import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.dataStorage.room.DataBaseBuilder
import com.example.myapplication.dataStorage.room.properties.PropertyEntity
import com.example.myapplication.dataStorage.room.propertydetails.PropertyDetailsEntity
import com.example.myapplication.dataStorage.room.user.UserEntity
import com.example.myapplication.databinding.HomepageActivityBinding
import com.example.myapplication.global.GlobalVariables
import com.example.myapplication.interfaces.FilterListener
import com.example.myapplication.interfaces.PropertyClickListnerInterface
import com.example.myapplication.models.PropertyWithDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageActivity : AppCompatActivity(), FilterListener, PropertyClickListnerInterface {
    private lateinit var binding: HomepageActivityBinding
    private lateinit var propertyList: List<PropertyWithDetails>
    private var userpropertytoggle: Boolean = false
    private lateinit var userData: UserEntity
    private lateinit var toggle: ActionBarDrawerToggle   //Hammberger sign in the app bar
    private var username: String = ""
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomepageActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        title = "All Properties"
        val database = DataBaseBuilder.getInstance(this)
        val sharedPref = SharedPrefs(this@HomePageActivity)
        val userEmail = sharedPref.getString(GlobalVariables.userEmail, "")
        val headerView = binding.navigationView.getHeaderView(0)

        val usernameTextView = headerView.findViewById<TextView>(R.id.header_user_name)
        val emailTextView = headerView.findViewById<TextView>(R.id.header_user_email)

        database.userDao().validateEmail(userEmail)?.observe(this@HomePageActivity) {
            username = it.username
            email = it.email
            usernameTextView.text = username
            emailTextView.text = email
        }

//        CoroutineScope(Dispatchers.IO).launch {
//             userData = database.userDao().validateEmail(userEmail)
//                username = userData.name
//                email = userData.phone
//
////          //            withContext(Dispatchers.Main) {
//                usernameTextView.text = username
//                emailTextView.text = email
//            }
//        }

        // Side drawable code
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)   //Showing hamburger button


        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_all_properties -> {
                    // Populating all properties in recycler view
                    title = "All Properties"
                    database.propertiesDao().getAllProperties().observe(this) { data ->
                        propertyList = data
                        binding.recyclerView.adapter =
                            PropertyAdapter(propertyList, this, this)
                    }
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.menu_filter -> {
                    val filterBottomSheet = FilterBottomSheetFragment()
                    filterBottomSheet.show(supportFragmentManager, filterBottomSheet.tag)

                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.menu_my_property -> {
                    userpropertytoggle = true
                    title = "My Properties"
                    database.propertiesDao().getCurrentUserProperties(userEmail)
                        .observe(this) { data ->
                            propertyList = data
                            binding.recyclerView.adapter =
                                PropertyAdapter(propertyList, this, this)
                        }
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)

                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }


                R.id.menu_my_profile -> {
                    val intent = Intent(this, UserProfileActivity::class.java)
                    startActivity(intent)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }


                R.id.menu_logout -> {
                    sharedPref.clearPrefs()
                    sharedPref.saveBoolean(GlobalVariables.isLoggedIn, false)

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)

                }

                R.id.menu_all_users -> {
                    val intent = Intent(this, AllUsersActivity::class.java)
                    startActivity(intent)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.menu_add_property -> {
                    val intent = Intent(this, AddPropertyActivity::class.java)
                    startActivity(intent)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }
//Show all properties in the opening of app
        if (!userpropertytoggle) {

            database.propertiesDao().getAllProperties().observe(this) {
                propertyList = it
                binding.recyclerView.adapter = PropertyAdapter(propertyList, this, this)
            }
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun filterApplied(filteredData: List<PropertyWithDetails>) {
        binding.recyclerView.adapter = PropertyAdapter(filteredData, this, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPropertyClick(position: PropertyWithDetails) {
        val intent = Intent(this, AddPropertyActivity::class.java)
        intent.putExtra("objproperty", position)
        startActivity(intent)
    }

    override fun onPropertyDelete(position: PropertyWithDetails, currenposition: Int) {

        val database = DataBaseBuilder.getInstance(this@HomePageActivity)
        val property = PropertyEntity(
            position.propertyId,
            position.address,
            position.city,
            position.userEmail
        )
        val propertyDetail = PropertyDetailsEntity(
            position.propertyDetailsId,
            position.propertyId,
            position.size,
            position.propertyName,
            position.price,
            position.rooms,
            position.kitchen,
            position.floors,
            position.bathrooms,
            position.furnished,
            position.sale
        )
        CoroutineScope(Dispatchers.IO).launch {
            database.propertiesDao().deleteProperty(property)
            database.propertyDetails().deletePropertyDetails(propertyDetail)
        }
        Toast.makeText(this, "Property Deleted", Toast.LENGTH_SHORT).show()
        binding.recyclerView.adapter?.notifyItemRemoved(currenposition)
    }

}