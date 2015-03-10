package com.mandroid.myservice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;


import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;


public class StartupCheckerActivity extends Activity {

    SharedPreferences prefs;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("Service", MODE_PRIVATE);
        isInternetOn();

        // Determine whether the current user is an anonymous user

    }
    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            ifConnected();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

           //Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            ifNotConnected();
            return false;
        }
        return false;
    }

    public void ifConnected(){

        if (prefs.getBoolean("iscalled",false)==true){
            startActivity(new Intent(this,WaitForCall.class));
            finish();
            return;
        }

        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to LoginSignupActivity.class
            Intent intent = new Intent(StartupCheckerActivity.this,
                    LoginActivity.class);
            startActivity(intent);
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to Welcome.class
                Intent intent = new Intent(StartupCheckerActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(StartupCheckerActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        }
        finish();

    }
    public void ifNotConnected(){
        if (prefs.getBoolean("iscalled",false)==true){
            startActivity(new Intent(this,WaitForCall.class));
            finish();
            return;
        }
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to LoginSignupActivity.class
            Intent intent = new Intent(StartupCheckerActivity.this,
                    LoginActivity.class);
            startActivity(intent);
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to Welcome.class
                Intent intent = new Intent(StartupCheckerActivity.this, OfflineMainActivity.class);
                startActivity(intent);
            } else {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(StartupCheckerActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        }
        finish();

    }
}
