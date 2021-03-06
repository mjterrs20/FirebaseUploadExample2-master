package com.example.asus.firebaseuploadexample.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.firebaseuploadexample.MainActivity;
import com.example.asus.firebaseuploadexample.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance ();
        if (firebaseAuth.getCurrentUser () != null) {
            finish();
            startActivity ( new Intent ( getApplicationContext (), MainActivity.class ));
        }
        progressDialog = new ProgressDialog ( this );

        buttonRegister = (Button) findViewById ( R.id.buttonRegister );

        editTextPassword = (EditText) findViewById ( R.id.editTextPassword );
        editTextEmail = (EditText) findViewById ( R.id.editTextEmail );

        textViewSignin = (TextView) findViewById ( R.id.textViewSignin );

        buttonRegister.setOnClickListener (this);
        textViewSignin.setOnClickListener (this);
    }

    private void registerUser(){
        String email = editTextEmail.getText ().toString ().trim ();
        String password = editTextPassword.getText ().toString ().trim ();

        if( TextUtils.isEmpty ( email )){
            // email is empty
            Toast.makeText ( this,"Please enter email",Toast.LENGTH_SHORT).show ();
            return;
        }
        if(TextUtils.isEmpty ( password )){
            // password is empty
            Toast.makeText ( this,"Please enter password",Toast.LENGTH_SHORT).show ();
            return;
        }

        progressDialog.setMessage ( "Register User" );
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword ( email,password )
                .addOnCompleteListener ( this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful ()){
                            finish();
                            startActivity ( new Intent ( getApplicationContext (), MainActivity.class ));
                        }else{
                            Toast.makeText ( Register.this,"gagal", Toast.LENGTH_SHORT ).show ();
                        }
                    }
                } );
    }
    @Override
    public void onClick(View v) {
        if (v == buttonRegister){
            registerUser ();
        }
        if (v == textViewSignin){
            startActivity ( new Intent ( this, Login.class ) );
        }
    }
}
