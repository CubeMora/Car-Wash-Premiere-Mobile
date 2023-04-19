package com.carwashpremiere.carwashpremieremobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Act_OrderDetail extends AppCompatActivity {
Button btn_WA, btn_Next;
boolean hasClickedWA = false;
String phoneNumber;
String phoneNumberTemp;
Function_NetworkRequests networkRequests;

TextView txt_TotalPrice, txt_ServiceTitle, txt_CarType, txt_ExtraServices, txt_Details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_act_order_detail);
        initUI();

        networkRequests = new Function_NetworkRequests(this);
        getPhoneNumberFromServer(networkRequests);
        Log.e("phoneNumber", phoneNumber);


        btn_WA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasClickedWA = true;

                getPhoneNumberFromServer(networkRequests);

//                String total_price = txt_TotalPrice.getText().toString();
                String service_title = txt_ServiceTitle.getText().toString();
                String car_type = txt_CarType.getText().toString();
                String extra_services = txt_ExtraServices.getText().toString();
                String details = txt_Details.getText().toString();

                String message = "Hola, quisiera solicitar una cotización para el servicio de " + service_title
                        + " con el siguiente detalle: \n\n"
                        + "Tipo de vehículo: " + car_type + "\n"
                        + "Servicios extra: " + extra_services + "\n"
                        + "Detalles adicionales: " + details + "\n\n"
                        //+ "Precio total: " + total_price + "\n\n"
                        + "¿Podrían proporcionarme una cotización para este servicio? Gracias.";

                String url = "https://api.whatsapp.com/send?phone="+ phoneNumberTemp + "&text=" + message;
                Log.e("URL",url);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Terminar la actividad y regresar al menú principal

                Intent intent = new Intent(Act_OrderDetail.this, Menu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
            }
        });


    }

    public void getPhoneNumberFromServer(Function_NetworkRequests networkRequests) {
        phoneNumber = "";
        networkRequests.getPhone(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    phoneNumberTemp = jsonObject.getString("phone");
                    Log.e("ON RESPONSE",phoneNumberTemp);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_OrderDetail.this, "Error al recuperar información: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasClickedWA) {
            btn_WA.setVisibility(View.GONE);
            btn_Next.setVisibility(View.VISIBLE);
        } else {
            btn_WA.setVisibility(View.VISIBLE);
            btn_Next.setVisibility(View.GONE);
        }
    }

    void initUI() {
        btn_WA = findViewById(R.id.btn_NextObjectOrderDetail);
        txt_TotalPrice = findViewById(R.id.txt_TotalPrice);
        txt_ServiceTitle = findViewById(R.id.txt_ServiceDescription);
        txt_CarType = findViewById(R.id.txt_ObjectDetail);
        txt_ExtraServices = findViewById(R.id.txt_ExtraServices);
        txt_Details = findViewById(R.id.txt_ExtraDetails);
        btn_Next = findViewById(R.id.btn_MainMenu);


        // Obtener el Bundle enviado desde la actividad anterior
        Bundle bundle = getIntent().getExtras();

        // Descomprimir los valores del Bundle y mostrarlos en los TextViews correspondientes
        if (bundle != null) {
//            txt_TotalPrice.setText("$ " + bundle.getDouble("totalPrice"));
            txt_ServiceTitle.setText(bundle.getString("serviceTitle"));
            txt_CarType.setText(bundle.getString("carType"));

            // Comprobar si hay servicios extra seleccionados y mostrarlos en una lista o un mensaje predeterminado
            ArrayList<String> extraServices = bundle.getStringArrayList("extraServices");
            if (extraServices != null && extraServices.size() > 0) {
                StringBuilder servicesText = new StringBuilder();
                for (String service : extraServices) {
                    servicesText.append("- ").append(service).append("\n");
                }
                txt_ExtraServices.setText(servicesText.toString());
            } else {
                txt_ExtraServices.setText("N/A");
            }

            // Comprobar si hay detalles seleccionados y mostrarlos en una lista o un mensaje predeterminado
            ArrayList<String> details = bundle.getStringArrayList("details");
            if (details != null && details.size() > 0) {
                StringBuilder detailsText = new StringBuilder();
                for (String detail : details) {
                    detailsText.append("- ").append(detail).append("\n");
                }
                txt_Details.setText(detailsText.toString());
            } else {
                txt_Details.setText("N/A");
            }
        }
    }

}