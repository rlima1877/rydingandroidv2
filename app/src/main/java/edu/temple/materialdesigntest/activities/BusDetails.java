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
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.adapters.BusDetailsAdapter;
import edu.temple.materialdesigntest.model.Bus;
import edu.temple.materialdesigntest.model.BusStop;
import edu.temple.materialdesigntest.utilities.BusStopService;
import edu.temple.materialdesigntest.network.VolleySingleton;

public class BusDetails extends AppCompatActivity {

    private BusDetailsAdapter busDetailsAdapter;
    private Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String url = "http://templecs.com/bus/getbusroute?busid=";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);
        setupToolbar();

        if(getIntent().getExtras() != null) {
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
            initializeViews(readBusStopJSON.getBusStopList());
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
            startActivity(new Intent(this.getApplicationContext(), MapsActivity.class));

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
