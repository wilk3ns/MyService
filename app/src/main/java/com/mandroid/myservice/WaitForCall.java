package com.mandroid.myservice;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class WaitForCall extends Activity {

    Button makeCallBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckStatus();
        setContentView(R.layout.activity_wait_for_call);
        makeCallBtn = (Button)findViewById(R.id.emergencyBtn);

        makeCallBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                makeCall();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckStatus();
    }

    public void CheckStatus(){
        SharedPreferences prefs = getSharedPreferences("Service", MODE_PRIVATE);
        boolean isCalled = prefs.getBoolean("iscalled",false);
        if (isCalled==false){
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, getResources().getString(R.string.callRecievedText).toString(),Toast.LENGTH_SHORT).show();
            finish();
        }
    }



    protected void makeCall() {
        Log.i("Make call", "");

        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:"+getResources().getString(R.string.serviceNumber)));

        try {
            startActivity(new Intent(WaitForCall.this,LoginActivity.class));
            SharedPreferences.Editor editor = getSharedPreferences("Service", MODE_PRIVATE).edit();
            editor.putBoolean("iscalled", false);
            editor.commit();
            startActivity(phoneIntent);
            finish();
            Log.i("Finished making a call...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(WaitForCall.this,
                    "Call failed, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}
