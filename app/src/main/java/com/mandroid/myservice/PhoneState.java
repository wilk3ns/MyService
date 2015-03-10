package com.mandroid.myservice;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by Chamran on 3/10/2015.
 */
public class PhoneState extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);

        switch (tm.getCallState()) {

            case TelephonyManager.CALL_STATE_RINGING:
                String phoneNr= intent.getStringExtra("incoming_number");
                Toast.makeText(context, phoneNr, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
