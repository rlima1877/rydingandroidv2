package edu.temple.materialdesigntest.activities;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import edu.temple.materialdesigntest.R;

public class DriverView extends AppCompatActivity {

    private String route;
    private String direction;

    //Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverview);

        //get route and direction submitted from DriverActivity
        route = getIntent().getExtras().getString("route");
        direction = getIntent().getExtras().getString("direction");

        //PLACEHOLDER - display these values to show they were passed successfully
        TextView routeText = (TextView)findViewById(R.id.route);
        TextView directionText = (TextView)findViewById(R.id.direction);
        routeText.setText("Route: " + route);
        directionText.setText("Direction: " + direction);

        //the actual GPS part (not yet hooked into anything)
        String locationProvider = LocationManager.GPS_PROVIDER ;
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the GPS provider.
                double lat = location.getLatitude();
                double log = location.getLongitude();
                //Insert JSON call here
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        //Update every minute or when you change 50 meters
        locationManager.requestLocationUpdates(locationProvider, (1000*60*1), 50, locationListener);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
    //Loop method for sending updates
    //lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);


}
