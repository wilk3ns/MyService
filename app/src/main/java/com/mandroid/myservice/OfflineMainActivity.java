package com.mandroid.myservice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;


public class OfflineMainActivity extends ActionBarActivity {


    String userName;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        ParseUser currentUser = ParseUser.getCurrentUser();
        TextView textView = (TextView)findViewById(R.id.textView);
        if(getIntent().getStringExtra("username")== null){
            textView.setText(currentUser.getUsername().toString()+" (Offline)");
            userName = currentUser.getUsername().toString();
        }
        else{
            userName = getIntent().getStringExtra("username");
            textView.setText(userName);
        }
        setSupportActionBar(toolbar);
    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
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
        builder.setNegativeButton("Xeyir", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which ) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void makeCall(View view) {
        Log.i("Make call", "");

        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:" + getResources().getString(R.string.emergencyNumber)));

        try {
            startActivity(phoneIntent);
            finish();
            Log.i("Finished making a call...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(OfflineMainActivity.this,
                    "Zəng alınmadı, lütfən daha sonra yoxlayın.", Toast.LENGTH_SHORT).show();
        }
    }

    public void OfflineCallDriverClicked(View view){
        SharedPreferences prefs = getSharedPreferences("Service", MODE_PRIVATE);
        boolean isCalled = prefs.getBoolean("iscalled",false);

        if (isCalled == true) {
            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
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
        }
        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
            builder.setMessage("Offline vəya Qonaq rejimdə balansınızdan SMS tarifinə uyğun olaraq ödəniş həyata keçiriləcək");
            builder.setTitle("Təstiqləyin");
            builder.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendSMS(getResources().getString(R.string.serviceNumber), getResources().getString(R.string.driverSentence) + userName.toString());
                    dialog.dismiss();
                    SharedPreferences.Editor editor = getSharedPreferences("Service", MODE_PRIVATE).edit();
                    editor.putBoolean("iscalled", true);
                    editor.commit();
                    //startActivity(new Intent(OfflineMainActivity.this, WaitForCall.class));
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
    }

    public void OfflineCallTaxiClicked(View view){
        SharedPreferences prefs = getSharedPreferences("Service", MODE_PRIVATE);
        boolean isCalled = prefs.getBoolean("iscalled",false);
        if (isCalled == true) {
            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
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
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
            builder.setMessage("Offline vəya Qonaq rejimdə balansınızdan SMS tarifinə uyğun olaraq ödəniş həyata keçiriləcək");
            builder.setTitle("Təstiqləyin");
            builder.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendSMS(getResources().getString(R.string.serviceNumber), getResources().getString(R.string.taxiSentence) + userName.toString());
                    dialog.dismiss();
                    SharedPreferences.Editor editor = getSharedPreferences("Service", MODE_PRIVATE).edit();
                    editor.putBoolean("iscalled", true);
                    editor.commit();
                    //startActivity(new Intent(OfflineMainActivity.this, WaitForCall.class));
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
    }

    public void OfflineCarWashClicked(View view){
        SharedPreferences prefs = getSharedPreferences("Service", MODE_PRIVATE);
        boolean isCalled = prefs.getBoolean("iscalled",false);
        if (isCalled == true) {
            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
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
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
            builder.setMessage("Offline vəya Qonaq rejimdə balansınızdan SMS tarifinə uyğun olaraq ödəniş həyata keçiriləcək");
            builder.setTitle("Təstiqləyin");
            builder.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendSMS(getResources().getString(R.string.serviceNumber), getResources().getString(R.string.washSentence) + userName.toString());
                    dialog.dismiss();
                    SharedPreferences.Editor editor = getSharedPreferences("Service", MODE_PRIVATE).edit();
                    editor.putBoolean("iscalled", true);
                    editor.commit();
                    //startActivity(new Intent(OfflineMainActivity.this, WaitForCall.class));
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
    }

    public void OfflineCarRent(View view){
        SharedPreferences prefs = getSharedPreferences("Service", MODE_PRIVATE);
        boolean isCalled = prefs.getBoolean("iscalled",false);
        if (isCalled == true) {
            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
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
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
            builder.setMessage("Offline vəya Qonaq rejimdə balansınızdan SMS tarifinə uyğun olaraq ödəniş həyata keçiriləcək");
            builder.setTitle("Təstiqləyin");
            builder.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendSMS(getResources().getString(R.string.serviceNumber), getResources().getString(R.string.rentSentence) + userName.toString());
                    dialog.dismiss();
                    SharedPreferences.Editor editor = getSharedPreferences("Service", MODE_PRIVATE).edit();
                    editor.putBoolean("iscalled", true);
                    editor.commit();
                    //startActivity(new Intent(OfflineMainActivity.this, WaitForCall.class));
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
    }

    public void OfflineEvacuate(View view){
        SharedPreferences prefs = getSharedPreferences("Service", MODE_PRIVATE);
        boolean isCalled = prefs.getBoolean("iscalled",false);
        if (isCalled == true) {
            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
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
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
            builder.setMessage("Offline vəya Qonaq rejimdə balansınızdan SMS tarifinə uyğun olaraq ödəniş həyata keçiriləcək");
            builder.setTitle("Təstiqləyin");
            builder.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendSMS(getResources().getString(R.string.serviceNumber), getResources().getString(R.string.evacuateSentence) + userName.toString());
                    dialog.dismiss();
                    SharedPreferences.Editor editor = getSharedPreferences("Service", MODE_PRIVATE).edit();
                    editor.putBoolean("iscalled", true);
                    editor.commit();
                    //startActivity(new Intent(OfflineMainActivity.this, WaitForCall.class));
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
//            case R.id.action_settings:
//                return true;
            case R.id.action_goOnline:
                if (isInternetOn()){
                    Intent i = new Intent(OfflineMainActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMainActivity.this);
                        builder.setMessage("İnternetlə əlaqə yoxdur");
                        builder.setTitle("Səhv");
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
