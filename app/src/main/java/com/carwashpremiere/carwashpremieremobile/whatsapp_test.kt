package com.carwashpremiere.carwashpremieremobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class whatsapp_test : AppCompatActivity() {
    var txt_Message: EditText? = null
    var txtPhone: EditText? = null
    var btn_Intent: Button? = null
    var btn_Url: Button? = null
    var chBox_opt1: RadioButton? = null
    var chBox_opt2: RadioButton? = null
    var chBox_opt3: RadioButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whatsapp_test)
        initUI()
    }

    /**
     * The initUI function initializes the UI elements of the app.
     * It sets up all of the buttons and text fields, as well as their listeners.
     *
     *
     *
     * @return A void
     *
     * @docauthor Trelent
     */
    private fun initUI() {
        //txtfields
        txtPhone = findViewById(R.id.txt_phone)
        txt_Message = findViewById(R.id.txt_Message)
        chBox_opt1 = findViewById(R.id.cBox_Opt1)
        chBox_opt2 = findViewById(R.id.cBox_Opt2)
        chBox_opt3 = findViewById(R.id.cBox_Opt3)


        //buttons
        btn_Intent = findViewById(R.id.btn_Intent)
        btn_Url = findViewById(R.id.btn_Url)
    }

    fun checkBox(): String {
        return if (chBox_opt1!!.isChecked) "Hi " else if (chBox_opt2!!.isChecked) "Hello " else "Greetings "
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
    fun onClick(v: View) {
        val id = v.id
        if (id == R.id.btn_Intent) {
            val number = txtPhone!!.text.toString()
            val message = txt_Message!!.text.toString()
            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.putExtra("jid", "whatsapp:$number@s.whatsapp.net")
            sendIntent.putExtra(Intent.EXTRA_TEXT, checkBox() + message)
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.setPackage("com.whatsapp")
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        } else if (id == R.id.btn_Url) {
            val number = txtPhone!!.text.toString()
            val message = txt_Message!!.text.toString()
            val url =
                "https://api.whatsapp.com/send?phone=" + number + "&text=" + (checkBox() + message)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}