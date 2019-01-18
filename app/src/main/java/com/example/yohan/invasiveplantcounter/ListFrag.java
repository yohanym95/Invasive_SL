package com.example.yohan.invasiveplantcounter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class ListFrag extends ListFragment {
    ArrayList<String> data = new ArrayList<>();
    PlantDB db;
    getItem activity;
    Cursor cursor;

    public interface getItem{

        void ItemSelected(int index);
    }


    public ListFrag() {
        // Required empty public constructor
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (getItem) context;
        db = new PlantDB(context);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db.open();
        cursor = db.getName();

        int Irow = cursor.getColumnIndex(PlantDB.Key_DateTime);
        while (cursor.moveToNext()){
            data.add(cursor.getString(Irow));
            setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,data));
        }
        db.close();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        activity.ItemSelected(position);
    }
}
