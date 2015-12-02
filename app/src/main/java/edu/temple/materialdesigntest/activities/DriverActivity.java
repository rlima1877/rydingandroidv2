package edu.temple.materialdesigntest.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.adapters.BusListAdapter;
import edu.temple.materialdesigntest.model.Bus;
import edu.temple.materialdesigntest.network.VolleySingleton;
import edu.temple.materialdesigntest.utilities.BusService;
import edu.temple.materialdesigntest.utilities.ReadJSON;

public class DriverActivity extends AppCompatActivity {

    private Spinner BusNumberSpin;
    private EditText busid;
    public static final String url = "http://templecs.com/bus/getallbuses";
    private LinearLayout driverContent;
    private LinearLayout loadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Ryding Driver");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupToolbar();

        driverContent = (LinearLayout)findViewById(R.id.driverContent);
        driverContent.setVisibility(View.GONE);
        loadingIcon = (LinearLayout)findViewById(R.id.loadingIcon);
        loadingIcon.setVisibility(View.VISIBLE);

        GetBusInformation task = new GetBusInformation();
        task.execute(this);
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
            List<Integer> busNumList = new ArrayList<>();
            for(int i=0; i<busList.size(); i++){
                busNumList.add(busList.get(i).getBusNumber());
            }
            initializeViews(busNumList);
            loadingIcon.setVisibility(View.GONE);
            driverContent.setVisibility(View.VISIBLE);
        }
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
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeViews(List<Integer> list){
        Set<Integer> hs = new HashSet<>();
        hs.addAll(list);
        list.clear();
        list.addAll(hs);
        Collections.sort(list);
        busid = (EditText) findViewById(R.id.busid);
        BusNumberSpin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<Integer> adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, list);
        BusNumberSpin.setAdapter(adapter);
    }


    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Ryding Driver");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void OpenView(View view){
        String busID = busid.getText().toString();
        if(busID.length() > 0){
            try {
                int tempID = Integer.parseInt(busID);
                Intent intent = new Intent(this, DriverView.class);
                intent.putExtra("busnumber", BusNumberSpin.getSelectedItem().toString());
                intent.putExtra("busid",String.valueOf(tempID));
                startActivity(intent);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter Bus ID", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Please enter Bus ID", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}