package com.cragtographer.app;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cragtographer.app.models.Crag;
import com.cragtographer.app.models.GlobalApplicationModel;
import com.cragtographer.app.models.Section;
import com.cragtographer.app.services.AddItemWithNameClickListener;
import com.google.android.gms.maps.model.LatLng;

public class SectionActivity extends ActionBarActivity {
    Section m_section;
    int m_areaIndex;
    int m_sectionIndex;

    ArrayAdapter<LatLng> m_parkingModelInterface;
    ArrayAdapter<LatLng> m_pathModelInterface;
    Crag.NameArrayAdapter m_cragModelInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        m_areaIndex = getIntent().getIntExtra(AreaActivity.AREA_INDEX_FROM_AREA, 0);
        m_sectionIndex = getIntent().getIntExtra(AreaActivity.SECTION_INDEX_FROM_AREA, 0);
        m_section = GlobalApplicationModel.GetSection(m_areaIndex, m_sectionIndex);

        ListView parkingView = (ListView)findViewById(R.id.parkingList);
        m_parkingModelInterface = new ArrayAdapter<LatLng>(this, android.R.layout.simple_list_item_1, m_section.ParkingSpots);
        parkingView.setAdapter(m_parkingModelInterface);

        updateLocationView();

        TextView addSectionButton = (TextView)findViewById(R.id.addCrag);
        addSectionButton.setOnClickListener(new AddItemWithNameClickListener("Crag", new onAddCrag()));
        m_cragModelInterface = new Crag.NameArrayAdapter(this, m_section.Crags);
        ListView cragView = (ListView)findViewById(R.id.cragList);
        cragView.setAdapter(m_cragModelInterface);
    }

    public class onAddCrag implements AddItemWithNameClickListener.AddListener {
        @Override
        public void onAdd(String name) {
            addCrag(name);
        }
    }

    public void addCrag(String name) {
        if(name.length() > 0) {
            m_cragModelInterface.add(new Crag(name));
        }
    }

    public void addParking(View view) {
        m_parkingModelInterface.add(new LatLng(0,0 ));
    }

    public void setLocationWithCurrentLocation() {
        m_section.Location = new LatLng(0, 0);
        updateLocationView();
    }

    public void setLocation(View view) {
        setLocationWithCurrentLocation();
    }

    public void updateLocationView() {
        String location = m_section.Location != null ? m_section.Location.toString() : "";
        TextView locationView = (TextView)findViewById(R.id.location);
        locationView.setText(location);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.section, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_section, container, false);
            return rootView;
        }
    }

}
