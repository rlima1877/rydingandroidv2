package edu.temple.materialdesigntest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

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

    private RecyclerView recyclerView;
    private BusDetailsAdapter busDetailsAdapter;
    private Bus bus;
    private BusStop busStop;
    private Button toMapButton;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public static String url = "http://templecs.com/bus/getbusroute?busid=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        setupToolbar();
        if(getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            bus = (Bus)bundle.get("Bus");
            url += bus.getBusID();
            BusStopService readBusStopJSON = new BusStopService(this, url);
            Thread threat = new Thread(readBusStopJSON);
            threat.start();
            try{
                threat.join();
            }catch(InterruptedException ie){
                ie.printStackTrace();
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
        recyclerView = (RecyclerView) findViewById(R.id.listBusDetails);
        busDetailsAdapter = new BusDetailsAdapter(this, list);
        recyclerView.setAdapter(busDetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //Pulls data from webservice and initialize list view
    public void pullData(){
        final List<BusStop> busStopList = new ArrayList<BusStop>();
        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray points = response.getJSONArray("points");

                    for(int i= 0; i < points.length(); i++){
                        BusStop currentStop = new BusStop();
                        JSONObject currentObject = points.getJSONObject(i);
                        currentStop.setBusID(Integer.parseInt(currentObject.getString("BusID")));
                        currentStop.setName(currentObject.getString("Name"));
                        currentStop.setGeoLat(currentObject.getDouble("Latitude"));
                        currentStop.setGeoLong(currentObject.getDouble("Longitude"));
                        currentStop.setBusNumber(bus.getBusNumber());
                        busStopList.add(currentStop);
                    }
                    //once data is pulled, initialize listview.
                    initializeViews(busStopList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", error.getMessage());
            }
        });
        requestQueue.add(request);
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
