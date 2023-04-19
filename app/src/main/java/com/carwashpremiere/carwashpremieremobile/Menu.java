package com.carwashpremiere.carwashpremieremobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {
    public static List<Model_Category> mCategoryList = new ArrayList<>();
    public static List<Model_Shortcuts> mShortcutsList = new ArrayList<>();
    Button btn_Services;
    ImageButton imgBtn_Atajo1;
    ProgressBar bar_loadingMenu;
    private RecyclerView listCategory, listShortcuts;

    private static Adapter_Category adapterCategory;
    private Adapter_Shortcuts adapterShortcuts;
    Function_NetworkRequests networkRequests;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);
        initUI();

        networkRequests = new Function_NetworkRequests(this);
        ClearList();


        //TODO Separate this adapter sets to an async task
        //Category Adapter

        adapterCategory = new Adapter_Category(this, mCategoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listCategory.setLayoutManager(layoutManager);
        listCategory.setAdapter(adapterCategory);
        getCategoriesFromServer(networkRequests);

        //Shortcuts Adapter


        adapterShortcuts = new Adapter_Shortcuts(this, mShortcutsList);
        LinearLayoutManager layoutManagerShortcuts = new LinearLayoutManager(this);
        layoutManagerShortcuts.setOrientation(LinearLayoutManager.HORIZONTAL);
        listShortcuts.setLayoutManager(layoutManagerShortcuts);
        listShortcuts.setAdapter(adapterShortcuts);
        getShortcutsFromServer(networkRequests);


    }

    private void initUI(){
        listCategory = findViewById(R.id.rList_Category);
        imgBtn_Atajo1 = findViewById(R.id.imgBtn_Atajo1);
        listShortcuts = findViewById(R.id.rList_Shortcuts);
        


    }
    public void ClearList(){
        mCategoryList.clear();
        mShortcutsList.clear();
    }
    public void getCategoriesFromServer(Function_NetworkRequests networkRequests) {
        mCategoryList.clear();
        networkRequests.getCategories(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayCategory = new JSONArray(response);

                    for (int i = 0; i < jsonArrayCategory.length(); i++) {
                        JSONObject jsonObject = jsonArrayCategory.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String imageUrl = jsonObject.getString("imageUrl");
                        String description = jsonObject.getString("description");
                        String activity = jsonObject.getString("screen_name");


                        mCategoryList.add(new Model_Category(id, title, imageUrl, description, activity));
                    }
                    adapterCategory.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Menu.this, "Error al recuperar información: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getShortcutsFromServer(Function_NetworkRequests networkRequests) {
        mShortcutsList.clear();
        networkRequests.getShortcuts(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayShortcuts = new JSONArray(response);

                    for (int i = 0; i < jsonArrayShortcuts.length(); i++) {
                        JSONObject jsonObject = jsonArrayShortcuts.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String imageUrl = jsonObject.getString("imgUrl");
                        String activity = jsonObject.getString("screen_name");

                        mShortcutsList.add(new Model_Shortcuts(id, title, imageUrl, activity));
                    }
                    adapterShortcuts.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Menu.this, "Error al recuperar información: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        );
    }


    public void change_Whatsapp(View view){
        Intent intent = new Intent(this, whatsapp_test.class);
        startActivity(intent);
    }
    public void change_ServicesCars(View view){
        Intent intent = new Intent(this, Act_ServicesCars.class);
        startActivity(intent);
    }
    public void change_MainMenu(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);

    }
    public void change_ServiceDetails1(View view){
        Intent intent = new Intent(this, Act_DescriptionCars.class);
        startActivity(intent);

    }
    public void change_ServiceDetails2(View view){
        Intent intent = new Intent(this, Act_description_parts.class);
        startActivity(intent);

    }
    public void change_ServiceParameters(View view){
        Intent intent = new Intent(this, Act_CarParameters.class);
        startActivity(intent);

    }
    public void change_ServiceDetails3(View view){
        Intent intent = new Intent(this, Act_OrderDetail.class);
        startActivity(intent);
    }
    public void change_ObjectParameters(View view){
        Intent intent = new Intent(this, Act_ObjectParameters.class);
        startActivity(intent);
    }
}