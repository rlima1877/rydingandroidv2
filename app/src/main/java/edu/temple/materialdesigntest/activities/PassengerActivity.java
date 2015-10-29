package edu.temple.materialdesigntest.activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import edu.temple.materialdesigntest.model.Bus;
import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.adapters.BusListAdapter;
import edu.temple.materialdesigntest.network.VolleySingleton;

public class PassengerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BusListAdapter busListAdapter;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public static final String url = "http://templecs.com/bus/getallbuses";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        setupToolbar();
        pullData();
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Ryding Passenger");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeViews(List<Bus> list){
        recyclerView = (RecyclerView) findViewById(R.id.busList);
        busListAdapter = new BusListAdapter(this, list);
        recyclerView.setAdapter(busListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    //Pulls data from webservice and initialize list view
    public void pullData(){
        final List<Bus> busListTemp = new ArrayList<Bus>();
        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray buses = response.getJSONArray("buses");

                    for(int i= 0; i < buses.length(); i++){
                        Bus currentBus = new Bus();
                        JSONObject currentObject = buses.getJSONObject(i);
                        currentBus.setBusNumber(Integer.parseInt(currentObject.getString("BusNumber")));
                        currentBus.setBusRoute(currentObject.getString("Direction"));
                        currentBus.setBusID(Integer.parseInt(currentObject.getString("BusID")));
                        busListTemp.add(currentBus);
                    }

                    //once data is pulled, initialize listview.
                    initializeViews(busListTemp);

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


    //this method mocks the call from json.
    public static List<Bus> getDataDummy() {

        List<Bus> buses = new ArrayList<Bus>();
        int[] busNumbers = {56, 45, 34, 65, 34, 35, 37, 90, 43, 67, 98, 30, 56, 45, 34, 65, 34};
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
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Hey you just hit " + item.getTitle(), Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        //when clicking tool bar search item do something..
        if (id == R.id.searchBar) {
            //startActivity(new Intent(this, SomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
