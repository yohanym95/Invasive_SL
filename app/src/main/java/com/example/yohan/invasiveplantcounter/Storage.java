package com.example.yohan.invasiveplantcounter;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class Storage extends AppCompatActivity implements ListFrag.getItem {
    TextView tvplant, tvlocation, tvlatitude, tvlongitude, tvdistribution, tvdensity, tvRemark;
    PlantDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        tvplant = findViewById(R.id.tvSpecies);
        tvlocation = findViewById(R.id.tvLocation);
        tvlatitude = findViewById(R.id.tvLatitude);
        tvlongitude = findViewById(R.id.tvLongitude);
        tvdistribution = findViewById(R.id.tvDistributionCode);
        tvdensity = findViewById(R.id.tvDensityCode);
        tvRemark = findViewById(R.id.tvRemark);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Storage");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);


        FragmentManager manager = this.getSupportFragmentManager();
        manager.beginTransaction()
                .show(manager.findFragmentById(R.id.ListFragment))
                .hide(manager.findFragmentById(R.id.ViewFragment))
                .commit();


    }

    @Override
    public void ItemSelected(int index) {
        db = new PlantDB(this);
        db.open();

        String plantname = db.getPlantName(index + 1);
        String location = db.getLocation(index + 1);
        String latitude = db.getLatitude(index + 1);
        String longitude = db.getLongitude(index + 1);
        String distribution = db.getDistribution(index + 1);
        String density = db.getDensity(index + 1);
        String remark = db.getRemark(index + 1);

        tvlocation.setText(location);
        tvplant.setText(plantname);
        tvlongitude.setText(longitude);
        tvlatitude.setText(latitude);
        tvdistribution.setText(distribution);
        tvdensity.setText(density);
        tvRemark.setText(remark);

        FragmentManager manager = this.getSupportFragmentManager();


        manager.beginTransaction()
                .show(manager.findFragmentById(R.id.ViewFragment))
                .hide(manager.findFragmentById(R.id.ListFragment))
                .addToBackStack(null)
                .commit();
    }

    private void updateUI() {
        finish();
        Toast.makeText(Storage.this, "You are logout!", Toast.LENGTH_LONG).show();
        Intent i = new Intent(Storage.this, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homepage:
                finish();
                Intent i = new Intent(Storage.this, HomePage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }

        return super.onOptionsItemSelected(item);

    }

}
