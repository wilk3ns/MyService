package com.mandroid.myservice;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
    //protected EditText mUserEmail;
    protected EditText mUserPassword;
    protected EditText mRePass;
    protected EditText mMobileNumber;
    protected Button mSignUp;
    String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initalize

        mUserName = (EditText)findViewById(R.id.usrnameTxt);
        mSignUp = (Button)findViewById(R.id.signUp);
        //mUserEmail = (EditText)findViewById(R.id.emailTxt);
        mUserPassword = (EditText)findViewById(R.id.passTxt);
        mMobileNumber = (EditText)findViewById(R.id.numberTxt);
        mRePass = (EditText)findViewById(R.id.rePassTxt);

        mSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


// other fields can be set just like with ParseObject


                if (noEmptyFields() && canRegister())
                {
                    final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                    mDialog.setMessage("Qeydiyyat tamamlanır...");
                    mDialog.setCancelable(false);
                    mDialog.show();
                    Log.i(TAG, "Started");
                    ParseUser user = new ParseUser();
                    user.setUsername(mUserName.getText().toString().trim());
                    user.setPassword(mUserPassword.getText().toString().trim());
                    //user.setEmail(mUserEmail.getText().toString().trim());
                    user.put("phone", mMobileNumber.getText().toString());
                    Log.i(TAG, "starting parse background process");
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                mDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Qeydiyyat tamamlandı!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                mDialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Qeydiyyat alınmadı, bir daha sınayın");
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
                else if(noEmptyFields()==false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Heç bir xananı boş qoymayın");
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
                else if(canRegister()==false){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Bütün xanaların doğruluğunu yoxlayın");
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
                else {
                    Toast.makeText(RegisterActivity.this,"Xəta baş verdi",Toast.LENGTH_SHORT).show();
                }
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

    boolean noEmptyFields(){
        if(mUserName.getText().toString().trim().length() == 0 || mUserPassword.getText().toString().trim().length()==0
                || mRePass.toString().trim().length() ==0
                || mMobileNumber.getText().toString().trim().length()==0) {
            return false;
        }
        else {
            return true;
        }
    }
    boolean canRegister(){
        if (mUserPassword.getText().toString().equals(mRePass.getText().toString())) {
            return true;
        }
        else return false;
    }
}
