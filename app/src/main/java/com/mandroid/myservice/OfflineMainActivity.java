package com.mandroid.myservice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;


public class OfflineMainActivity extends ActionBarActivity {


    String userName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_main);
        ParseUser currentUser = ParseUser.getCurrentUser();
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(currentUser.getUsername().toString()+" (Offline)");
        userName = currentUser.getUsername().toString();
    }

    public void OfflineCallDriverClicked(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
        builder.setMessage("In offline mode orders will charge you by SMS cost");
        builder.setTitle("Confirm");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendSMS("+994555801188", getResources().getString(R.string.driverSentence) + userName.toString());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void OfflineCallTaxiClicked(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
        builder.setMessage("In offline mode orders will charge you by SMS cost");
        builder.setTitle("Confirm");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendSMS("+994555801188", getResources().getString(R.string.taxiSentence) + userName.toString());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void OfflineCarWashClicked(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
        builder.setMessage("In offline mode orders will charge you by SMS cost");
        builder.setTitle("Confirm");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendSMS("+994555801188", getResources().getString(R.string.washSentence) + userName.toString());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void OfflineCarRent(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
        builder.setMessage("In offline mode orders will charge you by SMS cost");
        builder.setTitle("Confirm");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendSMS("+994555801188", getResources().getString(R.string.rentSentence) + userName.toString());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void sendSMS(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_offline_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                return true;
            case R.id.action_goOnline:
                if (isInternetOn()){
                    Intent i = new Intent(OfflineMainActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
                        builder.setMessage("No Internet connection");
                        builder.setTitle("Error");
                        builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                }
                break;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            //Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

}
