package com.mandroid.myservice;


import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Proqramdan çıxmağa əminsinizmi?");
        builder.setTitle("Təstiq");
        builder.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Xeyir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        currentUser = ParseUser.getCurrentUser();
        TextView textView = (TextView) findViewById(R.id.userNameTxt);
        textView.setText(currentUser.getUsername().toString() + " (Online)");
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void makeOnlineCall(View view) {
        Log.i("Make call", "");

        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:" + getResources().getString(R.string.emergencyNumber)));

        try {
            SharedPreferences.Editor editor = getSharedPreferences("Service", MODE_PRIVATE).edit();
            editor.putBoolean("iscalled", false);
            editor.commit();
            startActivity(phoneIntent);
            Log.i("Finished making a call...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "Zəng alınmadı, lütfən daha sonra yoxlayın.", Toast.LENGTH_SHORT).show();
        }
    }


    public void CallDriverClicked(View view) {
        if (isInternetOn()) {
            final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Sifariş qeydə alınır...");
            mDialog.setCancelable(false);
            mDialog.show();
            SharedPreferences prefs = getSharedPreferences("Service", MODE_PRIVATE);
            final boolean isCalled = prefs.getBoolean("iscalled", false);
            calls = new ParseObject("Calls");
            calls.put("User", currentUser.getUsername().toString());
            calls.put("ServiceType", "DriverCall");
            calls.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null && isCalled == false) {
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Sifariş qeydə alındı", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(MainActivity.this,WaitForCall.class));
                        SharedPreferences.Editor editor = getSharedPreferences(IS_CALLED_SERVICE, MODE_PRIVATE).edit();
                        editor.putBoolean("iscalled", true);
                        editor.commit();
                    } else if (isCalled == true) {
                        mDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Hal hazırda zəng gözləyirsiniz");
                        builder.setTitle("Gözləyin");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        mDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(e.getMessage());
                        builder.setTitle("Error");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("İnternetlə əlaqə yoxdur, offline rejim aktivləşdirilsin?");
            builder.setTitle("Səhv");
            builder.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(MainActivity.this, OfflineMainActivity.class));
                }
            });
            builder.setNegativeButton("Xeyir", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }



    public void CallTaxiClicked(View view){
        if(isInternetOn()) {
            final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Sifariş qeydə alınır...");
            mDialog.setCancelable(false);
            mDialog.show();
            SharedPreferences prefs = getSharedPreferences("Service", MODE_PRIVATE);
            final boolean isCalled = prefs.getBoolean("iscalled", false);
            calls = new ParseObject("Calls");
            calls.put("User", currentUser.getUsername().toString());
            calls.put("ServiceType", "TaxiCall");
            calls.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null && isCalled == false) {
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Sifariş qeydə alındı", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(MainActivity.this,WaitForCall.class));
                        SharedPreferences.Editor editor = getSharedPreferences(IS_CALLED_SERVICE, MODE_PRIVATE).edit();
                        editor.putBoolean("iscalled", true);
                        editor.commit();
                    } else if (isCalled == true) {
                        mDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Hal hazırda zəng gözləyirsiniz");
                        builder.setTitle("Gözləyin");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        mDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(e.getMessage());
                        builder.setTitle("Error");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("İnternetlə əlaqə yoxdur, offline rejim aktivləşdirilsin?");
            builder.setTitle("Səhv");
            builder.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(MainActivity.this, OfflineMainActivity.class));
                }
            });
            builder.setNegativeButton("Xeyir", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    public void CallCarWashClicked(View view){
        if(isInternetOn()) {
            final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Sifariş qeydə alınır...");
            mDialog.setCancelable(false);
            mDialog.show();
            SharedPreferences prefs = getSharedPreferences("Service", MODE_PRIVATE);
            final boolean isCalled = prefs.getBoolean("iscalled", false);
            calls = new ParseObject("Calls");
            calls.put("User", currentUser.getUsername().toString());
            calls.put("ServiceType", "CarWash");
            calls.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null && isCalled == false) {
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Sifariş qeydə alındı", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(MainActivity.this,WaitForCall.class));
                        SharedPreferences.Editor editor = getSharedPreferences(IS_CALLED_SERVICE, MODE_PRIVATE).edit();
                        editor.putBoolean("iscalled", true);
                        editor.commit();
                    } else if (isCalled == true) {
                        mDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Hal hazırda zəng gözləyirsiniz");
                        builder.setTitle("Gözləyin");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        mDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(e.getMessage());
                        builder.setTitle("Error");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("İnternetlə əlaqə yoxdur, offline rejim aktivləşdirilsin?");
            builder.setTitle("Səhv");
            builder.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(MainActivity.this, OfflineMainActivity.class));
                }
            });
            builder.setNegativeButton("Xeyir", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void CallCarRentClicked(View view){
        if(isInternetOn()) {
            final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Sifariş qeydə alınır...");
            mDialog.setCancelable(false);
            mDialog.show();
            SharedPreferences prefs = getSharedPreferences("Service", MODE_PRIVATE);
            final boolean isCalled = prefs.getBoolean("iscalled", false);
            calls = new ParseObject("Calls");
            calls.put("User", currentUser.getUsername().toString());
            calls.put("ServiceType", "RentACar");
            calls.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null && isCalled == false) {
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Sifariş qeydə alındı", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(MainActivity.this, WaitForCall.class));
                        SharedPreferences.Editor editor = getSharedPreferences(IS_CALLED_SERVICE, MODE_PRIVATE).edit();
                        editor.putBoolean("iscalled", true);
                        editor.commit();
                    } else if (isCalled == true) {
                        mDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Hal hazırda zəng gözləyirsiniz");
                        builder.setTitle("Gözləyin");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        mDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(e.getMessage());
                        builder.setTitle("Error");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("İnternetlə əlaqə yoxdur, offline rejim aktivləşdirilsin?");
            builder.setTitle("Səhv");
            builder.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(MainActivity.this, OfflineMainActivity.class));
                }
            });
            builder.setNegativeButton("Xeyir", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void CallEvacuationClicked(View view){
        if(isInternetOn()) {
            final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Sifariş qeydə alınır...");
            mDialog.setCancelable(false);
            mDialog.show();
            SharedPreferences prefs = getSharedPreferences("Service", MODE_PRIVATE);
            final boolean isCalled = prefs.getBoolean("iscalled", false);
            calls = new ParseObject("Calls");
            calls.put("User", currentUser.getUsername().toString());
            calls.put("ServiceType", "RentACar");
            calls.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null && isCalled == false) {
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Sifariş qeydə alındı", Toast.LENGTH_LONG).show();
                        //  startActivity(new Intent(MainActivity.this,WaitForCall.class));
                        SharedPreferences.Editor editor = getSharedPreferences(IS_CALLED_SERVICE, MODE_PRIVATE).edit();
                        editor.putBoolean("iscalled", true);
                        editor.commit();
                    } else if (isCalled == true) {
                        mDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Hal hazırda zəng gözləyirsiniz");
                        builder.setTitle("Gözləyin");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        mDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(e.getMessage());
                        builder.setTitle("Error");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("İnternetlə əlaqə yoxdur, offline rejim aktivləşdirilsin?");
            builder.setTitle("Səhv");
            builder.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(MainActivity.this, OfflineMainActivity.class));
                }
            });
            builder.setNegativeButton("Xeyir", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
//            case R.id.action_settings:
//                return true;

            case R.id.action_logout:
                ParseUser.logOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                SharedPreferences.Editor editor = getSharedPreferences(IS_CALLED_SERVICE, MODE_PRIVATE).edit();
                editor.putBoolean("iscalled", false);
                editor.commit();
                finish();
                break;
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
