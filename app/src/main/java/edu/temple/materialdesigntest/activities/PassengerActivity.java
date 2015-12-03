package edu.temple.materialdesigntest.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.temple.materialdesigntest.model.Bus;
import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.adapters.BusListAdapter;
import edu.temple.materialdesigntest.model.BusStop;
import edu.temple.materialdesigntest.utilities.BusService;
import edu.temple.materialdesigntest.network.VolleySingleton;
import edu.temple.materialdesigntest.utilities.ReadJSON;

public class PassengerActivity extends AppCompatActivity {
    private BusListAdapter busListAdapter;

    public static final String url = "http://templecs.com/bus/getallbuses";
    public ArrayList<Bus> busList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        setupToolbar();

        Bundle bundle = getIntent().getBundleExtra("BusList");
        if(bundle != null){
            busList = bundle.getParcelableArrayList("BusList");
            initializeViews(busList);
        }

        if(savedInstanceState != null) {
            busList = savedInstanceState.getParcelableArrayList("bus_data");
            initializeViews(busList);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Ryding Passenger");
        setSupportActionBar(toolbar);
    }

    private void initializeViews(List<Bus> list){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.busList);
        busListAdapter = new BusListAdapter(this, list);
        recyclerView.setAdapter(busListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList("bus_data", busList);
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        busList = bundle.getParcelableArrayList("bus_data");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu
        //getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
