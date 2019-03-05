package com.example.yohan.invasiveplantcounter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Spinner plant,distribution,density,spLocation;
    Button save,one,two,three,four,five,six,seven,eight,nine,ten,low,medium,high,dense;
    ImageView location,photo,sync;
    private ArrayList<plant_item> plantList;
    private plantAdapter mAdapter;
    EditText remark;
    private LocationManager locationManager;
    private LocationListener locationListener;
    TextView distributionCode,densityCode,longitude,latitude;
    plant_item clicked;
    FloatingActionButton fab;

    public static final int Request_no = 1;
    // private String mImageFileLocation ="";
    File photoFile =null;
    private static final String Image_Directory_name ="InavasivePlantCounter";
    String mCurrentPhotoPath;
    private FirebaseAuth mAUTH;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plant = findViewById(R.id.Plant_Sp);
       /* distribution = findViewById(R.id.Distribution_Sp);
        density = findViewById(R.id.Density_Sp);*/
        save = findViewById(R.id.save);
        location = findViewById(R.id.location);
        longitude= findViewById(R.id.editText);
        latitude= findViewById(R.id.editText2);
        one = findViewById(R.id.one);
        two= findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        ten = findViewById(R.id.ten);
        distributionCode = findViewById(R.id.DistributionCode);
        densityCode = findViewById(R.id.DenistyCode);
        low = findViewById(R.id.den1);
        medium = findViewById(R.id.den2);
        high = findViewById(R.id.den3);
        dense= findViewById(R.id.den4);
        remark = findViewById(R.id.remark);
        spLocation = findViewById(R.id.spLocation);
        fab = findViewById(R.id.fab);
        photo = findViewById(R.id.ivphoto);

        ActionBar actionBar = getSupportActionBar();
       // actionBar.setIcon(R.drawable.landscape);
        actionBar.setTitle("Data Collection");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);



        mAUTH = FirebaseAuth.getInstance();

        FirebaseUser user = mAUTH.getCurrentUser();
        String id = user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference(""+id);
        mDatabase.keepSynced(true);


        initList();


