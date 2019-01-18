package com.example.yohan.invasiveplantcounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlantDB {

    public static final String KEY_ID = "ID";
    public static final String KEY_PlantNAME = "Name";
    public static final String KEY_Location = "Location";
    public static final String KEY_Longitude = "Longitude";
    public static final String KEY_Latitude = "Latitude";
    public static final String KEY_Distribution = "DistributionCode";
    public static final String KEY_Density = "DensityCode";
    public static final String KEY_Remark = "Remark";
    public static final String Key_DateTime = "DateTime";

    private final String DATABASE_NAME = "PlantDB";
    private final String DATABASE_TABLE = "PlantDB_TABLE";
    private final int DATABASE_VERSION = 3;

    private DBHelper OurHelper;
    private final Context context;
    private SQLiteDatabase ourDatabase;

    public PlantDB(Context context){
        this.context = context;
    }

    public class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String SQLCode = "CREATE TABLE "+DATABASE_TABLE+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_PlantNAME+" TEXT NOT NULL, "+KEY_Location+" TEXT NOT NULL, "+KEY_Longitude+" TEXT NOT NULL, "+KEY_Latitude+" TEXT NOT NULL, "+KEY_Distribution+" TEXT NOT NULL, "+KEY_Density+" TEXT NOT NULL, "+Key_DateTime+" TEXT NOT NULL, "+KEY_Remark+" TEXT );";
            db.execSQL(SQLCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }

    public PlantDB open(){
        OurHelper = new DBHelper(context);
        ourDatabase = OurHelper.getWritableDatabase();

        return  this;

    }
    public void close(){
        OurHelper.close();
    }

    public long addData(String name, String Location, String latitude, String longitude, String datetime,String distribution, String density,String Remark){

        ContentValues cv  = new ContentValues();
        cv.put(KEY_PlantNAME,name);
        cv.put(KEY_Location,Location);
        cv.put(KEY_Latitude,latitude);
        cv.put(KEY_Longitude,longitude);
        cv.put(Key_DateTime,datetime);
        cv.put(KEY_Distribution,distribution);
        cv.put(KEY_Density,density);
        cv.put(KEY_Remark,Remark);

        return ourDatabase.insert(DATABASE_TABLE,null,cv);

    }

    public Cursor getName(){
        Cursor cursor = ourDatabase.rawQuery("SELECT "+Key_DateTime+ " FROM "+DATABASE_TABLE,null);

        return cursor;
    }

    public String getPlantName(int position){
        Cursor cursor = null;
        String name = "";
        String no = position+"";
        try{
            cursor= ourDatabase.rawQuery("SELECT "+KEY_PlantNAME+" FROM "+DATABASE_TABLE+" WHERE "+KEY_ID+"=?",new String[]{no});

            if(cursor.getCount()>0){
                cursor.moveToFirst();
                name = cursor.getString(cursor.getColumnIndex(KEY_PlantNAME));

            }

            return name;

        }finally {
            cursor.close();
        }
    }

    public String getLocation(int position){
        Cursor cursor = null;
        String Location = "";
        String no = position+"";
        try{
            cursor= ourDatabase.rawQuery("SELECT "+KEY_Location+" FROM "+DATABASE_TABLE+" WHERE "+KEY_ID+"=?",new String[]{no});

            if(cursor.getCount()>0){
                cursor.moveToFirst();
                Location = cursor.getString(cursor.getColumnIndex(KEY_Location));

            }

            return Location;

        }finally {
            cursor.close();
        }
    }

    public String getLongitude(int position){
        Cursor cursor = null;
        String longitude = "";
        String no = position+"";
        try{
            cursor= ourDatabase.rawQuery("SELECT "+KEY_Longitude+" FROM "+DATABASE_TABLE+" WHERE "+KEY_ID+"=?",new String[]{no});

            if(cursor.getCount()>0){
                cursor.moveToFirst();
                longitude = cursor.getString(cursor.getColumnIndex(KEY_Longitude));

            }

            return longitude;

        }finally {
            cursor.close();
        }
    }

    public String getLatitude(int position){
        Cursor cursor = null;
        String latitude = "";
        String no = position+"";
        try{
            cursor= ourDatabase.rawQuery("SELECT "+KEY_Latitude+" FROM "+DATABASE_TABLE+" WHERE "+KEY_ID+"=?",new String[]{no});

            if(cursor.getCount()>0){
                cursor.moveToFirst();
                latitude = cursor.getString(cursor.getColumnIndex(KEY_Latitude));

            }

            return latitude;

        }finally {
            cursor.close();
        }
    }

    public String getDistribution(int position){
        Cursor cursor = null;
        String distribution = "";
        String no = position+"";
        try{
            cursor= ourDatabase.rawQuery("SELECT "+KEY_Distribution+" FROM "+DATABASE_TABLE+" WHERE "+KEY_ID+"=?",new String[]{no});

            if(cursor.getCount()>0){
                cursor.moveToFirst();
                distribution = cursor.getString(cursor.getColumnIndex(KEY_Distribution));

            }

            return distribution;

        }finally {
            cursor.close();
        }
    }

    public String getDensity(int position){
        Cursor cursor = null;
        String density = "";
        String no = position+"";
        try{
            cursor= ourDatabase.rawQuery("SELECT "+KEY_Density+" FROM "+DATABASE_TABLE+" WHERE "+KEY_ID+"=?",new String[]{no});

            if(cursor.getCount()>0){
                cursor.moveToFirst();
                density = cursor.getString(cursor.getColumnIndex(KEY_Density));

            }

            return density;

        }finally {
            cursor.close();
        }
    }

    public String getRemark(int position){
        Cursor cursor = null;
        String remark = "";
        String no = position+"";
        try{
            cursor= ourDatabase.rawQuery("SELECT "+KEY_Remark+" FROM "+DATABASE_TABLE+" WHERE "+KEY_ID+"=?",new String[]{no});

            if(cursor.getCount()>0){
                cursor.moveToFirst();
                remark = cursor.getString(cursor.getColumnIndex(KEY_Remark));

            }

            return remark;

        }finally {
            cursor.close();
        }
    }






}
