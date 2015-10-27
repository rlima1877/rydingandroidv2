package edu.temple.materialdesigntest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.temple.materialdesigntest.R;


public class  MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button driverButton;
    private Button passengerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Ryding");
        setSupportActionBar(toolbar);

        driverButton = (Button) findViewById(R.id.buttonDriver);
        driverButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), DriverActivity.class));
            }
        });


        passengerButton = (Button) findViewById(R.id.buttonPassenger);
        passengerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), PassengerActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id== R.id.action_settings){
            Toast.makeText(this,"Hey you just hit " + item.getTitle(), Toast.LENGTH_LONG).show();
            return true;
        }

        //when clicking tool bar search item do something..
        if(id == R.id.searchBar){
            //startActivity(new Intent(this, SomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


}
