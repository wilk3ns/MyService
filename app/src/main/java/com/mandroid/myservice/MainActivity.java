package com.mandroid.myservice;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;




public class MainActivity extends ActionBarActivity {

    ParseUser currentUser;
    ParseObject calls;
    String IS_CALLED_SERVICE = "Service";
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setTitle("*5533");
        currentUser = ParseUser.getCurrentUser();
        TextView textView = (TextView)findViewById(R.id.userNameTxt);
        textView.setText(currentUser.getUsername().toString()+" (Online)");
        setSupportActionBar(toolbar);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void CallDriverClicked(View view){
        calls = new ParseObject("Calls");
        calls.put("User",currentUser.getUsername().toString());
        calls.put("ServiceType","DriverCall");
        calls.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Toast.makeText(MainActivity.this,"Driver Called",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,WaitForCall.class));
                    SharedPreferences.Editor editor = getSharedPreferences(IS_CALLED_SERVICE, MODE_PRIVATE).edit();
                    editor.putBoolean("iscalled", true);
                    editor.commit();
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(e.getMessage());
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
            }
        });
    }

    public void CallTaxiClicked(View view){
        calls = new ParseObject("Calls");
        calls.put("User",currentUser.getUsername().toString());
        calls.put("ServiceType","TaxiCall");
        calls.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Toast.makeText(MainActivity.this,"Taxi Called",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,WaitForCall.class));
                    startActivity(new Intent(MainActivity.this,WaitForCall.class));
                    SharedPreferences.Editor editor = getSharedPreferences(IS_CALLED_SERVICE, MODE_PRIVATE).edit();
                    editor.putBoolean("iscalled", true);
                    editor.commit();
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(e.getMessage());
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
            }
        });

    }

    public void CallCarWashClicked(View view){
        calls = new ParseObject("Calls");
        calls.put("User",currentUser.getUsername().toString());
        calls.put("ServiceType","CarWash");
        calls.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){

                    Toast.makeText(MainActivity.this,"Car Wash service called",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,WaitForCall.class));
                    startActivity(new Intent(MainActivity.this,WaitForCall.class));
                    SharedPreferences.Editor editor = getSharedPreferences(IS_CALLED_SERVICE, MODE_PRIVATE).edit();
                    editor.putBoolean("iscalled", true);
                    editor.commit();
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(e.getMessage());
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
            }
        });
    }

    public void CallCarRentClicked(View view){
        calls = new ParseObject("Calls");
        calls.put("User",currentUser.getUsername().toString());
        calls.put("ServiceType","RentACar");
        calls.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Toast.makeText(MainActivity.this,"RentServiceCalled",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,WaitForCall.class));
                    startActivity(new Intent(MainActivity.this,WaitForCall.class));
                    SharedPreferences.Editor editor = getSharedPreferences(IS_CALLED_SERVICE, MODE_PRIVATE).edit();
                    editor.putBoolean("iscalled", true);
                    editor.commit();
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(e.getMessage());
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
            }
        });
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

            case R.id.action_logout:
                ParseUser.logOut();
                finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
