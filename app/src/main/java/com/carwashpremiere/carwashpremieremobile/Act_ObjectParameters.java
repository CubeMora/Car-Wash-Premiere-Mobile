package com.carwashpremiere.carwashpremieremobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Act_ObjectParameters extends AppCompatActivity {
    RecyclerView rList_ExtraServicesObject, rList_DetailsObject;
    TextView txt_ObjectTitle;
    CardView cardView_ExtraServicesObject, cardView_DetailsObject, cardView_TitleObjectDetails, cardView_TitleObjectExtraServices;
    EditText eTxt_ObjectSize, eTxt_ObjectForm, eTxt_ObjectMaterial;
    CheckBox chBox_ObjectSize, chBox_ObjectForm, chBox_ObjectMaterial;
    Button btn_Next;

    String tempObjectSize, tempObjectForm, tempObjectMaterial;


    Function_NetworkRequests networkRequests;

    Adapter_DetailsObject adapterDetailsObject;
    Adapter_ExtraServicesObject adapterExtraServiceObject;

    List<Model_DetailsObject> mDetailsObjectsList = new ArrayList<>();
    List<Model_ExtraServicesObjects> mExtraServicesObjectsList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_act_object_parameters);

        initUI();

        Intent intent = getIntent();
        txt_ObjectTitle.setText(intent.getStringExtra("objectTitle"));

        cardView_ExtraServicesObject.setVisibility(View.GONE);
        cardView_TitleObjectExtraServices.setVisibility(View.GONE);


        networkRequests = new Function_NetworkRequests(this);

        adapterExtraServiceObject = new Adapter_ExtraServicesObject(this, mExtraServicesObjectsList);
        LinearLayoutManager linearLayoutManagerExtra = new LinearLayoutManager(this);
        linearLayoutManagerExtra.setOrientation(LinearLayoutManager.VERTICAL);

        rList_ExtraServicesObject.setLayoutManager(linearLayoutManagerExtra);
        rList_ExtraServicesObject.setAdapter(adapterExtraServiceObject);
        getExtraServicesObjectsFromServer(networkRequests);

        adapterDetailsObject = new Adapter_DetailsObject(this, mDetailsObjectsList);
        LinearLayoutManager linearLayoutManagerDetails = new LinearLayoutManager(this);
        linearLayoutManagerDetails.setOrientation(LinearLayoutManager.VERTICAL);

        rList_DetailsObject.setLayoutManager(linearLayoutManagerDetails);
        rList_DetailsObject.setAdapter(adapterDetailsObject);
        getDetailsObjectsFromServer(networkRequests);

        btn_Next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("objectTitle", txt_ObjectTitle.getText().toString());
                bundle.putString("objectSize", eTxt_ObjectSize.getText().toString());
                bundle.putString("objectForm", eTxt_ObjectForm.getText().toString());
                bundle.putString("objectMaterial", eTxt_ObjectMaterial.getText().toString());
                bundle.putStringArrayList("extraServices", new ArrayList<>(adapterExtraServiceObject.getSelectedServices()));
                bundle.putStringArrayList("details", new ArrayList<>(adapterDetailsObject.getSelectedDetails()));
                bundle.putString("bundleType", "object");

                Intent intent = new Intent(Act_ObjectParameters.this, Act_OrderDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        chBox_ObjectSize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Deshabilitar EditText y establecer el texto predeterminado "N/A"
                    eTxt_ObjectSize.setEnabled(false);
                    // Guardar el valor actual del EditText en la variable auxiliar
                    tempObjectSize = eTxt_ObjectSize.getText().toString();
                    eTxt_ObjectSize.setText("N/A");

                } else {


                    // Habilitar EditText y mostrar el valor guardado
                    eTxt_ObjectSize.setEnabled(true);
                    eTxt_ObjectSize.setText(tempObjectSize);
                }
            }
        });

        chBox_ObjectForm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    eTxt_ObjectForm.setEnabled(false);

                    tempObjectForm = eTxt_ObjectForm.getText().toString();
                    eTxt_ObjectForm.setText("N/A");
                } else {


                    eTxt_ObjectForm.setEnabled(true);
                    eTxt_ObjectForm.setText(tempObjectForm);
                }
            }
        });

        chBox_ObjectMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    eTxt_ObjectMaterial.setEnabled(false);
                    tempObjectMaterial = eTxt_ObjectMaterial.getText().toString();
                    eTxt_ObjectMaterial.setText("N/A");
                } else {

                    eTxt_ObjectMaterial.setEnabled(true);
                    eTxt_ObjectMaterial.setText(tempObjectMaterial);
                }
            }
        });





    }

    void initUI() {
        rList_DetailsObject = findViewById(R.id.rList_DetailsObject);
        rList_ExtraServicesObject = findViewById(R.id.rList_ExtraServicesObject);
        txt_ObjectTitle = findViewById(R.id.txt_HeaderObjectParameters);
        cardView_DetailsObject = findViewById(R.id.card_DetailsObject);
        cardView_ExtraServicesObject = findViewById(R.id.card_ExtraServicesObject);
        cardView_TitleObjectDetails = findViewById(R.id.card_TitulosObjectDetails);
        cardView_TitleObjectExtraServices = findViewById(R.id.card_TitulosObjectService);
        eTxt_ObjectSize = findViewById(R.id.eTxt_ObjectSize);
        eTxt_ObjectForm = findViewById(R.id.eTxt_ObjectForm);
        eTxt_ObjectMaterial = findViewById(R.id.eTxt_ObjectMaterial);
        chBox_ObjectSize = findViewById(R.id.chBox_ObjectSize);
        chBox_ObjectForm = findViewById(R.id.chBox_ObjectForm);
        chBox_ObjectMaterial = findViewById(R.id.chBox_ObjectMaterial);
        btn_Next = findViewById(R.id.btn_NextObjectOrderDetail);


    }

    public void getExtraServicesObjectsFromServer(Function_NetworkRequests networkRequests) {
        mExtraServicesObjectsList.clear();
        networkRequests.getExtraServiceObject(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayCategory = new JSONArray(response);

                    for (int i = 0; i < jsonArrayCategory.length(); i++) {
                        JSONObject jsonObject = jsonArrayCategory.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String price = jsonObject.getString("price");

                        mExtraServicesObjectsList.add(new Model_ExtraServicesObjects(id, title, price));
                    }

                    adapterExtraServiceObject.notifyDataSetChanged();

                    if (mExtraServicesObjectsList.size() == 0) {
                        cardView_ExtraServicesObject.setVisibility(View.GONE);
                        cardView_TitleObjectExtraServices.setVisibility(View.GONE);
                    } else {
                        cardView_ExtraServicesObject.setVisibility(View.VISIBLE);
                        cardView_TitleObjectExtraServices.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Act_ObjectParameters.this, "Error al recuperar información", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getDetailsObjectsFromServer(Function_NetworkRequests networkRequests) {
        mDetailsObjectsList.clear();
        networkRequests.getDetailObject(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayCategory = new JSONArray(response);

                    for (int i = 0; i < jsonArrayCategory.length(); i++) {
                        JSONObject jsonObject = jsonArrayCategory.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");


                        mDetailsObjectsList.add(new Model_DetailsObject(id, title));
                    }

                    adapterDetailsObject.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Act_ObjectParameters.this, "Error al recuperar información", Toast.LENGTH_LONG).show();
            }
        });
    }
}