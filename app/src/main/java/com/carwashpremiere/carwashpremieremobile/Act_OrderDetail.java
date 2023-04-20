package com.carwashpremiere.carwashpremieremobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
    String bundleType;
    String phoneNumberTemp;
    Bundle bundle;
    Function_NetworkRequests networkRequests;
    CardView cardView_TitleSpecifications, cardView_Specifications, cardView_TitleCarService, cardView_CarService;

    TextView txt_TotalPrice, txt_ServiceTitle, txt_objectDetail, txt_ExtraServices, txt_Details, txt_Specifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_act_order_detail);
        initUI();

        networkRequests = new Function_NetworkRequests(this);
        getPhoneNumberFromServer(networkRequests);
        //Log.e("phoneNumber", phoneNumber);


        btn_WA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasClickedWA = true;
                String message = "";

                getPhoneNumberFromServer(networkRequests);

                if (bundleType.equals("car")) {
                    String service_title = txt_ServiceTitle.getText().toString();
                    String car_type = txt_objectDetail.getText().toString();
                    String extra_services = txt_ExtraServices.getText().toString();
                    String details = txt_Details.getText().toString();


                    message = "Hola, quisiera solicitar una cotización para el servicio de " + service_title
                            + " con el siguiente detalle: \n\n"
                            + "Tipo de vehículo: " + car_type + "\n"
                            + "Servicios extra: " + extra_services + "\n"
                            + "Detalles adicionales: " + details + "\n\n"
                            //+ "Precio total: " + total_price + "\n\n"
                            + "¿Podrían proporcionarme una cotización para este servicio? Gracias.";
                } else if (bundleType.equals("object")) {
                    String object_title = bundle.getString("objectTitle");
                    String object_specifications = txt_Specifications.getText().toString();
                    String object_extraServices = txt_ExtraServices.getText().toString();
                    String object_details = txt_Details.getText().toString();

                    message = "Hola, quisiera solicitar una cotización para el objeto:  " + object_title + "." +
                            "\n\n" + "Con las siguientes especificaciones: " + object_specifications + "\n\n" +
                            "Servicios extra: " + object_extraServices + "\n\n" +
                            "Detalles adicionales: " + object_details + "\n\n" +
                            "¿Podrían proporcionarme una cotización para este objeto? Gracias.";

                }

//                String total_price = txt_TotalPrice.getText().toString();


                String url = "https://api.whatsapp.com/send?phone=" + phoneNumberTemp + "&text=" + message;
                Log.e("URL", url);
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
                    Log.e("ON RESPONSE", phoneNumberTemp);


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
        txt_objectDetail = findViewById(R.id.txt_ObjectDetail);
        txt_ExtraServices = findViewById(R.id.txt_ExtraServices);
        txt_Details = findViewById(R.id.txt_ExtraDetails);
        txt_Specifications = findViewById(R.id.txt_ObjectSpecifications);
        btn_Next = findViewById(R.id.btn_MainMenu);
        cardView_CarService = findViewById(R.id.card_serviceDescription);
        cardView_TitleCarService = findViewById(R.id.card_TitulosParameterCar);
        cardView_Specifications = findViewById(R.id.card_objectSpecifications);
        cardView_TitleSpecifications = findViewById(R.id.card_TitulosSpecificationsObject);


        bundle = getIntent().getExtras();

        // Descomprimir los valores del Bundle y mostrarlos en los TextViews correspondientes
        if (bundle != null) {
            bundleType = bundle.getString("bundleType");

            if (bundleType.contentEquals("car")) {
                cardView_Specifications.setVisibility(View.GONE);
                cardView_TitleSpecifications.setVisibility(View.GONE);

                txt_ServiceTitle.setText(bundle.getString("serviceTitle"));
                txt_objectDetail.setText("Automóvil tipo: " + bundle.getString("carType"));
            } else if (bundleType.contentEquals("object")) {
                cardView_TitleCarService.setVisibility(View.GONE);
                cardView_CarService.setVisibility(View.GONE);

                txt_objectDetail.setText("Objeto: " + bundle.getString("objectTitle"));
                txt_Specifications.setText("Tamaño: " + bundle.getString("objectSize") + " cm" + "\n" +
                        "Forma: " + bundle.getString("objectForm") + "\n" +
                        "Material: " + bundle.getString("objectMaterial"));
            }

//            txt_TotalPrice.setText("$ " + bundle.getDouble("totalPrice"));
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