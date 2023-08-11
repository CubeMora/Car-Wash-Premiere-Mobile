package com.carwashpremiere.carwashpremieremobile.functions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_Category
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_DetailsCar
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_DetailsObject
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_ExtraServicesCar
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_ExtraServicesObject
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_ServicesCars
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_ServicesObjects
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_Shortcuts
import com.carwashpremiere.carwashpremieremobile.model.Model_Category
import com.carwashpremiere.carwashpremieremobile.model.Model_DetailsGeneral
import com.carwashpremiere.carwashpremieremobile.model.Model_ExtraServicesGeneral
import com.carwashpremiere.carwashpremieremobile.model.Model_ServicesCars
import com.carwashpremiere.carwashpremieremobile.model.Model_ServicesObjects
import com.carwashpremiere.carwashpremieremobile.model.Model_Shortcuts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class Function_AdaptersUtility {

    fun createAdapterCategory(context: Context, rView: RecyclerView) {
        val apiInterface = RetrofitClient.instance?.create(Interface_RetrofitMethods::class.java)
        val call = apiInterface?.getCategories()

        call?.enqueue(object : Callback<List<Model_Category>> {
            override fun onResponse(call: Call<List<Model_Category>>, response: Response<List<Model_Category>>) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null) {
                        // Crear un nuevo adaptador con los datos recibidos y establecerlo en el RecyclerView
                        val adapter = Adapter_Category(context, dataList)
                        Log.e("Adapter Category", dataList.toString())
                        rView.adapter = adapter
                    }
                } else {
                    // Manejar el error de la respuesta
                    Toast.makeText(
                        context,
                        "Error al recuperar información: " + response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Model_Category>>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Error al recuperar información: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun createAdapterShortcuts(context: Context, rView: RecyclerView) {
        val apiInterface = RetrofitClient.instance?.create(Interface_RetrofitMethods::class.java)
        val call = apiInterface?.getShortcuts()

        call?.enqueue(object : Callback<List<Model_Shortcuts>> {
            override fun onResponse(call: Call<List<Model_Shortcuts>>, response: Response<List<Model_Shortcuts>>) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null) {
                        // Crear un nuevo adaptador con los datos recibidos y establecerlo en el RecyclerView
                        val adapter = Adapter_Shortcuts(context, dataList)
                        rView.adapter = adapter
                    }
                } else {
                    // Manejar el error de la respuesta
                    Toast.makeText(
                        context,
                        "Error al recuperar información: " + response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Model_Shortcuts>>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Error al recuperar información: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun createAdapterServicesCars(context: Context, rView: RecyclerView) {
        val apiInterface = RetrofitClient.instance?.create(Interface_RetrofitMethods::class.java)
        val call = apiInterface?.getServicesCars()

        call?.enqueue(object : Callback<List<Model_ServicesCars>> {
            override fun onResponse(call: Call<List<Model_ServicesCars>>, response: Response<List<Model_ServicesCars>>) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null) {
                        // Crear un nuevo adaptador con los datos recibidos y establecerlo en el RecyclerView
                        val adapter = Adapter_ServicesCars(context, dataList)
                        rView.adapter = adapter
                    }
                } else {
                    // Manejar el error de la respuesta
                    Toast.makeText(
                        context,
                        "Error al recuperar información: " + response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Model_ServicesCars>>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Error al recuperar información: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun createAdapterServicesObjects(context: Context, rView: RecyclerView) {
        val apiInterface = RetrofitClient.instance?.create(Interface_RetrofitMethods::class.java)
        val call = apiInterface?.getServicesObjects()

        call?.enqueue(object : Callback<List<Model_ServicesObjects>> {
            override fun onResponse(call: Call<List<Model_ServicesObjects>>, response: Response<List<Model_ServicesObjects>>) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null) {
                        // Crear un nuevo adaptador con los datos recibidos y establecerlo en el RecyclerView
                        val adapter = Adapter_ServicesObjects(context, dataList)
                        rView.adapter = adapter
                    }
                } else {
                    // Manejar el error de la respuesta
                    Toast.makeText(
                        context,
                        "Error al recuperar información: " + response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Model_ServicesObjects>>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Error al recuperar información: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun createAdapterDetailsCar(context: Context, rView: RecyclerView) {
        val apiInterface = RetrofitClient.instance?.create(Interface_RetrofitMethods::class.java)
        val call = apiInterface?.getCarDetails()

        call?.enqueue(object : Callback<List<Model_DetailsGeneral>> {
            override fun onResponse(call: Call<List<Model_DetailsGeneral>>, response: Response<List<Model_DetailsGeneral>>) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null) {
                        // Crear un nuevo adaptador con los datos recibidos y establecerlo en el RecyclerView
                        val adapter = Adapter_DetailsCar(context, dataList)
                        rView.adapter = adapter


                    }
                } else {
                    // Manejar el error de la respuesta
                    Toast.makeText(
                        context,
                        "Error al recuperar información: " + response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Model_DetailsGeneral>>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Error al recuperar información: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun createAdapterDetailsObject(context: Context, rView: RecyclerView, adapterDetailsObject: Adapter_DetailsObject) {
        val apiInterface = RetrofitClient.instance?.create(Interface_RetrofitMethods::class.java)
        val call = apiInterface?.getObjectDetails()

        call?.enqueue(object : Callback<List<Model_DetailsGeneral>> {
            override fun onResponse(call: Call<List<Model_DetailsGeneral>>, response: Response<List<Model_DetailsGeneral>>) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null) {
                        // Assign the data to the existing adapter
                        adapterDetailsObject?.setData(dataList)
                        // Notify the adapter about the data change
                        adapterDetailsObject?.notifyDataSetChanged()
                    }
                } else {
                    // Manejar el error de la respuesta
                    Toast.makeText(
                        context,
                        "Error al recuperar información: " + response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Model_DetailsGeneral>>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Error al recuperar información: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun createAdapterExtraServicesCar(context: Context, rView: RecyclerView) {
        val apiInterface = RetrofitClient.instance?.create(Interface_RetrofitMethods::class.java)
        val call = apiInterface?.getExtraServicesCars()

        call?.enqueue(object : Callback<List<Model_ExtraServicesGeneral>> {
            override fun onResponse(call: Call<List<Model_ExtraServicesGeneral>>, response: Response<List<Model_ExtraServicesGeneral>>) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null) {
                        // Crear un nuevo adaptador con los datos recibidos y establecerlo en el RecyclerView
                        val adapter = Adapter_ExtraServicesCar(context, dataList)
                        rView.adapter = adapter
                    }
                } else {
                    // Manejar el error de la respuesta
                    Toast.makeText(
                        context,
                        "Error al recuperar información: " + response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Model_ExtraServicesGeneral>>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Error al recuperar información: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun createAdapterExtraServicesObject(context: Context, rView: RecyclerView, adapterExtraServiceObject: Adapter_ExtraServicesObject?) {
        val apiInterface = RetrofitClient.instance?.create(Interface_RetrofitMethods::class.java)
        val call = apiInterface?.getExtraServicesObjects()

        call?.enqueue(object : Callback<List<Model_ExtraServicesGeneral>> {
            override fun onResponse(
                call: Call<List<Model_ExtraServicesGeneral>>,
                response: Response<List<Model_ExtraServicesGeneral>>
            ) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null) {
                        // Assign the data to the existing adapter
                        adapterExtraServiceObject?.setData(dataList)
                        // Notify the adapter about the data change
                        adapterExtraServiceObject?.notifyDataSetChanged()
                    }
                } else {
                    // Handle the error response
                    Toast.makeText(
                        context,
                        "Error al recuperar información: " + response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Model_ExtraServicesGeneral>>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Error al recuperar información: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }


}
