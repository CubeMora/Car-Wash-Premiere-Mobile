package com.carwashpremiere.carwashpremieremobile.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.carwashpremiere.carwashpremieremobile.functions.Function_NetworkRequests
import com.carwashpremiere.carwashpremieremobile.R
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_CarTypeAdapter
import com.carwashpremiere.carwashpremieremobile.functions.Interface_RetrofitMethods
import com.carwashpremiere.carwashpremieremobile.functions.RetrofitClient
import com.carwashpremiere.carwashpremieremobile.model.Model_CarTypes
import com.carwashpremiere.carwashpremieremobile.model.Model_DetailsGeneral
import com.carwashpremiere.carwashpremieremobile.model.Model_ServicesCars
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Act_DescriptionCars : AppCompatActivity() {
    var btn_Next: Button? = null
    var btn_Back: Button? = null
    var txt_ServiceDescription: TextView? = null
    var txt_title: TextView? = null
    var txt_TitleCarServiceDetail: TextView? = null
    var adapterCarType: Adapter_CarTypeAdapter? = null

    var adapter: ArrayAdapter<Model_CarTypes>? = null
    var selectedAuto: String? = null
    var sp_CarType: Spinner? = null
    var shortDescription: String? = null
    var title: String? = null
    var serviceName: String? = null

    //    Spinner sp_CarType;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()

        setContentView(R.layout.activity_act_description_cars)

        InitUI()



        val intent = intent
        serviceName = intent.getStringExtra("serviceTitle")
        Log.e("Intent", serviceName.toString())
        getCarTypesSpinner()
        getService()



        btn_Back!!.setOnClickListener(View.OnClickListener { // Terminar la actividad y regresar al menú principal
            val intent = Intent(this@Act_DescriptionCars, Menu::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        })

        btn_Next!!.setOnClickListener(View.OnClickListener {
            if (selectedAuto != null) {
                val intent = Intent(this@Act_DescriptionCars, Act_CarParameters::class.java)
                intent.putExtra("serviceTitle", serviceName)
                intent.putExtra("carType", selectedAuto!!)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this@Act_DescriptionCars,
                    "Por favor, seleccione un tipo de automóvil.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun InitUI(){
        txt_ServiceDescription = findViewById(R.id.txt_ServiceDescription)
        sp_CarType = findViewById(R.id.sp_CarType);
        txt_title = findViewById(R.id.txt_serviceCarTitle)

        btn_Next = findViewById(R.id.btn_NextObjectOrderDetail)
        btn_Back = findViewById(R.id.btn_Back)
        sp_CarType = findViewById(R.id.sp_CarType)
        btn_Next!!.setVisibility(View.GONE)

    }
    fun getService(){
        //TODO Assignate the specific api for service cars name
        val apiInterface = RetrofitClient.instance?.create(Interface_RetrofitMethods::class.java)
        val call = apiInterface?.getCarDescription_Name("11", serviceName.toString())

        call?.enqueue(object : Callback<Model_ServicesCars> {
            override fun onResponse(call: Call<Model_ServicesCars>, response: Response<Model_ServicesCars>){
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null) {
                        txt_ServiceDescription?.text = dataList.description
                        txt_title?.text = dataList.title

                    }



                } else {
                    Toast.makeText(
                        applicationContext,
                        "Error al recuperar información: " + response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            override fun onFailure(call: Call<Model_ServicesCars>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Error al recuperar información: " + t.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
    fun getCarTypesSpinner(){
        val apiInterface = RetrofitClient.instance?.create(Interface_RetrofitMethods::class.java)
        val call = apiInterface?.getCarType()

        call?.enqueue(object : Callback<List<Model_CarTypes>> {
            override fun onResponse(call: Call<List<Model_CarTypes>>, response: Response<List<Model_CarTypes>>) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null)
                    {
                        val carTypeTitles = dataList.map { it.title }
                        val adapter = ArrayAdapter(
                            this@Act_DescriptionCars,
                            android.R.layout.simple_spinner_item,
                            carTypeTitles
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        sp_CarType?.adapter = adapter

                        sp_CarType?.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    adapterView: AdapterView<*>,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    selectedAuto =
                                        adapterView.getItemAtPosition(position) as String

                                    if (selectedAuto != "Seleccione una opción") {
                                        btn_Next?.visibility = View.VISIBLE
                                    } else {
                                        btn_Next?.visibility = View.GONE
                                    }
                                }

                                override fun onNothingSelected(adapterView: AdapterView<*>) {
                                    // No hacer nada si no se seleccionó ningún elemento
                                }
                            }
                    }



                } else {
                    // Manejar el error de la respuesta
                    Toast.makeText(
                        applicationContext,
                        "Error al recuperar información: " + response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Model_CarTypes>>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Error al recuperar información: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

}