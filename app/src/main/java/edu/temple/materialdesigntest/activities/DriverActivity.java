package edu.temple.materialdesigntest.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import edu.temple.materialdesigntest.R;

public class DriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Ryding Driver");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NumberPicker routepic = (NumberPicker)findViewById(R.id.Route);
        NumberPicker directionpic = (NumberPicker)findViewById(R.id.Direction);
        routepic.setMinValue(0);
        routepic.setMaxValue(1);
        routepic.setDisplayedValues(new String[]{"49", "Temple"});
        directionpic.setMinValue(0);
        directionpic.setMaxValue(1);
        directionpic.setDisplayedValues(new String[]{"Inbound", "Outbound"});
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

    public void OpenView(View view){
       // Intent intent = new Intent(this, DriverView.class);
       // NumberPicker routepic = (NumberPicker)findViewById(R.id.Route);
       // NumberPicker directionpic = (NumberPicker)findViewById(R.id.Direction)
       // String route = routepic.getDisplayedValues()[routepic.getValue()];
       // String direction = directionpic.getDisplayedValues()[directionpic.getValue()];
       // intent.putExtra("route", route);
       // intent.putExtra("direction", direction);
       // startActivity(intent);

        //TODO - create the next activity and open it
        //Toast.makeText(this, "Route: " + route + ", Direction: " + direction, Toast.LENGTH_LONG).show();
    }

}
