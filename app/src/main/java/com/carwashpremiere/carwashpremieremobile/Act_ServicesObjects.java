package com.carwashpremiere.carwashpremieremobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Act_ServicesObjects extends AppCompatActivity {
    RecyclerView listServicesObjects;
    Function_NetworkRequests networkRequests;
    Adapter_ServicesObjects adapter_servicesObjects;
    public static List<Model_ServicesObjects> mServicesObjectsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_act_services_objects);

        listServicesObjects = findViewById(R.id.rList_Objects);
        networkRequests = new Function_NetworkRequests(this);

        adapter_servicesObjects = new Adapter_ServicesObjects(this, mServicesObjectsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listServicesObjects.setLayoutManager(layoutManager);
        listServicesObjects.setAdapter(adapter_servicesObjects);
        getServicesObjectsFromServer(networkRequests);
    }

    public void getServicesObjectsFromServer(Function_NetworkRequests networkRequests) {
        mServicesObjectsList.clear();
        networkRequests.getServicesObjects(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response", response);

                try {
                    JSONArray jsonArrayCategory = new JSONArray(response);

                    for (int i = 0; i < jsonArrayCategory.length(); i++) {
                        JSONObject jsonObject = jsonArrayCategory.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String imageUrl = jsonObject.getString("imgUrl");
                        String activity = jsonObject.getString("screen_name");


                        mServicesObjectsList.add(new Model_ServicesObjects(id, title, description, imageUrl, activity));
                    }
                    adapter_servicesObjects.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Act_ServicesObjects.this, "Error al recuperar información: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}