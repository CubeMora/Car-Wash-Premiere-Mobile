package com.carwashpremiere.carwashpremieremobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Act_ServicesCars extends AppCompatActivity {
    FloatingActionButton fltBtn_Back;
    RecyclerView listServicesCars;

    Button btn_Back;
    Function_NetworkRequests networkRequests;
    Adapter_ServicesCars adapter_servicesCars;
    public static List<Model_ServicesCars> mServicesCarsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_act_services_cars);

        listServicesCars = findViewById(R.id.rList_ServicesCars);
        networkRequests = new Function_NetworkRequests(this);

        adapter_servicesCars = new Adapter_ServicesCars(this, mServicesCarsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listServicesCars.setLayoutManager(layoutManager);
        listServicesCars.setAdapter(adapter_servicesCars);
        getServicesCarsFromServer(networkRequests);
        btn_Back = findViewById(R.id.btn_Back);

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                intent = new Intent(Act_ServicesCars.this, Menu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

    }

    public void getServicesCarsFromServer(Function_NetworkRequests networkRequests) {
        mServicesCarsList.clear();
        networkRequests.getServicesCars(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayCategory = new JSONArray(response);

                    for (int i = 0; i < jsonArrayCategory.length(); i++) {
                        JSONObject jsonObject = jsonArrayCategory.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String shortDescription = jsonObject.getString("short_description");
                        String imageUrl = jsonObject.getString("imgUrl");
                        String activity = jsonObject.getString("screen_name");


                        mServicesCarsList.add(new Model_ServicesCars(id, title, imageUrl, description, shortDescription, activity));
                    }
                    adapter_servicesCars.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Act_ServicesCars.this, "Error al recuperar información: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}