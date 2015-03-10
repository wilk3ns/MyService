package com.mandroid.myservice;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


public class WaitForCall extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckStatus();
        setContentView(R.layout.activity_wait_for_call);

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
}
