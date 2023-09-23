package com.example.myapplication.activities


import FilterBottomSheetFragment
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.PropertyAdapter
import com.example.myapplication.dataStorage.SharedPrefs
import com.example.myapplication.dataStorage.room.DataBaseBuilder
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
//    private lateinit var toggle: ActionBarDrawerToggle   //Hammberger sign in the app bar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomepageActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val database = DataBaseBuilder.getInstance(this)
        val sharedPref = SharedPrefs(this@HomePageActivity)
        val userEmail = sharedPref.getString(GlobalVariables.userEmail, "")
        val headerView = binding.navigationView.getHeaderView(0)
        val usernameTextView = headerView.findViewById<TextView>(R.id.header_user_name)
        val emailTextView = headerView.findViewById<TextView>(R.id.header_user_email)


        CoroutineScope(Dispatchers.IO).launch {
            userData = database.userDao().validateEmail(userEmail)!!

            withContext(Dispatchers.Main) {
                // Update UI using data binding
                usernameTextView.text = userData?.username.toString()
                emailTextView.text = userData?.email.toString()

            }
        }
//        // Side drawable code
//        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
//        binding.drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_all_properties -> {
                    // Populating all properties in recycler view ! Look for alternate approach
                    database.propertiesDao().getAllProperties().observe(this) { data ->
                        propertyList = data
                        binding.recyclerView.adapter = PropertyAdapter(propertyList, this)
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
                    database.propertiesDao().getCurrentUserProperties(userEmail)
                        .observe(this) { data ->
                            propertyList = data
                            binding.recyclerView.adapter = PropertyAdapter(propertyList, this)
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
                    val intent = Intent(this, RecyclerActivity::class.java)
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

        if (!userpropertytoggle) {
            // Populating all properties in recycler view
            database.propertiesDao().getAllProperties().observe(this) {
                propertyList = it
                binding.recyclerView.adapter = PropertyAdapter(propertyList, this)
            }
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun filterApplied(filteredData: List<PropertyWithDetails>) {
        binding.recyclerView.adapter = PropertyAdapter(filteredData, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onPropertyClick(position: Int) {
        val clickedUser = propertyList[position]
        val intent = Intent(this, AddPropertyActivity::class.java)
        val bundle = Bundle().apply {
            putString(GlobalVariables.city, clickedUser.city)
            putString(GlobalVariables.address, clickedUser.address)
            putString(GlobalVariables.size, clickedUser.size)
            putString(GlobalVariables.propertyName, clickedUser.propertyName)
            putString(GlobalVariables.rooms, clickedUser.rooms)
            putString(GlobalVariables.kitchen, clickedUser.kitchen)
            putString(GlobalVariables.floors, clickedUser.floors)
            putString(GlobalVariables.bathrooms, clickedUser.bathrooms)
            putString(GlobalVariables.isFurnished, clickedUser.furnished)
            putString(GlobalVariables.isSale, clickedUser.sale)
            putString(GlobalVariables.price, clickedUser.price)
            putString(GlobalVariables.propertyDetailsId, clickedUser.propertyDetailsId.toString())
            putString(
                GlobalVariables.propertyID, clickedUser.propertyId.toString()
            )
        }

        intent.putExtras(bundle)
        startActivity(intent)
    }
}