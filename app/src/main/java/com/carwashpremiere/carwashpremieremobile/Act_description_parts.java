package com.carwashpremiere.carwashpremieremobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class Act_description_parts extends AppCompatActivity {
    ImageButton imgBtn_Ex;
    TextView txt_ServiceDescription;
    TextView txt_ServiceParts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_act_description_parts);
        txt_ServiceDescription = findViewById(R.id.txt_ServiceDescription);
        txt_ServiceParts = findViewById(R.id.txt_ServiceParts);

    }
    public void setLongText(View v) {

        txt_ServiceDescription.setText(R.string.large_text);
        txt_ServiceParts.setText(R.string.large_text);
    }
}
