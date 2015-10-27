package edu.temple.materialdesigntest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.adapters.BusDetailsAdapter;
import edu.temple.materialdesigntest.model.Bus;

public class BusDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BusDetailsAdapter busDetailsAdapter;
    private List<Bus> busList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        setupToolbar();
        initializeViews(getDataDummy());


        Bundle bundle = getIntent().getExtras();
        busList = bundle.getParcelableArrayList("bus_list");
        Log.d("JSON","BUS LIST INSIDE BUSDETAILS: " + busList.toString());

/*        if (getIntent().getExtras() != null) {
            for (String a : getIntent().getExtras().getStringArrayList("items_to_parse")) {
                Log.d("=======", "Data " + a);
            }
        }*/
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Bus Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeViews(List<Bus> list){
        recyclerView = (RecyclerView) findViewById(R.id.listBusDetails);
        busDetailsAdapter = new BusDetailsAdapter(this, list);
        recyclerView.setAdapter(busDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    //this method mocks the call from json.
    public static List<Bus> getDataDummy() {

        List<Bus> buses = new ArrayList<Bus>();
        int[] busNumbers = {56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56};
        String[] busRoutes = {"Inbound", "Northbound", "Northbound", "Inbound",
                "Inbound", "Northbound", "Inbound", "Inbound", "Inbound", "Northbound", "Northbound",
                "Inbound", "Inbound", "Northbound", "Northbound", "Inbound", "Northbound"};

        for (int i = 0; i < busNumbers.length && i < busRoutes.length; i++) {
            Bus currentBus = new Bus();
            currentBus.setBusNumber(busNumbers[i]);
            currentBus.setBusRoute(busRoutes[i]);
            buses.add(currentBus);
        }
        return buses;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu
        getMenuInflater().inflate(R.menu.detail_screen_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //control action bar menu using if or switch staments

        int id = item.getItemId();

        //when user click on gps icon to get real time location of the bus
        if(id == R.id.locationIcon){
            Toast.makeText(this, "Hey you just hit " + item.getTitle(), Toast.LENGTH_LONG).show();


            //Bellow code sends data to MapActivity.class

/*          Intent intent = new Intent(getActivityContext(), MapActivity.class);
            intent.putParcelable("bus_list", (Bus) currentBusInformation);

            view.getContext().startActivity(intent);*/


            //To grab data in MapActivity use bellow code inside onOncreate in MapActivity.
/*
            Bundle bundle = getIntent().getExtras();
            currentBus = bundle.getParcelable("bus_list");
            Log.d("JSON","CURRENT BUS OBJECT: " + currentBus.toString());
*/



        }


        return super.onOptionsItemSelected(item);
    }
}
