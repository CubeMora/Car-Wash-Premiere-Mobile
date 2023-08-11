package com.carwashpremiere.carwashpremieremobile.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carwashpremiere.carwashpremieremobile.R
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_DetailsObject
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_ExtraServicesObject
import com.carwashpremiere.carwashpremieremobile.functions.Function_AdaptersUtility
import com.carwashpremiere.carwashpremieremobile.model.Model_DetailsGeneral
import com.carwashpremiere.carwashpremieremobile.model.Model_ExtraServicesGeneral

class Act_ObjectParameters : AppCompatActivity() {
    var rList_ExtraServicesObject: RecyclerView? = null
    var rList_DetailsObject: RecyclerView? = null

    var txt_ObjectTitle: TextView? = null
    var cardView_ExtraServicesObject: CardView? = null
    var cardView_DetailsObject: CardView? = null
    var cardView_TitleObjectDetails: CardView? = null
    var cardView_TitleObjectExtraServices: CardView? = null
    var eTxt_ObjectSize: EditText? = null
    var eTxt_ObjectForm: EditText? = null
    var eTxt_ObjectMaterial: EditText? = null
    var btn_Next: Button? = null
    var btn_Back: Button? = null
    var tempObjectSize = "N/A"
    var tempObjectForm = "N/A"
    var tempObjectMaterial = "N/A"
    var adapterDetailsObject: Adapter_DetailsObject? = null
    var adapterExtraServiceObject: Adapter_ExtraServicesObject? = null
    var mDetailsObjectsList: List<Model_DetailsGeneral> = ArrayList()
    var mExtraServicesObjectsList: List<Model_ExtraServicesGeneral> = ArrayList()
    val adaptersUtility = Function_AdaptersUtility()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_act_object_parameters)
        initUI()

        structureAdapters()

        val intent = intent
        txt_ObjectTitle!!.text = intent.getStringExtra("objectTitle")
       // cardView_ExtraServicesObject!!.visibility = View.GONE
       // cardView_TitleObjectExtraServices!!.visibility = View.GONE






        // Agregar un TextWatcher al campo de tamaño
        eTxt_ObjectSize!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                // Verificar si el campo está vacío
                if (editable.toString().isEmpty()) {
                    return
                }
                try {
                    // Obtener el valor ingresado como un entero
                    val size = editable.toString().toInt()

                    // Verificar si el valor excede el límite de 200
                    if (size > 200) {
                        // Restablecer el valor del campo a 200
                        eTxt_ObjectSize!!.setText("200")
                        // Mover el cursor al final del texto
                        eTxt_ObjectSize!!.setSelection(eTxt_ObjectSize!!.text.length)
                    }
                } catch (e: NumberFormatException) {
                    // Mostrar un mensaje de error cuando se ingresa un valor no numérico
                    Toast.makeText(
                        this@Act_ObjectParameters,
                        "Solo se aceptan valores numéricos",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Restablecer el campo a su valor anterior
                    eTxt_ObjectSize!!.setText("")
                }
            }
        })
        btn_Next!!.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("objectTitle", txt_ObjectTitle!!.text.toString())

            // Obtener valores de los campos de entrada, si están vacíos, establecer "N/A"
            bundle.putString(
                "objectSize",
                if (eTxt_ObjectSize!!.text.toString()
                        .isEmpty()
                ) "N/A" else eTxt_ObjectSize!!.text.toString()
            )
            bundle.putString(
                "objectForm",
                if (eTxt_ObjectForm!!.text.toString()
                        .isEmpty()
                ) "N/A" else eTxt_ObjectForm!!.text.toString()
            )
            bundle.putString(
                "objectMaterial",
                if (eTxt_ObjectMaterial!!.text.toString()
                        .isEmpty()
                ) "N/A" else eTxt_ObjectMaterial!!.text.toString()
            )
            bundle.putStringArrayList(
                "extraServices", ArrayList(

                    adapterExtraServiceObject!!.getSelectedServices()
                )
            )
            bundle.putStringArrayList(
                "details", ArrayList(
                    adapterDetailsObject!!.getSelectedDetails()
                )
            )
            bundle.putString("bundleType", "object")
            val intent = Intent(this@Act_ObjectParameters, Act_OrderDetail::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        btn_Back!!.setOnClickListener {

            finish()
        }
    }

    private fun structureAdapters() {


        val linearLayoutManagerExtra = LinearLayoutManager(this)
        linearLayoutManagerExtra.orientation = LinearLayoutManager.VERTICAL
        rList_ExtraServicesObject!!.layoutManager = linearLayoutManagerExtra
        adapterExtraServiceObject = Adapter_ExtraServicesObject(this, mExtraServicesObjectsList)
        rList_ExtraServicesObject!!.adapter = adapterExtraServiceObject



        val linearLayoutManagerDetails = LinearLayoutManager(this)
        linearLayoutManagerDetails.orientation = LinearLayoutManager.VERTICAL
        rList_DetailsObject!!.layoutManager = linearLayoutManagerDetails
        adapterDetailsObject = Adapter_DetailsObject(this, mDetailsObjectsList)
        rList_DetailsObject!!.adapter = adapterDetailsObject

        adaptersUtility.createAdapterExtraServicesObject(this, rList_ExtraServicesObject!!, adapterExtraServiceObject!!)
        adaptersUtility.createAdapterDetailsObject(this, rList_DetailsObject!!, adapterDetailsObject!!)
    }

    fun initUI() {
        rList_DetailsObject = findViewById(R.id.rList_DetailsObject)
        rList_ExtraServicesObject = findViewById(R.id.rList_ExtraServicesObject)
        txt_ObjectTitle = findViewById(R.id.txt_HeaderObjectParameters)
        cardView_DetailsObject = findViewById(R.id.card_DetailsObject)
        cardView_ExtraServicesObject = findViewById(R.id.card_ExtraServicesObject)
        cardView_TitleObjectDetails = findViewById(R.id.card_TitulosObjectDetails)
        cardView_TitleObjectExtraServices = findViewById(R.id.card_TitulosObjectService)
        eTxt_ObjectSize = findViewById(R.id.eTxt_ObjectSize)
        eTxt_ObjectForm = findViewById(R.id.eTxt_ObjectForm)
        eTxt_ObjectMaterial = findViewById(R.id.eTxt_ObjectMaterial)
        btn_Next = findViewById(R.id.btn_NextObjectOrderDetail)
        btn_Back = findViewById(R.id.btn_Back)
    }
}