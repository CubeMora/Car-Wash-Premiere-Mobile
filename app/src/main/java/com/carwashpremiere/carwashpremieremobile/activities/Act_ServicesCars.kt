package com.carwashpremiere.carwashpremieremobile.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carwashpremiere.carwashpremieremobile.R
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_ServicesCars
import com.carwashpremiere.carwashpremieremobile.functions.Function_AdaptersUtility
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Act_ServicesCars : AppCompatActivity() {

    var fltBtn_Back: FloatingActionButton? = null
    var rList_ServicesCars: RecyclerView? = null
    var btn_Back: Button? = null

    var adapter_servicesCars: Adapter_ServicesCars? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_act_services_cars)

        structureAdapters()



        btn_Back = findViewById(R.id.btn_Back)
        btn_Back!!.setOnClickListener(View.OnClickListener {
            val intent: Intent
            intent = Intent(this@Act_ServicesCars, Menu::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        })
    }

    private fun structureAdapters() {
        val adaptersUtility = Function_AdaptersUtility(this)
        rList_ServicesCars = findViewById(R.id.rList_ServicesCars)


        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rList_ServicesCars!!.layoutManager = layoutManager
        rList_ServicesCars!!.adapter = adapter_servicesCars

        adaptersUtility.createAdapterServicesCars(this, rList_ServicesCars!!)
    }


}