package com.carwashpremiere.carwashpremieremobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Act_DescriptionCars extends AppCompatActivity {
    Button btn_Next;
    TextView txt_ServiceDescription, txt_TitleCarServiceDetail;
    public static List<Model_CarTypes> mCarTypesList = new ArrayList<>();
    public static List<Model_ServicesCars> mServicesCarsList = new ArrayList<>();
    Function_NetworkRequests networkRequests;
    ArrayAdapter<Model_CarTypes> adapter;
    Model_CarTypes selectedAuto;
    String shortDescription, title, serviceName;
    Spinner sp_CarType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_act_description_cars);

        txt_ServiceDescription = findViewById(R.id.txt_ServiceDescription);
        sp_CarType = findViewById(R.id.sp_CarType);
        btn_Next = findViewById(R.id.btn_NextObjectOrderDetail);
        txt_TitleCarServiceDetail = findViewById(R.id.txt_HeaderCarServiceDetail);

        btn_Next.setVisibility(View.GONE);

        Intent intent = getIntent();
        serviceName = intent.getStringExtra("serviceTitle");


        networkRequests = new Function_NetworkRequests(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mCarTypesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_CarType.setAdapter(adapter);

        getCarTypesFromServer(networkRequests);
        getServicesCarsFromServer(networkRequests);

        sp_CarType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                selectedAuto = (Model_CarTypes) adapterView.getItemAtPosition(position);


                if (!selectedAuto.getTitle().equals("Seleccione una opción")) {

                    btn_Next.setVisibility(View.VISIBLE);
                } else {

                    btn_Next.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada si no se seleccionó ningún elemento
            }
        });



        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_DescriptionCars.this, Act_CarParameters.class);
                intent.putExtra("serviceTitle", serviceName);
                intent.putExtra("carType", selectedAuto.getTitle());
                startActivity(intent);
            }
        });



    }

    private void getCarTypesFromServer(Function_NetworkRequests networkRequests) {
        mCarTypesList.clear();
        mCarTypesList.add(new Model_CarTypes(1000, "Seleccione una opción"));
        networkRequests.getCarTypes(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayCategory = new JSONArray(response);

                    for (int i = 0; i < jsonArrayCategory.length(); i++) {
                        JSONObject jsonObject = jsonArrayCategory.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");


                        mCarTypesList.add(new Model_CarTypes(id, title));
                    }

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Act_DescriptionCars.this, "Error al recuperar información", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getServicesCarsFromServer(Function_NetworkRequests networkRequests) {
        mServicesCarsList.clear();
        networkRequests.getServicesCarDescription(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    shortDescription = jsonObject.getString("description");
                    title = jsonObject.getString("title");

                    txt_ServiceDescription.setText(shortDescription);
                    txt_TitleCarServiceDetail.setText(title);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_DescriptionCars.this, "Error al recuperar información: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, serviceName);
    }



}

