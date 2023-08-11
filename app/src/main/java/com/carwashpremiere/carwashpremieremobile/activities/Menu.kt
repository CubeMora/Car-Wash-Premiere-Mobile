package com.carwashpremiere.carwashpremieremobile.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.carwashpremiere.carwashpremieremobile.R

import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_Category
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_Shortcuts
import com.carwashpremiere.carwashpremieremobile.functions.Function_AdaptersUtility
import com.google.android.material.bottomnavigation.BottomNavigationView

class Menu : AppCompatActivity() {

    var btn_Services: Button? = null
    var imgBtn_Atajo1: ImageButton? = null
    var bar_loadingMenu: ProgressBar? = null
    var menuBottomNav: BottomNavigationView? = null
    private var rView_Categories: RecyclerView? = null
    private var rView_Shortcuts: RecyclerView? = null


    var adapterShortcuts: Adapter_Shortcuts? = null
    var adapterCategory: Adapter_Category? = null
    private lateinit var account: Auth0
    var accessToken: String? = null
    var sharedPreferences: SharedPreferences? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_menu)
        initUI()
        sharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        structureAdapters()

         account = Auth0(
            "FYnBK2HKXZorCkO8kjQJHFsIbFzOr9S1",
            "dev-ap8ggrxa3835inf7.us.auth0.com"
        )
        //TODO Separate this adapter sets to an async task





        menuBottomNav!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val itemId = item.itemId
            if (itemId == R.id.user) {
                // Navegar a la pantalla deseada (pantalla con ID "user")
                val dialog = Dialog(this)
                val view = LayoutInflater.from(this).inflate(R.layout.modal_login, null)
                dialog.setContentView(view)

                val btn_login = view.findViewById<Button>(R.id.btn_Login)
                val btn_admin = view.findViewById<Button>(R.id.btn_Admin)
                val btn_logout = view.findViewById<Button>(R.id.btn_Logout)

                btn_login.visibility = View.GONE

                if(!sharedPreferences!!.contains("access_token")){
                    btn_admin.visibility = View.GONE
                    btn_logout.visibility = View.GONE
                    btn_login.visibility = View.VISIBLE
                }

                btn_login.setOnClickListener {
                    loginWithBrowser()
                }
                btn_admin.setOnClickListener {
                    var intent = Intent(applicationContext, Act_AdminCrud::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                btn_logout.setOnClickListener {
                    logout()
                }

                dialog.show()

                return@OnNavigationItemSelectedListener true
            }
            // Agrega más condiciones para otros elementos del menú si es necesario
            // else if (itemId == R.id.otro_elemento) { ... }
            false
        })


    }


    private fun initUI() {
        rView_Categories = findViewById(R.id.rList_Category)
        imgBtn_Atajo1 = findViewById(R.id.imgBtn_Atajo1)
        rView_Shortcuts = findViewById(R.id.rList_Shortcuts)
        menuBottomNav = findViewById(R.id.menuBottomNav)
    }

    fun structureAdapters(){
        val adaptersUtility = Function_AdaptersUtility()

        //Category Adapter


        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rView_Categories!!.layoutManager = layoutManager
        rView_Categories!!.adapter = adapterCategory


        //Shortcuts Adapter

        val layoutManagerShortcuts = LinearLayoutManager(this)
        layoutManagerShortcuts.orientation = LinearLayoutManager.HORIZONTAL
        rView_Shortcuts!!.layoutManager = layoutManagerShortcuts
        rView_Shortcuts!!.adapter = adapterShortcuts

        adaptersUtility.createAdapterCategory(this, rView_Categories!!)
        adaptersUtility.createAdapterShortcuts(this, rView_Shortcuts!!)
    }


    private fun logout() {

        val editor = sharedPreferences!!.edit()
        editor.remove("access_token")
        editor.apply()
        WebAuthProvider.logout(account)
            .withScheme("demo")

            .start(this, object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(payload: Void?) {
                    // The user has been logged out!
                    val intent = Intent(applicationContext, Menu::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                    startActivity(intent)
                }

                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
                }
            })
    }

    private fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope.

        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email read:current_user update:current_user_metadata")
            .withAudience("https://${getString(R.string.com_auth0_domain)}/api/v2/")
            // Launch the authentication passing the callback where the results will be received
            .start(this, object : Callback<Credentials, AuthenticationException> {
                // Called when there is an authentication failure
                override fun onFailure(exception: AuthenticationException) {
                    // Something went wrong!
                    Toast.makeText(
                        applicationContext,
                        "Authentication failed: " + exception.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                // Called when authentication completed successfully
                override fun onSuccess(credentials: Credentials) {
                    // Get the access token from the credentials object.
                    // This can be used to call APIs

                    accessToken = credentials.accessToken
                    //txt_test!!.text = "Access token: $accessToken"

                    val sharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("access_token", accessToken)
                    editor.apply()


                    Toast.makeText(
                        applicationContext,
                        "Authentication succeded: " + accessToken,
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(applicationContext, Menu::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                    startActivity(intent)

                    /* var intent = Intent(this@Act_FirstScreen, Menu::class.java)
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                     intent.putExtra("AccessToken", accessToken)
                     startActivity(intent)
                     */
                }
            })
    }




    fun change_ServicesCars(view: View?) {
        val intent = Intent(this, Act_ServicesCars::class.java)
        startActivity(intent)
    }

    fun change_MainMenu(view: View?) {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }

    fun change_ServiceDetails1(view: View?) {
        val intent = Intent(this, Act_DescriptionCars::class.java)
        startActivity(intent)
    }

    fun change_ServiceDetails2(view: View?) {
        val intent = Intent(this, Act_description_parts::class.java)
        startActivity(intent)
    }

    fun change_ServiceParameters(view: View?) {
        val intent = Intent(this, Act_CarParameters::class.java)
        startActivity(intent)
    }

    fun change_ServiceDetails3(view: View?) {
        val intent = Intent(this, Act_OrderDetail::class.java)
        startActivity(intent)
    }

    fun change_ObjectParameters(view: View?) {
        val intent = Intent(this, Act_ObjectParameters::class.java)
        startActivity(intent)
    }

  /*  companion object {
        var mCategoryList: MutableList<Model_Category> = ArrayList<Model_Category>()
        var mShortcutsList: MutableList<Model_Shortcuts> = ArrayList<Model_Shortcuts>()
        private var adapterCategory: Adapter_Category? = null
    }*/
}