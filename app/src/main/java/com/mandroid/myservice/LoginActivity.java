package com.mandroid.myservice;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseUser;


public class LoginActivity extends ActionBarActivity {

    protected EditText mUserName;
    protected EditText mPassword;
    protected Button mLoginBtn;
    protected Button mGuestLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            System.exit(0);
        }

        //initalize
        mUserName = (EditText)findViewById(R.id.usrLoginTxt);
        mPassword = (EditText)findViewById(R.id.passLoginTxt);
        mLoginBtn = (Button)findViewById(R.id.loginBtn);
        mGuestLogin = (Button)findViewById(R.id.guestBtn);




        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Giriş edilir...");
                mDialog.setCancelable(false);
                mDialog.show();
                ParseUser.logInInBackground(mUserName.getText().toString().trim(), mPassword.getText().toString().trim(), new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, com.parse.ParseException e) {
                        if (e==null){
                            mDialog.dismiss();
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            Toast.makeText(LoginActivity.this,"Xoş gəldiniz",Toast.LENGTH_LONG).show();

                        }
                        else {
                            mDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage(e.getMessage());
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
                    }
                });
            }
        });

        mGuestLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, OfflineMainActivity.class);
                i.putExtra("username","Qonaq");
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    public void OpenRegActivity(View view){
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
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
