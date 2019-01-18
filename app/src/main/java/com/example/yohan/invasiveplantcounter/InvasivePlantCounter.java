package com.example.yohan.invasiveplantcounter;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class InvasivePlantCounter extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
