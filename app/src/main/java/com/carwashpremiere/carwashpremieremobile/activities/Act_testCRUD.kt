package com.carwashpremiere.carwashpremieremobile.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.carwashpremiere.carwashpremieremobile.R
import com.carwashpremiere.carwashpremieremobile.functions.Interface_RetrofitMethods
import com.carwashpremiere.carwashpremieremobile.functions.RetrofitClient
import com.carwashpremiere.carwashpremieremobile.model.Data_RawData
import com.carwashpremiere.carwashpremieremobile.model.Data_Test

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class Act_testCRUD : AppCompatActivity() {

    var btn_showDialog: Button? = null
    var txt_resultCrud: TextView? = null
    var btn_cancelCrud: Button? = null
    var fieldsFromDB = mutableListOf<Data_Test>()
    var dialog: Dialog? = null
    val parentLayout: LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_test_crud)

        InitUI()


        // Supongamos que estos son los campos obtenidos de la fila seleccionada

// Crear el modal personalizado
        dialog = Dialog(this)
        dialog!!.setContentView(R.layout.modal_crud_test)

        btn_cancelCrud = dialog!!.findViewById(R.id.btn_cancelCrud)





// Manejar el botón de guardado



        btn_showDialog?.setOnClickListener {
            getCrudData()




        }

        btn_cancelCrud?.setOnClickListener { dialog!!.dismiss() }



    }
    fun InitUI() {
        btn_showDialog = findViewById(R.id.btn_showDialog)
        txt_resultCrud = findViewById(R.id.txt_resultCrud)

    }








    fun getCrudData() {

        val apiInterface = RetrofitClient.instance?.create(Interface_RetrofitMethods::class.java)
        val call = apiInterface?.getTest()

        call?.enqueue(object : Callback<Data_Test> {
            override fun onResponse(call: Call<Data_Test>, response: Response<Data_Test>) {
                if (response.isSuccessful) {



                    val data = response.body()
                    if (data != null) {
                        // Limpia la lista y agrega el objeto recibido
                        fieldsFromDB.clear()
                        Log.e("SI" , data.toString())
                        fieldsFromDB.add(data)
                        Log.e("SI" , fieldsFromDB.toString())

                        val parentLayout: LinearLayout? = dialog!!.findViewById(R.id.linLayout_EditText)
                        parentLayout?.removeAllViews() // Limpia las vistas previas

                        for (field in fieldsFromDB) {
                            Log.e("Field", field.toString())
                            val textView = TextView(applicationContext)

                            textView.text = "ID: ${field.id}, Name: ${field.name}, Population Date: ${field.populationDate}, Percentage: ${field.percentage}"
                            parentLayout?.addView(textView)
                            Log.e("txt", textView.text.toString())
                            val editText = EditText(applicationContext)
                            editText.setText(field.name) // O cualquier propiedad que quieras mostrar/editar
                            parentLayout?.addView(editText)
                        }

                        dialog?.show()

                        val saveButton: Button = dialog!!.findViewById(R.id.btn_updateCrud)
                        saveButton.setOnClickListener {
                            // Obtener los nuevos valores de los EditTexts
                            val newValues = mutableListOf<String>()
                            for (i in 0 until parentLayout!!.childCount step 2) {
                                val editText = parentLayout.getChildAt(i + 1) as EditText
                                newValues.add(editText.text.toString())
                            }

                            txt_resultCrud?.text = newValues.joinToString("\n")

                            // Aquí puedes actualizar la fila en la base de datos con los nuevos valores
                            // ...

                            dialog!!.dismiss()
                        }


                    }
                } else {
                    // Manejar el error de la respuesta
                    Toast.makeText(
                        this@Act_testCRUD,
                        "Error al recuperar información: " + response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Data_Test>, t: Throwable) {
                Toast.makeText(
                    this@Act_testCRUD,
                    "Error al recuperar información: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })


    }



}