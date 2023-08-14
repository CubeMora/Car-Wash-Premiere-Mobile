package com.carwashpremiere.carwashpremieremobile.activities

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carwashpremiere.carwashpremieremobile.R
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_DetailsCar
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_ExtraServicesCar
import com.carwashpremiere.carwashpremieremobile.functions.Function_AdaptersUtility
import com.carwashpremiere.carwashpremieremobile.model.Model_DetailsGeneral
import com.carwashpremiere.carwashpremieremobile.model.Model_ExtraServicesGeneral

class Act_CarParameters : AppCompatActivity() {
    var adapterExtraServicesCar: Adapter_ExtraServicesCar? = null
    var adapterDetailsCar: Adapter_DetailsCar? = null


    var txt_TotalPrice: TextView? = null
    var txt_ServiceTitle: TextView? = null
    var txt_CarType: TextView? = null
    var List_ExtraServicesCar: RecyclerView? = null
    var List_DetailsCar: RecyclerView? = null
    var btn_Next: Button? = null
    var btn_Back: Button? = null
    var mExtraServicesCar: List<Model_ExtraServicesGeneral> = ArrayList()
    var mDetailsCar: List<Model_DetailsGeneral> = ArrayList()

    // Define initial value for total price
    private var totalPrice = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_act_car_parameters)
        initUI()
        structureAdapters()


        val intent = intent
        txt_ServiceTitle!!.text = intent.getStringExtra("serviceTitle")
        txt_CarType!!.text = intent.getStringExtra("carType")







        // Set the initial value of total price in the UI
        txt_TotalPrice!!.text = String.format("Total: $%.2f", totalPrice)
        adapterExtraServicesCar!!.setOnTotalPriceChangedListener(object :
            Adapter_ExtraServicesCar.OnTotalPriceChangedListener {
            override fun onTotalPriceChanged(totalPrice: Double) {
                // Update the class-level totalPrice variable
                this@Act_CarParameters.totalPrice = totalPrice

                // Update the UI with the new total price
                txt_TotalPrice!!.text = String.format("Total: $%.2f", totalPrice)
            }
        })
        btn_Back!!.setOnClickListener { // Terminar la actividad y regresar al menú principal
            val intent = Intent(this@Act_CarParameters, Act_DescriptionCars::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
        btn_Next!!.setOnClickListener { // Crear un Bundle y agregar los valores que quieres enviar a la actividad final
            val bundle = Bundle()
            bundle.putString("serviceTitle", txt_ServiceTitle!!.text.toString())
            bundle.putString("carType", txt_CarType!!.text.toString())
            bundle.putStringArrayList(
                "extraServices", ArrayList(
                    adapterExtraServicesCar!!.getSelectedServices()
                )
            )
            bundle.putStringArrayList(
                "details", ArrayList(
                    adapterDetailsCar!!.getSelectedDetails()
                )
            )
            bundle.putString("bundleType", "car")
            bundle.putDouble("totalPrice", totalPrice)


            // Iniciar la actividad final y pasar el Bundle como argumento
            val intent = Intent(this@Act_CarParameters, Act_OrderDetail::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun structureAdapters() {
        val adaptersUtility = Function_AdaptersUtility(this)

        val linearLayoutManagerExtra = LinearLayoutManager(this)
        linearLayoutManagerExtra.orientation = LinearLayoutManager.VERTICAL
        List_ExtraServicesCar!!.layoutManager = linearLayoutManagerExtra
        adapterExtraServicesCar = Adapter_ExtraServicesCar(this, mExtraServicesCar)
        List_ExtraServicesCar!!.adapter = adapterExtraServicesCar

        val linearLayoutManagerDetails = LinearLayoutManager(this)
        linearLayoutManagerDetails.orientation = LinearLayoutManager.VERTICAL
        List_DetailsCar!!.layoutManager = linearLayoutManagerDetails
        adapterDetailsCar = Adapter_DetailsCar(this, mDetailsCar)
        List_DetailsCar!!.adapter = adapterDetailsCar

        //TODO: Adapt the adapters call to accept a create adapter like in details/extra services objects
        adaptersUtility.createAdapterExtraServicesCar(this, List_ExtraServicesCar!!, adapterExtraServicesCar!!)
        adaptersUtility.createAdapterDetailsCar(this, List_DetailsCar!!, adapterDetailsCar!!)
    }

    private fun initUI() {
        List_ExtraServicesCar = findViewById(R.id.rList_ExtraServicesCar)
        txt_TotalPrice = findViewById(R.id.txt_TotalPrice)
        List_DetailsCar = findViewById(R.id.rList_DetailsCar)
        btn_Next = findViewById(R.id.btn_NextObjectOrderDetail)
        txt_ServiceTitle = findViewById(R.id.txt_HeaderCarServiceDetail)
        txt_CarType = findViewById(R.id.txt_ServiceDescription)
        btn_Back = findViewById(R.id.btn_Back)
    }


}