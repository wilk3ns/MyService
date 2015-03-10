package com.mandroid.myservice;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;


public class RegisterActivity extends ActionBarActivity {

    protected EditText mUserName;
    protected EditText mUserEmail;
    protected EditText mUserPassword;
    protected Button mSignUp;
    String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initalize

        mUserName = (EditText)findViewById(R.id.usrnameTxt);
        mSignUp = (Button)findViewById(R.id.signUp);
        mUserEmail = (EditText)findViewById(R.id.emailTxt);
        mUserPassword = (EditText)findViewById(R.id.passTxt);

        mSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Started");
                ParseUser user = new ParseUser();
                user.setUsername(mUserName.getText().toString().trim());
                user.setPassword(mUserPassword.getText().toString().trim());
                user.setEmail(mUserEmail.getText().toString().trim());

// other fields can be set just like with ParseObject
                user.put("phone", "650-253-0000");
                Log.i(TAG,"starting parse background process");
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null){
                            Toast.makeText(RegisterActivity.this,"Success!",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"Error!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
