package com.example.yohan.invasiveplantcounter;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Login extends AppCompatActivity  implements View.OnClickListener{
    Button btnLogin;
    EditText etEmail,etPassword;
    TextView tvSignUp;
    ProgressBar progressBar1;

    private FirebaseAuth mAuth;
    private Button btnFBLogin;
    private CallbackManager mCallbackManger;
    private static final String TAG ="FaceLog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        progressBar1 = findViewById(R.id.ProgressBar);


        mAuth = FirebaseAuth.getInstance();
        mCallbackManger = CallbackManager.Factory.create();
      //  facebook.authorize(this, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH, this);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Invasive SL");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        /*try{
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("KeyHash",Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT));

            }

        }catch (PackageManager.NameNotFoundException e){

        }catch (NoSuchAlgorithmException e){

        }*/


        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.tvSignUp).setOnClickListener(this);
        findViewById(R.id.btnFBLogin).setOnClickListener(this);




    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if( user != null){
            updateUI(user);
        }
    }

    private void updateUI(FirebaseUser currentUser) {

        Toast.makeText(Login.this,"You are Logged in", Toast.LENGTH_LONG).show();
        Intent i = new Intent(Login.this,HomePage.class);
        startActivity(i);
        finish();
    }

    private void userLogin(){
        String Email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(Email.isEmpty()){
            etEmail.setError("Email is Required");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            etEmail.setError("Please enter valid email");
            etEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            etPassword.setError("Minimum length of password shoul be 6");
            etPassword.requestFocus();
            return;

        }

        progressBar1.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar1.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    finish();
                    Intent intent = new Intent(Login.this,HomePage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManger.onActivityResult(requestCode, resultCode, data);
    }


    //facebook login
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        //sign with credential which create from firebase auth
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar1.setVisibility(View.GONE);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                           // btnFBLogin.setEnabled(true);
                            updateUI(user);
                        } else {
                            progressBar1.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // btnFBLogin.setEnabled(true);
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    //button
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                userLogin();
                break;
            case R.id.tvSignUp:
                finish();
                startActivity(new Intent(this,signup.class));
                break;
            case R.id.btnFBLogin:
              //R  btnFBLogin.setEnabled(true);
                progressBar1.setVisibility(View.VISIBLE);
                LoginManager.getInstance().logInWithReadPermissions(Login.this,Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManger, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);

                        // ...
                    }
                });
                break;

        }

    }
}
