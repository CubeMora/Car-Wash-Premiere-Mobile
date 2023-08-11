package com.carwashpremiere.carwashpremieremobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class whatsapp_test extends AppCompatActivity {

    EditText txt_Message, txtPhone;
    Button btn_Intent, btn_Url;
    RadioButton chBox_opt1,chBox_opt2, chBox_opt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp_test);

        initUI();
    }

    /**
     * The initUI function initializes the UI elements of the app.
     * It sets up all of the buttons and text fields, as well as their listeners.

     *
     *
     * @return A void
     *
     * @docauthor Trelent
     */
    private void initUI(){
        //txtfields
        txtPhone = findViewById(R.id.txt_phone);
        txt_Message = findViewById(R.id.txt_Message);
        chBox_opt1 = findViewById(R.id.cBox_Opt1);
        chBox_opt2 = findViewById(R.id.cBox_Opt2);
        chBox_opt3 = findViewById(R.id.cBox_Opt3);



        //buttons
        btn_Intent = findViewById(R.id.btn_Intent);
        btn_Url = findViewById(R.id.btn_Url);

    }
    public String checkBox(){
        String option = chBox_opt1.isChecked() ? "Hi " : chBox_opt2.isChecked() ? "Hello " : "Greetings ";
        return option;

    }

    /**
     * The onClick function is a function that is called when the user clicks on a button.
     * The function takes in an argument of type View, which represents the view that was clicked.
     * In this case, we are using two buttons: btn_Intent and btn_Url. When either of these buttons are clicked,
     * it will call this onClick function with its respective view as an argument (v). We then use v to get the id of the button that was pressed by calling v's getId() method. Then we check if it matches any one of our two buttons' ids (R.id.&lt;
     *
     * @param View v Get the id of the button that was clicked
     *
     * @return A boolean value
     *
     * @docauthor Trelent
     */
    public void onClick(View v){
        int id = v.getId();

        if(id == R.id.btn_Intent){
            String number = txtPhone.getText().toString();
            String message = txt_Message.getText().toString();

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.putExtra("jid", "whatsapp:" + number + "@s.whatsapp.net");
            sendIntent.putExtra(Intent.EXTRA_TEXT, checkBox() + message);
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setPackage("com.whatsapp");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);


        }else if(id == R.id.btn_Url){
            String number = txtPhone.getText().toString();
            String message = txt_Message.getText().toString();

            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + (checkBox() + message);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

}