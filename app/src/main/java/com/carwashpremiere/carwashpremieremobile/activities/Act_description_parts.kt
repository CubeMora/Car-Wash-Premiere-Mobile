package com.carwashpremiere.carwashpremieremobile.activities

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.carwashpremiere.carwashpremieremobile.R

class Act_description_parts : AppCompatActivity() {
    var imgBtn_Ex: ImageButton? = null
    var txt_ServiceDescription: TextView? = null
    var txt_ServiceParts: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_act_description_parts)
        txt_ServiceDescription = findViewById(R.id.txt_ServiceDescription)
        txt_ServiceParts = findViewById(R.id.txt_ServiceParts)
    }

    fun setLongText(v: View?) {
        txt_ServiceDescription!!.setText(R.string.large_text)
        txt_ServiceParts!!.setText(R.string.large_text)
    }
}