package com.carwashpremiere.carwashpremieremobile;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Function_NetworkRequests {
    private final String URL_GET_CATEGORIES = "https://cubemora-forum.000webhostapp.com/carwash/categories.php";
    private final String URL_GET_SHORTCUTS = "https://cubemora-forum.000webhostapp.com/carwash/shortcuts.php";
    private final String URL_GET_SERVICESCARS = "https://cubemora-forum.000webhostapp.com/carwash/servicescarslist.php";

    private final String URL_GET_SERVICESOBJECTS = "https://cubemora-forum.000webhostapp.com/carwash/servicesobjectslist.php";
    private final String URL_GET_EXTRASERVICESCARS = "https://cubemora-forum.000webhostapp.com/carwash/extraservicescars.php";
    private final String URL_GET_DETAILSCAR = "https://cubemora-forum.000webhostapp.com/carwash/detailscar.php";

    private final String URL_GET_CARTYPES = "https://cubemora-forum.000webhostapp.com/carwash/cartypes.php";

    private final String URL_GET_SERVICESCARSDESCRIPTION = "https://cubemora-forum.000webhostapp.com/carwash/servicescardescriptions.php?service_name=";

    private final String URL_GET_PHONE = "https://cubemora-forum.000webhostapp.com/carwash/phone.php";

    private final String URL_GET_EXTRASERVICESOBJECTS = "https://cubemora-forum.000webhostapp.com/carwash/extraservicesobjects.php";
    private final String URL_GET_DETAILSOBJECTS = "https://cubemora-forum.000webhostapp.com/carwash/detailsobjects.php";



    private RequestQueue requestQueue;
    private Context context;

    public Function_NetworkRequests(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void getCategories(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_CATEGORIES,
                responseListener, errorListener);

        requestQueue.add(stringRequest);
    }
    public void getShortcuts(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_SHORTCUTS,
                responseListener, errorListener);

        requestQueue.add(stringRequest);
    }
    public void getServicesCars(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_SERVICESCARS,
                responseListener, errorListener);

        requestQueue.add(stringRequest);
    }
    public void getServicesObjects(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_SERVICESOBJECTS,
                responseListener, errorListener);

        requestQueue.add(stringRequest);
    }

    public void getExtraServiceCars(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_EXTRASERVICESCARS,
                responseListener, errorListener);

        requestQueue.add(stringRequest);
    }
    public void getDetailsCar(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_DETAILSCAR,
                responseListener, errorListener);

        requestQueue.add(stringRequest);
    }

    public void getCarTypes(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_CARTYPES,
                responseListener, errorListener);

        requestQueue.add(stringRequest);
    }

    public void getServicesCarDescription(Response.Listener<String> responseListener, Response.ErrorListener errorListener, String service) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_SERVICESCARSDESCRIPTION + service,
                responseListener, errorListener);
        //Log.e("Ekishh", "" + stringRequest);

        requestQueue.add(stringRequest);
    }

    public void getPhone(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_PHONE,
                responseListener, errorListener);

        requestQueue.add(stringRequest);
    }
    public void getExtraServiceObject(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_EXTRASERVICESOBJECTS,
                responseListener, errorListener);

        requestQueue.add(stringRequest);
    }

    public void getDetailObject(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_DETAILSOBJECTS,
                responseListener, errorListener);

        requestQueue.add(stringRequest);
    }

}
