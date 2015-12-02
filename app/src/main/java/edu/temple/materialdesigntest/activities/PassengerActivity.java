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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.temple.materialdesigntest.model.Bus;
import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.adapters.BusListAdapter;
import edu.temple.materialdesigntest.utilities.BusService;
import edu.temple.materialdesigntest.network.VolleySingleton;
import edu.temple.materialdesigntest.utilities.ReadJSON;

public class PassengerActivity extends AppCompatActivity {
    private BusListAdapter busListAdapter;
    private BusService loadBus;
    private LinearLayout passengerContent;
    private LinearLayout loadingIcon;

    public static final String url = "http://templecs.com/bus/getallbuses";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        setupToolbar();

        passengerContent = (LinearLayout)findViewById(R.id.passsengerContent);
        passengerContent.setVisibility(View.GONE);
        loadingIcon = (LinearLayout)findViewById(R.id.loadingIcon);
        loadingIcon.setVisibility(View.VISIBLE);

        GetBusInformation task = new GetBusInformation();
        task.execute(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Ryding Passenger");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeViews(List<Bus> list){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.busList);
        busListAdapter = new BusListAdapter(this, list);
        recyclerView.setAdapter(busListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetBusInformation extends AsyncTask<Activity, Void, List<Bus>> {
        @Override
        protected List<Bus> doInBackground(Activity...activities) {
            try{
                List<Bus> busList = loadJSONFromNetwork(url);
                return busList;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Loading JSON from inputstream then store in arraylist
        private ArrayList<Bus> loadJSONFromNetwork(String urlString) throws XmlPullParserException, IOException {
            InputStream stream = null;
            ReadJSON readJSON = new ReadJSON();
            ArrayList<Bus> entries = new ArrayList();
            try {
                stream = downloadUrl(urlString);
                entries = readJSON.readBusJSON(stream);
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
            return entries;
        }

        //Creating inputstream from url
        private InputStream downloadUrl(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(50000 /* milliseconds */);
            conn.setConnectTimeout(50000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            return conn.getInputStream();
        }

        @Override
        protected void onPostExecute(List<Bus> busList) {
            initializeViews(busList);
            loadingIcon.setVisibility(View.GONE);
            passengerContent.setVisibility(View.VISIBLE);
        }
    }
}
