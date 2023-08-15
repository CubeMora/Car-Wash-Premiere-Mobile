package com.carwashpremiere.carwashpremieremobile.activities

import android.annotation.SuppressLint
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carwashpremiere.carwashpremieremobile.R
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_Category
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_Shortcuts
import com.carwashpremiere.carwashpremieremobile.functions.Function_AdaptersUtility
import com.carwashpremiere.carwashpremieremobile.functions.Function_Auth0Utility
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class Menu : AppCompatActivity() {

    var imgBtn_Atajo1: ImageButton? = null


    var menuBottomNav: BottomNavigationView? = null
    private var rView_Categories: RecyclerView? = null
    private var rView_Shortcuts: RecyclerView? = null
    var adapterShortcuts: Adapter_Shortcuts? = null
    var adapterCategory: Adapter_Category? = null
    var sharedPreferences: SharedPreferences? = null
    var auth0utility : Function_Auth0Utility? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val activityContext = this




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_menu)


        initUI()
        asyncAdapter()

        if(!sharedPreferences!!.contains("access_token")){
            val dialogFirstLogin = Dialog(this)
            val view = LayoutInflater.from(this).inflate(R.layout.modal_loginforce, null)
            dialogFirstLogin.setContentView(view)
            dialogFirstLogin.setCancelable(false)

            val btn_forcelogin = view.findViewById<Button>(R.id.btn_iniciarSesion)
            btn_forcelogin.setOnClickListener {
                auth0utility!!.loginWithBrowser()
            }
            dialogFirstLogin.show()
        }




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
                val txt_username = view.findViewById<TextView>(R.id.txt_usernameModal)
                val img_userPicture = view.findViewById<ImageView>(R.id.img_userPicture)

                val sharedPreferences: SharedPreferences = getSharedPreferences("auth_prefs", MODE_PRIVATE)
                val username = sharedPreferences.getString("name", null)
                val img = sharedPreferences.getString("picture", null)

                txt_username.text = username
                Picasso.get().load(img).into(img_userPicture)

                btn_login.visibility = View.GONE

                if(!sharedPreferences!!.contains("access_token")){
                    btn_admin.visibility = View.GONE
                    btn_logout.visibility = View.GONE
                    txt_username.visibility = View.GONE
                    btn_login.visibility = View.VISIBLE
                }

                btn_login.setOnClickListener {
                    auth0utility!!.loginWithBrowser()
                }
                btn_admin.setOnClickListener {
                    var intent = Intent(applicationContext, Act_AdminCrud::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                btn_logout.setOnClickListener {
                    auth0utility!!.logout()
                }

                dialog.show()

                return@OnNavigationItemSelectedListener true
            }

            false
        })






    }

    private fun asyncAdapter() {
        coroutineScope.launch {
                       // Llama a structureAdapters() en el contexto principal
            withContext(Dispatchers.Main) {
               structureAdapters()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel() // Cancela todas las coroutines cuando se destruye la actividad
    }
    private fun initUI() {

        rView_Categories = findViewById(R.id.rList_Category)
        imgBtn_Atajo1 = findViewById(R.id.imgBtn_Atajo1)
        rView_Shortcuts = findViewById(R.id.rList_Shortcuts)
        menuBottomNav = findViewById(R.id.menuBottomNav)

        sharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

        auth0utility = Function_Auth0Utility(this)

    }

    suspend fun structureAdapters(){
        val adaptersUtility = Function_AdaptersUtility(this)

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

        adaptersUtility.createAdapterCategory(rView_Categories!!)
        adaptersUtility.createAdapterShortcuts(rView_Shortcuts!!)



    }

}