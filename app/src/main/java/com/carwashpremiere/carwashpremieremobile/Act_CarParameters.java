package com.carwashpremiere.carwashpremieremobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Act_CarParameters extends AppCompatActivity {
    public static List<Model_ExtraServicesCar> mExtraServicesCarsList = new ArrayList<>();
    public static List<Model_DetailsCar> mDetailsCarList = new ArrayList<>();
    Adapter_ExtraServicesCar adapterExtraServicesCar;
    Adapter_DetailsCar adapterDetailsCar;
    Function_NetworkRequests networkRequests;
    TextView txt_TotalPrice, txt_ServiceTitle, txt_CarType;
    RecyclerView List_ExtraServicesCar, List_DetailsCar;
    Button btn_Next, btn_Back;

    // Define initial value for total price
    private double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_act_car_parameters);

        initUI();
        Intent intent = getIntent();
        txt_ServiceTitle.setText(intent.getStringExtra("serviceTitle"));
        txt_CarType.setText(intent.getStringExtra("carType"));


        networkRequests = new Function_NetworkRequests(this);

        adapterExtraServicesCar = new Adapter_ExtraServicesCar(this, mExtraServicesCarsList);
        LinearLayoutManager linearLayoutManagerExtra = new LinearLayoutManager(this);
        linearLayoutManagerExtra.setOrientation(LinearLayoutManager.VERTICAL);

        List_ExtraServicesCar.setLayoutManager(linearLayoutManagerExtra);
        List_ExtraServicesCar.setAdapter(adapterExtraServicesCar);
        getExtraServicesCarsFromServer(networkRequests);

        adapterDetailsCar = new Adapter_DetailsCar(this, mDetailsCarList);
        LinearLayoutManager linearLayoutManagerDetails = new LinearLayoutManager(this);
        linearLayoutManagerDetails.setOrientation(LinearLayoutManager.VERTICAL);

        List_DetailsCar.setLayoutManager(linearLayoutManagerDetails);
        List_DetailsCar.setAdapter(adapterDetailsCar);
        getDetailsCarFromServer(networkRequests);


        // Set the initial value of total price in the UI
        txt_TotalPrice.setText(String.format("Total: $%.2f", totalPrice));

        adapterExtraServicesCar.setOnTotalPriceChangedListener(new Adapter_ExtraServicesCar.OnTotalPriceChangedListener() {
            @Override
            public void onTotalPriceChanged(double totalPrice) {
                // Update the class-level totalPrice variable
                Act_CarParameters.this.totalPrice = totalPrice;

                // Update the UI with the new total price
                txt_TotalPrice.setText(String.format("Total: $%.2f", totalPrice));
            }
        });


        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Terminar la actividad y regresar al menú principal

                Intent intent = new Intent(Act_CarParameters.this, Act_DescriptionCars.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
            }
        });

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Bundle y agregar los valores que quieres enviar a la actividad final
                Bundle bundle = new Bundle();
                bundle.putString("serviceTitle", txt_ServiceTitle.getText().toString());
                bundle.putString("carType", txt_CarType.getText().toString());
                bundle.putStringArrayList("extraServices", new ArrayList<>(adapterExtraServicesCar.getSelectedServices()));
                bundle.putStringArrayList("details", new ArrayList<>(adapterDetailsCar.getSelectedDetails()));
                bundle.putString("bundleType", "car");
                bundle.putDouble("totalPrice", totalPrice);


                // Iniciar la actividad final y pasar el Bundle como argumento
                Intent intent = new Intent(Act_CarParameters.this, Act_OrderDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void initUI() {
        List_ExtraServicesCar = findViewById(R.id.rList_ExtraServicesCar);
        txt_TotalPrice = findViewById(R.id.txt_TotalPrice);
        List_DetailsCar = findViewById(R.id.rList_DetailsCar);
        btn_Next = findViewById(R.id.btn_NextObjectOrderDetail);
        txt_ServiceTitle = findViewById(R.id.txt_HeaderCarServiceDetail);
        txt_CarType = findViewById(R.id.txt_ServiceDescription);
        btn_Back = findViewById(R.id.btn_Back);

    }

    public void getExtraServicesCarsFromServer(Function_NetworkRequests networkRequests) {
        mExtraServicesCarsList.clear();
        networkRequests.getExtraServiceCars(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayCategory = new JSONArray(response);

                    for (int i = 0; i < jsonArrayCategory.length(); i++) {
                        JSONObject jsonObject = jsonArrayCategory.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String price = jsonObject.getString("price");

                        mExtraServicesCarsList.add(new Model_ExtraServicesCar(id, title, price));
                    }

                    adapterExtraServicesCar.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Act_CarParameters.this, "Error al recuperar información", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getDetailsCarFromServer(Function_NetworkRequests networkRequests) {
        mDetailsCarList.clear();
        networkRequests.getDetailsCar(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayCategory = new JSONArray(response);

                    for (int i = 0; i < jsonArrayCategory.length(); i++) {
                        JSONObject jsonObject = jsonArrayCategory.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");


                        mDetailsCarList.add(new Model_DetailsCar(id, title));
                    }

                    adapterDetailsCar.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Act_CarParameters.this, "Error al recuperar información", Toast.LENGTH_LONG).show();
            }
        });
    }

}

