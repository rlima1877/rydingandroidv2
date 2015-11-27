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

import java.util.ArrayList;
import java.util.List;

import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.adapters.BusListAdapter;
import edu.temple.materialdesigntest.model.Bus;
import edu.temple.materialdesigntest.network.VolleySingleton;
import edu.temple.materialdesigntest.utilities.BusService;

public class DriverActivity extends AppCompatActivity {

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private Spinner BusNumberSpin;
    private Spinner Direction;
    private EditText busid;
    private BusService loadBus;
    public static final String url = "http://templecs.com/bus/getallbuses";
    private LinearLayout driverContent;
    private ImageView loadingIcon;

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
        loadingIcon = (ImageView)findViewById(R.id.loadingIcon);
        loadingIcon.setVisibility(View.VISIBLE);

        GetBusInformation task = new GetBusInformation();
        task.execute(this);
        //pullData();
    }

    private class GetBusInformation extends AsyncTask<Activity, Void, List<Bus>> {
        @Override
        protected List<Bus> doInBackground(Activity...activities) {
            loadBus = new BusService(activities[0], url);
            Thread threat = new Thread(loadBus);
            threat.start();
            try{
                threat.join();
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
            List<Bus> busList = loadBus.getBusList();
            return busList;
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
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        //when clicking tool bar search item do something..
        if(id == R.id.searchBar){
            //startActivity(new Intent(this, SomeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeViews(List<Integer> list){
        busid = (EditText) findViewById(R.id.busid);
        BusNumberSpin = (Spinner) findViewById(R.id.spinner);
        Direction = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_values,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Direction.setAdapter(adapter);
        ArrayAdapter<Integer> adapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list);
        BusNumberSpin.setAdapter(adapter1);
    }


    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Ryding Passenger");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void OpenView(View view){
        if(busid.getText().toString().length() > 0){
            Intent intent = new Intent(this, DriverView.class);
            intent.putExtra("busnumber", BusNumberSpin.getSelectedItem().toString());
            intent.putExtra("direction", Direction.getSelectedItem().toString());
            intent.putExtra("busid",busid.getText().toString());
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Please enter Bus ID", Toast.LENGTH_SHORT).show();
        }
    }
}