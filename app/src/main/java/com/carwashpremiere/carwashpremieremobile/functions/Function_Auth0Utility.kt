package com.carwashpremiere.carwashpremieremobile.functions

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.carwashpremiere.carwashpremieremobile.R
import com.carwashpremiere.carwashpremieremobile.activities.Menu

class Function_Auth0Utility(val context: Context) {

    var accessToken: String? = null
    var sharedPreferences: SharedPreferences? = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private val account: Auth0 =  Auth0(
        "FYnBK2HKXZorCkO8kjQJHFsIbFzOr9S1",
        "dev-ap8ggrxa3835inf7.us.auth0.com"
    )
    val domain = "dev-ap8ggrxa3835inf7.us.auth0.com"




    fun loginWithBrowser() {

        // Setup the WebAuthProvider, using the custom scheme and scope.

        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email read:current_user update:current_user_metadata")
            .withAudience("https://${domain}/api/v2/")
            // Launch the authentication passing the callback where the results will be received
            .start(context, object : Callback<Credentials, AuthenticationException> {
                // Called when there is an authentication failure
                override fun onFailure(exception: AuthenticationException) {
                    // Something went wrong!
                    Toast.makeText(
                        context,
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

                    sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences!!.edit()
                    editor.putString("access_token", accessToken)

                    editor.apply()
                    showUserProfile(accessToken!!)


                    Toast.makeText(
                        context,
                        "Authentication succeeded",
                        Toast.LENGTH_LONG
                    ).show()

                    val intent = Intent(context, Menu::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)

                }
            })
    }

    fun logout() {

        val editor = sharedPreferences!!.edit()
        editor.remove("access_token")
        editor.remove("email")
        editor.remove("name")
        editor.remove("picture")
        editor.apply()
        WebAuthProvider.logout(account)
            .withScheme("demo")

            .start(context, object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(payload: Void?) {
                    // The user has been logged out!
                    val intent = Intent(context, Menu::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }

                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
                }
            })
    }
    fun showUserProfile(accessToken: String) {
        var client = AuthenticationAPIClient(account)

        // With the access token, call `userInfo` and get the profile from Auth0.
        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    // Something went wrong!
                }

                override fun onSuccess(profile: UserProfile) {
                    // We have the user's profile!
                    val email = profile.email
                    val name = profile.name
                    val image = profile.pictureURL

                    sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences!!.edit()
                    editor.putString("email", email)
                    editor.putString("name", name)
                    editor.putString("picture", image)
                    editor.apply()

                }
            })


    }


}