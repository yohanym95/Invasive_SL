package com.example.yohan.invasiveplantcounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class plantAdapter extends ArrayAdapter {

    public plantAdapter(@NonNull Context context, ArrayList<plant_item>plantList) {
        super(context, 0, plantList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,  @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position , View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.plant_row,parent,false
            );
        }

        ImageView imagePlant = convertView.findViewById(R.id.imageView2);
        TextView textPlant = convertView.findViewById(R.id.textView);


        plant_item plantItem = (plant_item) getItem(position);

        if(plantItem != null){

            imagePlant.setImageResource(plantItem.getPlant_Image());
            textPlant.setText(plantItem.getPlant_Name());

        }

        return convertView;
    }
}
