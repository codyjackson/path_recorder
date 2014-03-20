package com.cragtographer.app;

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
import android.widget.ListView;

import com.cragtographer.app.models.Area;
import com.cragtographer.app.models.GlobalApplicationModel;
import com.cragtographer.app.models.Section;
import com.cragtographer.app.services.AddItemWithNameClickListener;
import com.google.android.gms.maps.model.LatLng;

public class AreaActivity extends ActionBarActivity {
    Area m_area;
    int m_areaIndex;
    ArrayAdapter<LatLng> m_parkingModelInterface;
    Section.NameArrayAdapter m_sectionModelInterface;

    public static final String AREA_INDEX_FROM_AREA = "AREA_INDEX_FROM_AREA";
    public static final String SECTION_INDEX_FROM_AREA = "SECTION_INDEX_FROM_AREA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        m_areaIndex = getIntent().getIntExtra(AreasActivity.AREA_INDEX_FROM_AREAS, 0);
        m_area = GlobalApplicationModel.GetArea(m_areaIndex);

        ListView parkingView = (ListView)findViewById(R.id.parkingList);
        m_parkingModelInterface = new ArrayAdapter<LatLng>(this, android.R.layout.simple_list_item_1, m_area.ParkingSpots);
        parkingView.setAdapter(m_parkingModelInterface);

        ListView sectionView = (ListView)getSectionView();
        m_sectionModelInterface = new Section.NameArrayAdapter(this, m_area.Sections);
        sectionView.setAdapter(m_sectionModelInterface);
        sectionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent sectionActivity = new Intent(AreaActivity.this, SectionActivity.class);
                sectionActivity.putExtra(AREA_INDEX_FROM_AREA, m_areaIndex);
                sectionActivity.putExtra(SECTION_INDEX_FROM_AREA, i);
                view.getContext().startActivity(sectionActivity);
            }
        });

        Button addSectionButton = (Button)findViewById(R.id.addSectionButton);
        addSectionButton.setOnClickListener(new AddItemWithNameClickListener("Section", new onAddSection()));
    }

    public ListView getSectionView(){
        return (ListView)findViewById(R.id.sectionList);
    }

    public void addParking(View v) {
        m_parkingModelInterface.add(new LatLng(0, 0));
    }

    class onAddSection implements AddItemWithNameClickListener.AddListener {
        @Override
        public void onAdd(String name) {
            addSection(name);
        }
    }

    public void addSection(String name) {
        if(name.length() > 0) {
            m_sectionModelInterface.add(new Section(name));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.area, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_area, container, false);
            return rootView;
        }
    }

}
