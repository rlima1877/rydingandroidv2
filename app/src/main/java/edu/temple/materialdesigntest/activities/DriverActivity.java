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

    private EditText busid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        busid = (EditText) findViewById(R.id.busid);
        setupToolbar();
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

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Ryding Driver");
        setSupportActionBar(toolbar);
    }

    public void OpenView(View view){
        try {
            int tempID = getBusID();
            Intent intent = new Intent(this, DriverView.class);
            intent.putExtra("busid",String.valueOf(tempID));
            startActivity(intent);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter Bus ID", Toast.LENGTH_SHORT).show();
        }
    }

    public int getBusID() throws NumberFormatException {
        String busID = busid.getText().toString();
        return Integer.parseInt(busID);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}