package edu.temple.materialdesigntest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.model.Bus;
import edu.temple.materialdesigntest.model.BusStop;

/**
 * Created by rafaellima on 10/27/15.
 */
public class BusDetailsAdapter extends RecyclerView.Adapter<BusDetailsAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;

    List<BusStop> busStops = Collections.emptyList();


    public BusDetailsAdapter(Context context, List<BusStop> busStops){
        Log.d("JSON", "Context " + context.toString());
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.busStops = busStops;

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
        holder.busETA.setText("Arriving at: " + currentBusStop.getName() + " in " + "[ETA in Mins]");
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
