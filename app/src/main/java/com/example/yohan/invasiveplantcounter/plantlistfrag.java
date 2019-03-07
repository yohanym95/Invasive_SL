package com.example.yohan.invasiveplantcounter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class plantlistfrag extends ListFragment {

    ArrayList<plant_image> data;
    getItem activity;
    Context context;

    public interface getItem{

        void ItemSelected(int index);
    }

    public plantlistfrag(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (getItem) context;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        data = new ArrayList<plant_image>();

        plant_image plant1 = new plant_image("ulex","Ulex Europaeus");

        plant_image plant2 = new plant_image("Blue","Aristea Ecklonii(Blue Stars)");

        plant_image plant3 = new plant_image("plant","Ageratina Riparia(Mist Flower)");

        data.add(plant1);
        data.add(plant2);
        data.add(plant3);

        plant_imageAdapter plantAdapter = new plant_imageAdapter(getActivity(),data);

        setListAdapter(plantAdapter);

        // setListAdapter(new ArrayAdapter<Plant>(getActivity(),android.R.layout.simple_list_item_1,data));




    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        activity.ItemSelected(position);
    }



}
