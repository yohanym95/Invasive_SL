package com.example.yohan.invasiveplantcounter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signup extends AppCompatActivity  implements View.OnClickListener {
    Button btnSignUp;
    EditText etEmail1,etPassword1;
    TextView tvLogin;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etEmail1 = findViewById(R.id.etEmail1);
        etPassword1 = findViewById(R.id.etPassword1);
        progressBar = findViewById(R.id.ProgressBar1);

        findViewById(R.id.btnsignup).setOnClickListener(this);
        findViewById(R.id.tvlogin).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();



    }

    private void registerUser(){
        String name = etEmail1.getText().toString().trim();
        String password = etPassword1.getText().toString().trim();

        if(name.isEmpty()){
            etEmail1.setError("Email is Required");
            etEmail1.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(name).matches()){
            etEmail1.setError("Please Enter Valid Email");
            etEmail1.requestFocus();
            return;
        }

        if (password.isEmpty()){
            etPassword1.setError("Password is Required");
            etPassword1.requestFocus();
            return;
        }

        if(password.length() < 6){
            etPassword1.setError("Minimum length of password shoul be 6");
            etPassword1.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(name,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User Registered Successful",Toast.LENGTH_LONG).show();

                    finish();
                    Intent intent = new Intent(signup.this,HomePage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"You are already registered!",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(signup.this,Login.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(),"Some Error Occurred!",Toast.LENGTH_LONG).show();

                    }
                }

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnsignup :
                registerUser();
                break;
            case R.id.tvlogin:
                finish();
                startActivity(new Intent(this,Login.class));
                break;
        }

    }
}
