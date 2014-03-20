package com.cragtographer.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cragtographer.app.models.Area;
import com.cragtographer.app.models.GlobalApplicationModel;
import com.cragtographer.app.services.AddItemWithNameClickListener;

import java.util.ArrayList;
import java.util.List;

public class AreasActivity extends ActionBarActivity {
    private Area.NameArrayAdapter m_areasModelInterface;

    public static final String AREA_INDEX_FROM_AREAS = "AREA_INDEX_FROM_AREAS";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        GlobalApplicationModel.Areas.add(new Area("Castle Rock"));
        GlobalApplicationModel.Areas.add(new Area("Mt. Tam"));

        ListView areasView = getAreaView();
        m_areasModelInterface = new Area.NameArrayAdapter(this, GlobalApplicationModel.Areas);
        areasView.setAdapter(m_areasModelInterface);
        areasView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent sectionsActivity = new Intent(AreasActivity.this, AreaActivity.class);
                sectionsActivity.putExtra(AREA_INDEX_FROM_AREAS, i);
                view.getContext().startActivity(sectionsActivity);
            }
        });

        Button addAreaButton = (Button)findViewById(R.id.addAreaButton);
        addAreaButton.setOnClickListener(new AddItemWithNameClickListener("Area", new AddListener()));
    }

    class AddListener implements AddItemWithNameClickListener.AddListener {
        @Override
        public void onAdd(String name){
            addArea(name);
        }
    }

    public ListView getAreaView(){
        return (ListView)findViewById(R.id.areaList);
    }

    public void addArea(String areaName){
        if(areaName.length() > 0) {
            m_areasModelInterface.add(new Area(areaName));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.areas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_areas, container, false);
            return rootView;
        }
    }

}
