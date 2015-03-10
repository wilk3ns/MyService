package com.mandroid.myservice;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Chamran on 3/10/2015.
 */
public class ParsePushApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "P5nGU1g1pXm3eEGGIbZJhgE0AAGOAgh6rBJdNL2N", "gwn2qSbJvPL6bKJTULJYKgr74oWJv0ShdlXlGCcn");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }
}
