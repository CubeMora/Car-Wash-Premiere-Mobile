package com.carwashpremiere.carwashpremieremobile.functions

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Function_NetworkRequests(private val context: Context) {

    private val URL_GET_CARTYPES = "https://cubemora-forum.000webhostapp.com/carwash/cartypes.php"
    private val URL_GET_SERVICESCARSDESCRIPTION =
        "https://cubemora-forum.000webhostapp.com/carwash/servicescardescriptions.php?service_name="
    private val URL_GET_PHONE = "https://cubemora-forum.000webhostapp.com/carwash/phone.php"

    private val requestQueue: RequestQueue

    init {
        requestQueue = Volley.newRequestQueue(context)
    }



    fun getCarTypes(
        responseListener: Response.Listener<String?>?,
        errorListener: Response.ErrorListener?
    ) {
        val stringRequest = StringRequest(
            Request.Method.GET, URL_GET_CARTYPES,
            responseListener, errorListener
        )
        requestQueue.add(stringRequest)
    }

    fun getServicesCarDescription(
        responseListener: Response.Listener<String?>?,
        errorListener: Response.ErrorListener?,
        service: String
    ) {
        val stringRequest = StringRequest(
            Request.Method.GET, URL_GET_SERVICESCARSDESCRIPTION + service,
            responseListener, errorListener
        )
        //Log.e("Ekishh", "" + stringRequest);
        requestQueue.add(stringRequest)
    }

    fun getPhone(
        responseListener: Response.Listener<String?>?,
        errorListener: Response.ErrorListener?
    ) {
        val stringRequest = StringRequest(
            Request.Method.GET, URL_GET_PHONE,
            responseListener, errorListener
        )
        requestQueue.add(stringRequest)
    }


}