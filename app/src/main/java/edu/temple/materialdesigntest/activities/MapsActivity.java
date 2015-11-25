package edu.temple.materialdesigntest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.model.BusStop;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    BusStop currentBus;
    Marker marker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        Bundle bundle = getIntent().getExtras();
        currentBus = bundle.getParcelable("current_bus");
        Log.d("JSON", "CURRENT BUS OBJECT: " + currentBus.getBusNumber());

        setupToolbar();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Route Map");

        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);


        LatLng geoLoc = new LatLng(currentBus.getGeoLat(), currentBus.getGeoLong());
        marker =  mMap.addMarker(new MarkerOptions()
                .position(geoLoc).title(String.valueOf(currentBus.getBusNumber()))
                .snippet("ETA to Ambler : 21 mins")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_icon_32)));
        marker.showInfoWindow();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate the menu
        getMenuInflater().inflate(R.menu.maps_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //grab id to work with buttons
        int id = item.getItemId();


        //when clicking refresh button this will trigger..
        if(id == R.id.refreshIcon){
            Toast.makeText(this, "Clicked on refresh", Toast.LENGTH_SHORT).show();

            // grab data and set to currentBus...
            currentBus.setGeoLat(40.98468347687379);
            currentBus.setGeoLong(-77.156569480896);

            LatLng newLoc = new LatLng(currentBus.getGeoLat(), currentBus.getGeoLong());

            //set new position of bus and move camera
            marker.setPosition(newLoc);
            marker.showInfoWindow();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14));



        }

        return super.onOptionsItemSelected(item);
    }




}
