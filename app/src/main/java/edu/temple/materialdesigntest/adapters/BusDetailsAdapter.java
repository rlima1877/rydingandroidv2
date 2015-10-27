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

/**
 * Created by rafaellima on 10/27/15.
 */
public class BusDetailsAdapter extends RecyclerView.Adapter<BusDetailsAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;

    List<Bus> buses = Collections.emptyList();


    public BusDetailsAdapter(Context context, List<Bus> buses){


        Log.d("JSON", "Context " + context.toString());


        this.context=context;
        inflater = LayoutInflater.from(context);
        this.buses = buses;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_detail_row, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {

        Bus currentBus = buses.get(position);

        holder.icon.setImageResource(R.drawable.bus_icon_32);
        holder.busNumber.setText(Integer.toString(currentBus.getBusNumber()));
        holder.busETA.setText("Arriving at: " + "[busNearestStop]" + " in " + "[ETA in Mins]");

    }



    //In case we want to let user delete an item from the list.
    //Create a button is custom layout with an onclick listener calling this method.
    //Will need use of database or shared preferences for changes to persist.
    public void delete(int position){
        buses.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return buses.size();
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
