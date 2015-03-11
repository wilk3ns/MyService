package com.mandroid.myservice;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneState extends BroadcastReceiver{

    String IS_CALLED_SERVICE = "Service";


    @Override
    public void onReceive(Context context, Intent intent) {

        TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);

        switch (tm.getCallState()) {

            case TelephonyManager.CALL_STATE_RINGING:
                WaitForCall call = new WaitForCall();
                String phoneNr= intent.getStringExtra("incoming_number");
                SharedPreferences.Editor editor = context.getSharedPreferences(IS_CALLED_SERVICE, context.MODE_PRIVATE).edit();
                editor.putBoolean("iscalled", false);
                editor.commit();
                break;
        }
    }
}
