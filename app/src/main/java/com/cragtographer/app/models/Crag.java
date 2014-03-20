package com.cragtographer.app.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

public class Crag implements Serializable{
    public String Name;
    public LatLng Location;

    public Crag() {
    }

    public Crag(String name){
        this();
        this.Name = name;
    }

    public static class NameArrayAdapter extends ArrayAdapter<Crag> {
        private Context m_context;

        public NameArrayAdapter(Context context, List<Crag> model){
            super(context, android.R.layout.simple_list_item_1, model);
            m_context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(android.R.layout.simple_list_item_1, null);

            Crag modelElement = getItem(position);
            TextView elementView = (TextView)view.findViewById(android.R.id.text1);
            elementView.setText(modelElement.Name);

            return view;
        }
    }
}
