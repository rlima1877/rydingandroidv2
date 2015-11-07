package edu.temple.materialdesigntest.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.model.Bus;
import edu.temple.materialdesigntest.model.BusStop;
import edu.temple.materialdesigntest.utilities.BusLocationService;
import edu.temple.materialdesigntest.utilities.BusStopService;
import edu.temple.materialdesigntest.utilities.TravelTimeService;

/**
 * Created by rafaellima on 10/27/15.
 */
public class BusDetailsAdapter extends RecyclerView.Adapter<BusDetailsAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private Activity activity;
    private double previousLat;
    private double previousLon;
    private int duration;
    List<BusStop> busStops = Collections.emptyList();

    public BusDetailsAdapter(Context context, List<BusStop> busStops){
        Log.d("JSON", "Context " + context.toString());
        this.context = context;
        this.activity = (Activity)context;
        inflater = LayoutInflater.from(context);
        this.busStops = busStops;
        this.previousLat = 0;
        this.previousLon = 0;
        this.duration = 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_detail_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        BusStop currentBusStop = busStops.get(position);
        holder.icon.setImageResource(R.drawable.bus_icon_32);
        holder.busNumber.setText(Integer.toString(currentBusStop.getBusNumber()));
        String locationUrl = "http://templecs.com/bus/getbuslocation?id=" + currentBusStop.getBusID();
        BusLocationService busLocationService = new BusLocationService(activity, locationUrl);
        Thread t1 = new Thread(busLocationService);
        t1.start();
        try{
            t1.join();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }
        String travelTimeUrl = "";
        if(position == 0){
            travelTimeUrl = "http://templecs.com/bus/gettraveltime?startlat=" + busLocationService.getBus().getGeoLat()
                    + "&startlon=" + busLocationService.getBus().getGeoLong() + "&endlat=" + currentBusStop.getGeoLat()
                    + "&endlon=" + currentBusStop.getGeoLong();
            previousLat = currentBusStop.getGeoLat();
            previousLon = currentBusStop.getGeoLong();
        }
        else{
            travelTimeUrl = "http://templecs.com/bus/gettraveltime?startlat=" + previousLat
                    + "&startlon=" + previousLon + "&endlat=" + currentBusStop.getGeoLat()
                    + "&endlon=" + currentBusStop.getGeoLong();
            previousLat = currentBusStop.getGeoLat();
            previousLon = currentBusStop.getGeoLong();
        }

        TravelTimeService travelTimeService = new TravelTimeService(activity, travelTimeUrl);
        Thread t2 = new Thread(travelTimeService);
        t2.start();
        try{
            t2.join();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }
        String temp = travelTimeService.getTravelTime();
        int index = temp.indexOf(' ');
        duration += Integer.valueOf(temp.substring(0, index));
        holder.busETA.setText("Arriving at: " + currentBusStop.getName() + " in " + duration + " min(s)");

    }

    //In case we want to let user delete an item from the list.
    //Create a button is custom layout with an onclick listener calling this method.
    //Will need use of database or shared preferences for changes to persist.
    public void delete(int position){
        busStops.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return busStops.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView busNumber;
        TextView busETA;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            busNumber = (TextView) itemView.findViewById(R.id.textViewDetailsBusNumber);
            busETA = (TextView) itemView.findViewById(R.id.textViewEAT);
            icon = (ImageView) itemView.findViewById(R.id.listDetailsIcon);
        }
    }
}//end BusDetailsAdapter
