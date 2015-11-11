package edu.temple.materialdesigntest.activities;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.network.VolleySingleton;

public class DriverView extends AppCompatActivity {

    private String route;
    private String direction;
    private String id;
    private double latitude;
    private double longitude;

    private TextView routeText;
    private TextView directionText;
    private TextView busIDText;
    private TextView latitudeText;
    private TextView longitudeText;
    private TextView statusText;
    private TextView responseText;
    
    //Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverview);

        //get route and direction submitted from DriverActivity
        route = getIntent().getExtras().getString("route");
        direction = getIntent().getExtras().getString("direction");
        id = getIntent().getExtras().getString("busid");

        //PLACEHOLDER - display these values to show they were passed successfully
        routeText = (TextView)findViewById(R.id.route);
        directionText = (TextView)findViewById(R.id.direction);
        busIDText = (TextView)findViewById(R.id.busid);
        latitudeText = (TextView)findViewById(R.id.latitude);
        longitudeText = (TextView)findViewById(R.id.longitude);
        statusText = (TextView)findViewById(R.id.status);
        responseText = (TextView)findViewById(R.id.response);

        routeText.setText("Route: " + route);
        directionText.setText("Direction: " + direction);
        busIDText.setText("Bus ID: " + id);
        statusText.setText("Status: initialized");

        //the actual GPS part (not yet hooked into anything)
        String locationProvider = LocationManager.GPS_PROVIDER ;
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                Log.d("DRIVER", "onLocationChanged() called");
                statusText.setText("Status: onLocationChanged() called");
                // Called when a new location is found by the GPS provider.
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d("DRIVER", "lat: " + latitude + ", long: " + longitude);
                latitudeText.setText("Latitude: " + latitude);
                longitudeText.setText("Longitude: " + longitude);
                //Insert JSON call here
                String url = "http://templecs.com/bus/setbuslocation?id=" + id + "&lat=" + latitude + "&lon=" + longitude + "&direction=" + direction;
                Log.d("DRIVER", "url: " + url);
                statusText.setText("Status: querying " + url);
                VolleySingleton volleySingleton = VolleySingleton.getsInstance();
                RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("DRIVER", "onResponse() called");
                        statusText.setText("Status: onResponse() called");
                        try {
                            responseText.setText("Response: " + response.toString());
                            String status = response.getString("status");
                            String message = response.getString("message");
                            Log.d("DRIVER", "status: " + status + ", message: " + message);
                            if (!"success".equals(status)) {
                                Log.d("DRIVER", "Setting bus location failed: " + message);
                            }

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

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        //Start with last known location
        locationListener.onLocationChanged(locationManager.getLastKnownLocation(locationProvider));

        //Update every minute or when you change 50 meters
        locationManager.requestLocationUpdates(locationProvider, (1000*60*1), 50, locationListener);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    //Loop method for sending updates
    //lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);


}