//custom spinner
        mAdapter= new plantAdapter(this,plantList);
        plant.setAdapter(mAdapter);

        plant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clicked = (plant_item) parent.getItemAtPosition(position);
                //String clikedPlant = clicked.getPlant_Name();
              //  Toast.makeText(MainActivity.this, clikedPlant+" selected",Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
      //  String name = plant.getSelectedItem().toString();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    captureImage();
                }else{
                    captureImage2();
                }

            }
        });

        //distribution code
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                distributionCode.setText("1");

            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                distributionCode.setText("2");
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distributionCode.setText("3");
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distributionCode.setText("4");

            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distributionCode.setText("5");

            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distributionCode.setText("6");

            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distributionCode.setText("7");

            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distributionCode.setText("8");

            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distributionCode.setText("9");

            }
        });

        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distributionCode.setText("10");

            }
        });

        String distributioncode = distributionCode.getText().toString();

        //densitycode

        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                densityCode.setText("LOW");
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                densityCode.setText("MEDIUM");

            }
        });

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                densityCode.setText("HIGH");

            }
        });

        dense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                densityCode.setText("DENSE");

            }
        });

        String densitycode = densityCode.getText().toString();


        //Remark

        //String Remark =  remark.getText().toString();

        //gpslocaton
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

             //   cordiantes.setText("\n"+location.getLatitude()+" "+location.getLongitude());

                longitude.setText(""+location.getLongitude());
                latitude.setText(""+location.getLatitude());
            }
             @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
            @Override
            public void onProviderEnabled(String provider) {

            }
            @Override
            public void onProviderDisabled(String provider) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET

                }, 10);
            }
        }else{
            configureButton();
        }

    }
    private void updateUI() {
        Toast.makeText(MainActivity.this,"you are logout!",Toast.LENGTH_LONG).show();
        Intent i = new Intent(MainActivity.this,Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main1,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.homepage:
                finish();
                Intent i =new Intent(MainActivity.this,HomePage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }

        return super.onOptionsItemSelected(item);
    }



    /*   @Override
   protected void onStart() {
        super.onStart();
        if(mAUTH.getCurrentUser() != null){
            FirebaseUser user = mAUTH.getCurrentUser();
           // String id = user.getUid();
           // mDatabase = FirebaseDatabase.getInstance().getReference(id);
        }else{
            Intent i = new Intent(MainActivity.this,Login.class);
            startActivity(i);
        }
    } */

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode ){
            case 10 :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;

            case 0 :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    captureImage();
                }


        }

    }


    private void configureButton() {
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.requestLocationUpdates("gps", 2000, 0, locationListener);

            }
        });
    }





    //custom spinner
    private void initList() {
        plantList = new ArrayList<>();

        plantList.add(new plant_item("Ageratina Riparia",R.mipmap.aa_a));
        plantList.add(new plant_item("Aristea Ecklonii",R.mipmap.aa_a1));
        plantList.add(new plant_item("Ulex Europaeus",R.mipmap.uu_u));

    }

    //image
    private void captureImage2(){
        try{
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoFile = createImageFile4();
            if(photoFile != null){
                displayMessage(getBaseContext(),photoFile.getAbsolutePath());
                Log.i("Mayank",photoFile.getAbsolutePath());
                Uri photoUri = Uri.fromFile(photoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(i,Request_no);
            }
        }catch (Exception e){
            displayMessage(getBaseContext(),"camera is not available."+e.toString());

        }
    }

    private  void captureImage(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},0);

        }else{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                //create file where the photo should go
                try{
                    photoFile = createImageFile();
                    displayMessage(getBaseContext(),photoFile.getAbsolutePath());
                    Log.i("Mayank",photoFile.getAbsolutePath());

                    if(photoFile != null){
                        Uri photoUri = FileProvider.getUriForFile(this,"com.example.yohan.invasiveplantcounter.fileprovider",photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                        startActivityForResult(takePictureIntent,Request_no);

                    }

                }catch (Exception e){
                    //Error occured while creating the file
                    displayMessage(getBaseContext(),e.getMessage().toString());
                }

            }else{
                displayMessage(getBaseContext(),"nullll");
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Request_no){
            if(resultCode == RESULT_OK){
                // Bundle extras = data.getExtras();
                //Bitmap photoCapturemap = (Bitmap) extras.get("data");
                //photo.setImageBitmap(photoCapturemap);

                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                photo.setImageBitmap(bitmap);


            }
        }else{
            displayMessage(getBaseContext(),"Request cancelled or something went wrong.");
        }
    }

    private File createImageFile4(){

        //External sdcard
        //  File mediaStorgaeDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),Image_Directory_name);
        File mediaStorgaeDir = new File(Environment.getExternalStorageDirectory()+"/Invasive Plant");
        //CREATE STORAGE DIRECTORY IF IT DOES NOT EXITS
        if(!mediaStorgaeDir.exists()){
            if(!mediaStorgaeDir.mkdir()){
                displayMessage(getBaseContext(),"Unable to create directory.");
                return null;
            }
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(new Date());
        File mediaFile = new File(mediaStorgaeDir.getPath() + File.separator+"IMG_"+timestamp+".jpg");

        return mediaFile;

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/Invasive Plant");

        if(!storageDir.exists()) {
            if(storageDir.mkdir()); //directory is created;
        }

        File image = File.createTempFile(

                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir       /* directory */

        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void displayMessage(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }


  //  @RequiresApi(api = Build.VERSION_CODES.O)
    public void addData(View v){
       // longitude.setHint("");
        String plantName = clicked.getPlant_Name();
        String location = spLocation.getSelectedItem().toString();
        String longitude1 = longitude.getText().toString().trim();
        double longti1 = Double.parseDouble(longitude.getText().toString().trim());
        String latitude1 =  latitude.getText().toString().trim();
        double lati1 = Double.parseDouble(latitude.getText().toString().trim());
        String distribution1 = distributionCode.getText().toString().trim();
        String density = densityCode.getText().toString().trim();
        String remark1 =  remark.getText().toString();

      //  LocalDateTime t = null;
       // String currentDateTimeString = DateFormat.getD
       // String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        String  datetime = DateFormat.getDateTimeInstance()
               .format(new Date());
     // String datetime = "02.02.2019";
        try {
            if(latitude1.length() != 0 && longitude1.length() != 0 && distribution1.length() != 0 && density.length() !=0){
                latitude.setError(null);
                longitude.setError(null);
                distributionCode.setError(null);
                densityCode.setError(null);
                PlantDB db = new PlantDB(this);
                db.open();
                db.addData(plantName,location,latitude1,longitude1,datetime,distribution1,density,remark1);
                db.close();

               /* HashMap<String,String> dataMap = new HashMap<>();
                dataMap.put("Plant Name",plantName);
                dataMap.put("Location", location);
                dataMap.put("Longitude",longitude1);
                dataMap.put("Latitude",latitude1);
                dataMap.put("Distribution Code",distribution1);
                dataMap.put("Density Code",density);
                dataMap.put("Remark",remark1); */

                Datadetails datadetails = new Datadetails(plantName,location,lati1,longti1,distribution1,density,remark1,datetime);


                mDatabase.push().setValue(datadetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity.this,"Successfully saved In Cloud!",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }

                    }
                });

                Toast.makeText(MainActivity.this,"Successfully saved!",Toast.LENGTH_SHORT).show();

                longitude.setText("");
                latitude.setText("");
                distributionCode.setText("");
                densityCode.setText("");
                remark.setText("");

            }else {
                if(latitude1.length() == 0){
                    latitude.setError("Required");
                    latitude.requestFocus();
                    return;
                }else{
                    latitude.setError(null);
                   // latitude.requestFocus();
                }

                if(longitude1.length() ==  0){
                    longitude.setError("Required");
                    longitude.requestFocus();
                    return;
                }else {
                    longitude.setError(null);
                }

                if(distribution1.length() == 0){
                    distributionCode.setError("Click the code");
                    distributionCode.requestFocus();
                    return;
                }else{
                    distributionCode.setError(null);
                }

                if(density.length() == 0){
                    densityCode.setError("Click the Code");
                    densityCode.requestFocus();
                    return;
                }else {
                    densityCode.setError(null);
                }
            }

        }catch (Exception e){
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }


}
