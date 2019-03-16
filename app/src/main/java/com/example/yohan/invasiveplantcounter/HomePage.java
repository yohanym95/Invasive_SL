package com.example.yohan.invasiveplantcounter;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {


    TextView data,map,storgae,help;
    ImageView data1,map1,storgae1,help1;

    private static final String TAG = "HomePage";
    private static final int ERROR_DIALOG_REQUEST =9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        data = findViewById(R.id.DataCollection);
        map = findViewById(R.id.map);
        storgae = findViewById(R.id.storage);
        help = findViewById(R.id.InvasiveHelp);

        data1 = findViewById(R.id.imageView4);
        map1 = findViewById(R.id.imageView5);
        storgae1 = findViewById(R.id.imageView6);
        help1 = findViewById(R.id.imageView7);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Invasive SL");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        if(isServiceOk()){
            init();
        }

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // finish();
                Intent i = new Intent(HomePage.this,MainActivity.class);
              //  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });



        storgae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  finish();
                Intent i = new Intent(HomePage.this,Storage.class);
              //  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // finish();
                Intent i = new Intent(HomePage.this,ImageSlider.class);
                //   i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // finish();
                Intent i = new Intent(HomePage.this,MainActivity.class);
              //  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });



        storgae1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  finish();
                Intent i = new Intent(HomePage.this,Storage.class);
             //   i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  finish();
                Intent i = new Intent(HomePage.this,ImageSlider.class);
             //   i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        
        


    }


    private void init(){
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  finish();
                Intent i = new Intent(HomePage.this,MapsActivity.class);
                // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        map1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
                Intent i = new Intent(HomePage.this,MapsActivity.class);
                //  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

    }

    public boolean isServiceOk(){
        Log.d(TAG,"isServicesOK: Checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(HomePage.this);

        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG,"isServiceOK: Google play service is working");
            return  true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
           Log.d(TAG,"isServicesOK: an Error Occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(HomePage.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();

        }else{
            Toast.makeText(HomePage.this,"You Can't make map Request",Toast.LENGTH_LONG).show();

        }

        return false;
    }
    private void updateUI() {

        Toast.makeText(HomePage.this, "you are logout!",Toast.LENGTH_LONG).show();
        Intent i = new Intent(HomePage.this,Login.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ((item.getItemId())){
            case R.id.Signout:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                updateUI();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
