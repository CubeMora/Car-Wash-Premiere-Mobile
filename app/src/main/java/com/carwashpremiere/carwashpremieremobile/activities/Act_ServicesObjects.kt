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
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_ServicesObjects
import com.carwashpremiere.carwashpremieremobile.functions.Function_AdaptersUtility

class Act_ServicesObjects : AppCompatActivity() {
    var listServicesObjects: RecyclerView? = null
    var btn_Back: Button? = null
    var adapter_servicesObjects: Adapter_ServicesObjects? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_act_services_objects)
        listServicesObjects = findViewById(R.id.rList_Objects)

        structureAdapters()




        btn_Back = findViewById(R.id.btn_Back)
        btn_Back!!.setOnClickListener(View.OnClickListener {
            val intent: Intent
            intent = Intent(this@Act_ServicesObjects, Menu::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        })
    }

    private fun structureAdapters() {
        val adaptersUtility = Function_AdaptersUtility()


        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        listServicesObjects!!.setLayoutManager(layoutManager)
        listServicesObjects!!.setAdapter(adapter_servicesObjects)

        adaptersUtility.createAdapterServicesObjects(this, listServicesObjects!!)

    }


}