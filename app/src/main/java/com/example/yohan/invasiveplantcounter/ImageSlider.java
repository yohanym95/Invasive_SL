package com.example.yohan.invasiveplantcounter;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ImageSlider extends AppCompatActivity implements plantlistfrag.getItem {

    ViewPager viewPager;
    TextView tvplantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        viewPager = findViewById(R.id.viewPager1);
        tvplantName = findViewById(R.id.tvplantname);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Invasive Help");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        FragmentManager manager = this.getSupportFragmentManager();
        manager.beginTransaction()
                .show(manager.findFragmentById(R.id.fragmentplantlist))
                .hide(manager.findFragmentById(R.id.fragmentplantview))
                .commit();

    }

    @Override
    public void ItemSelected(int index) {

        if(index == 0){
            ImageAdapter3 imageAdapter = new ImageAdapter3(this);
            viewPager.setAdapter(imageAdapter);
            tvplantName.setText("Ulex Europaeus");

        }else if(index == 1){
            ImageAdapter imageAdapter = new ImageAdapter(this);
            viewPager.setAdapter(imageAdapter);
            tvplantName.setText("Aristea Ecklonii(Blue Stars)");
        }else if(index == 2){
            ImageAdapter2 imageAdapter = new ImageAdapter2(this);
            viewPager.setAdapter(imageAdapter);
            tvplantName.setText("Ageratina Riparia(Mist Flower)");
        }

        FragmentManager manager = this.getSupportFragmentManager();
        manager.beginTransaction()
                .show(manager.findFragmentById(R.id.fragmentplantview))
                .hide(manager.findFragmentById(R.id.fragmentplantlist))
                .addToBackStack(null)
                .commit();

    }

    private void updateUI() {
        finish();
        Toast.makeText(ImageSlider.this, "You are logout!", Toast.LENGTH_LONG).show();
        Intent i = new Intent(ImageSlider.this, Login.class);
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
                Intent i = new Intent(ImageSlider.this, HomePage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }

        return super.onOptionsItemSelected(item);

    }
}
