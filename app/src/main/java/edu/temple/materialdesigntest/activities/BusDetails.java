package edu.temple.materialdesigntest.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.adapters.BusDetailsAdapter;
import edu.temple.materialdesigntest.model.Bus;
import edu.temple.materialdesigntest.model.BusStop;
import edu.temple.materialdesigntest.utilities.BusStopService;

public class BusDetails extends AppCompatActivity {


    private BusDetailsAdapter busDetailsAdapter;
    private Bus bus;
    private ArrayList<BusStop> busGeos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(savedInstanceState == null) {
            Log.d("DEBUGBUSDETAILS", "OnCreate being CALLED...savedInstanceState == null: " );
        }

        String url = "http://templecs.com/bus/getbusroute?busid=";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);
        setupToolbar();

        if(getIntent().getExtras() != null) {

            Log.d("DEBUGBUSDETAILS", "OnCreate being CALLED first if statem.... ");

            Bundle bundle = getIntent().getExtras();
            bus = (Bus)bundle.get("Bus");
            url += bus.getBusID();
            ProgressDialog progressDialog = ProgressDialog.show(this, "Loading", "Please wait!", true);
            BusStopService readBusStopJSON = new BusStopService(this, url);
            Thread threat = new Thread(readBusStopJSON);
            threat.start();
            try{
                threat.join();
                progressDialog.dismiss();
            }catch(InterruptedException ie){
                ie.printStackTrace();
                progressDialog.dismiss();
            }
            busGeos = readBusStopJSON.getBusStopList();
            initializeViews(readBusStopJSON.getBusStopList());
        }
        //user pressed back button, retrieving data from onSavedInstanceState
        if(savedInstanceState != null) {

            Log.d("DEBUGBUSDETAILS", "OnCreate being CALLED second if statem.... ");

            busGeos = savedInstanceState.getParcelableArrayList("bus_data");


            initializeViews(busGeos);
        }




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

    private void initializeViews(List<BusStop> list){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listBusDetails);
        busDetailsAdapter = new BusDetailsAdapter(this, list);
        recyclerView.setAdapter(busDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        //when user click on gps icon to get real time location of the bus
        if(id == R.id.locationIcon){

            //Bellow code sends data to MapActivity.class


            Intent intent = new Intent(this.getApplicationContext(), MapsActivity.class);
            intent.putExtra("current_bus_id", busGeos.get(0).getBusID());
            intent.putExtra("current_bus_number", busGeos.get(0).getBusNumber());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);



            //To grab data in MapActivity use bellow code inside onOncreate in MapActivity.
/*
            Bundle bundle = getIntent().getExtras();
            currentBus = bundle.getParcelable("current_bus");
            Log.d("JSON","CURRENT BUS OBJECT: " + currentBus.toString());
*/



        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {

        Log.d("DEBUGBUSDETAILS", "onSaveInstanceStateCalled.... " + busGeos.get(0).getBusNumber());

        bundle.putParcelableArrayList("bus_data", (ArrayList<BusStop>) busGeos);
        bundle.putChar("random",'c');
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        Log.d("DEBUGBUSDETAILS", "ONRESTORE CALLED.... ");


        busGeos = bundle.getParcelableArrayList("bus_data");


        initializeViews(busGeos);

    }

    @Override
    protected void onStop() {
        super.onStop();



    }
}
