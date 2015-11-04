package edu.temple.materialdesigntest.activities;

import android.content.Intent;
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

public class DriverActivity extends AppCompatActivity {


    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private Spinner BusNumberSpin;
    private Spinner Direction;
    private Button select;
    private EditText busid;
    public static final String url = "http://templecs.com/bus/getallbuses";

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
        pullData();
    }


    public void pullData(){
        final List<Integer> busListTemp = new ArrayList<Integer>();
        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray buses = response.getJSONArray("buses");

                    for(int i= 0; i < buses.length(); i++){
                        int currentbusnumebr;
                        JSONObject currentObject = buses.getJSONObject(i);

                        currentbusnumebr = (Integer.parseInt(currentObject.getString("BusNumber")));
                        busListTemp.add(currentbusnumebr);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate the menu
        getMenuInflater().inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id== R.id.action_settings){
            Toast.makeText(this, "Hey you just hit " + item.getTitle(), Toast.LENGTH_LONG).show();
            return true;
        }

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
        select = (Button) findViewById(R.id.button);
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

        Intent intent = new Intent(this, DriverView.class);
       // NumberPicker routepic = (NumberPicker)findViewById(R.id.Route);
       // NumberPicker directionpic = (NumberPicker)findViewById(R.id.Direction)
       // String route = routepic.getDisplayedValues()[routepic.getValue()];
       // String direction = directionpic.getDisplayedValues()[directionpic.getValue()];
        intent.putExtra("route", BusNumberSpin.getSelectedItem().toString());
        intent.putExtra("direction", Direction.getSelectedItem().toString());
        intent.putExtra("busid",busid.getText().toString());
        startActivity(intent);


    }

}
