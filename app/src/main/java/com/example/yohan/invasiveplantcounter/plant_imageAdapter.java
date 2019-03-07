package com.example.yohan.invasiveplantcounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class plant_imageAdapter extends ArrayAdapter<plant_image> {

    private final Context context;
    private final ArrayList<plant_image> values;

    public plant_imageAdapter(@NonNull Context context, ArrayList<plant_image> values) {
        super(context, R.layout.row_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout,parent,false);

        TextView tvPlantName = rowView.findViewById(R.id.tvPlant);
        ImageView ivPlant = rowView.findViewById(R.id.ivPlant);

        tvPlantName.setText(values.get(position).getTypeName());

        if(values.get(position).getType().equals("ulex")){
            ivPlant.setImageResource(R.mipmap.ulex1_);
        }else if(values.get(position).getType().equals("Blue")) {
            ivPlant.setImageResource(R.mipmap.blue1_);
        }else if(values.get(position).getType().equals("plant")) {
            ivPlant.setImageResource(R.mipmap.plant1_);
        }

        return rowView;

    }
}
