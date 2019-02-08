package com.example.yohan.invasiveplantcounter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private DatabaseReference mfirebase;
    private ChildEventListener mChildEventListner;
    Marker marker;
    List<Datadetails> detaillist;
    private final static int MAP_PADDING = 10;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Map");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        detaillist = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        String id = user.getUid();

        mfirebase = FirebaseDatabase.getInstance().getReference("" + id);
        mfirebase.push().setValue(marker);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            // String id = user.getUid();
            // mDatabase = FirebaseDatabase.getInstance().getReference(id);
        } else {
            Intent i = new Intent(MapsActivity.this, Login.class);
            startActivity(i);
        }
    }

    private void updateUI() {
        Toast.makeText(MapsActivity.this, "you are logout!", Toast.LENGTH_LONG).show();
        Intent i = new Intent(MapsActivity.this, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.homepage:
                finish();
                Intent i = new Intent(MapsActivity.this, HomePage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.Signout:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                updateUI();
                break;
            case R.id.mapSatalite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapNormal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapHybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap){
            mMap = googleMap;

            // Add a marker in Sydney and move the camer
            // googleMap.setOnMarkerClickListener(this);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mfirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Datadetails datadetails = data.getValue(Datadetails.class);
                        detaillist.add(datadetails);

                        for (int i = 0; i < detaillist.size(); i++) {
                            LatLng location = new LatLng(datadetails.latitude, datadetails.lonigtude);

                            if (mMap != null) {

                                mMap.addMarker(new MarkerOptions().position(location).title(datadetails.datetime));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                            }
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
