package com.carwashpremiere.carwashpremieremobile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials

class Act_FirstScreen : AppCompatActivity() {

    var btn_login: Button? = null
    var btn_logout: Button? = null
    private lateinit var account: Auth0
    var accessToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_act_loginpage)


        InitUI()

        account = Auth0(
            "FYnBK2HKXZorCkO8kjQJHFsIbFzOr9S1",
            "dev-ap8ggrxa3835inf7.us.auth0.com"
        )


        btn_login!!.setOnClickListener {
            loginWithBrowser()
        }
        btn_logout!!.setOnClickListener {
            logout()
        }



    }

    fun InitUI(){
        btn_login = findViewById(R.id.btn_UserProfile)
        btn_logout = findViewById(R.id.btn_logout)
    }

    private fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope.

        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email read:current_user update:current_user_metadata")
            .withAudience("https://${getString(R.string.com_auth0_domain)}/api/v2/")
            // Launch the authentication passing the callback where the results will be received
            .start(this, object : Callback<Credentials, AuthenticationException> {
                // Called when there is an authentication failure
                override fun onFailure(exception: AuthenticationException) {
                    // Something went wrong!
                    Toast.makeText(
                        this@Act_FirstScreen,
                        "Authentication failed: " + exception.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                // Called when authentication completed successfully
                override fun onSuccess(credentials: Credentials) {
                    // Get the access token from the credentials object.
                    // This can be used to call APIs

                    accessToken = credentials.accessToken
                    //txt_test!!.text = "Access token: $accessToken"

                    val sharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("access_token", accessToken)
                    editor.apply()


                    Toast.makeText(
                        this@Act_FirstScreen,
                        "Authentication succeded: " + accessToken,

                        Toast.LENGTH_LONG
                    ).show()



                    var intent = Intent(this@Act_FirstScreen, Menu::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("AccessToken", accessToken)
                    startActivity(intent)

                }
            })
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("access_token")
        editor.apply()
        WebAuthProvider.logout(account)
            .withScheme("demo")

            .start(this, object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(payload: Void?) {
                    // The user has been logged out!
                    val intent = Intent(applicationContext, Act_FirstScreen::class.java)
                    startActivity(intent)
                }

                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
                }
            })
    }



}