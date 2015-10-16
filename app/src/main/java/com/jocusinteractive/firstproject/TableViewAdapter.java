package com.jocusinteractive.firstproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.*;


/**
 * Created by Samuel on 15/10/15.
 */
public class TableViewAdapter extends ArrayAdapter {

    private Collection listOfObjects = new ArrayList();

    public TableViewAdapter(Context context, int resource) {
        super(context, resource);
    }


    @Override
    public void addAll(Collection collection) {
        super.addAll(collection);
        listOfObjects = collection;

    }

    @Override
    public int getCount() {
        return listOfObjects.size();
    }


    @Override
    public Object getItem(int position) {
        Object obj = listOfObjects.toArray()[position];
        return obj;
    }



    static class DataHandler{
        TextView primaryTextView;
        TextView secondaryTextView;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        DataHandler handler;

        if (convertView == null){
            //setup row first
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.table_view_cell, parent, false);
            handler = new DataHandler();
            handler.primaryTextView = (TextView) row.findViewById(R.id.primaryLabel);
            handler.secondaryTextView = (TextView) row.findViewById(R.id.secondaryLabel);
            row.setTag(handler);

        }else{
            //row already exists
            handler =(DataHandler) row.getTag();
        }

        Location location;
        location =(Location) this.getItem(position);


        handler.primaryTextView.setText(location.getName());
        handler.secondaryTextView.setText(location.getDescription());



        return row;
    }
}
