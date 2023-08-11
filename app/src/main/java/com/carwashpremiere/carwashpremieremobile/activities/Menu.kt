package com.carwashpremiere.carwashpremieremobile.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carwashpremiere.carwashpremieremobile.R

import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_Category
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_Shortcuts
import com.carwashpremiere.carwashpremieremobile.functions.Function_AdaptersUtility
import com.carwashpremiere.carwashpremieremobile.whatsapp_test
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



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_menu)
        initUI()
        structureAdapters()


        //TODO Separate this adapter sets to an async task





        menuBottomNav!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val itemId = item.itemId
            if (itemId == R.id.user) {
                // Navegar a la pantalla deseada (pantalla con ID "user")
                val intent = Intent(
                    this@Menu,
                    Act_AdminCrud::class.java
                ) // Reemplaza "PantallaUsuario" con el nombre de tu actividad de destino
                startActivity(intent)
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







    fun change_Whatsapp(view: View?) {
        val intent = Intent(this, whatsapp_test::class.java)
        startActivity(intent)
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