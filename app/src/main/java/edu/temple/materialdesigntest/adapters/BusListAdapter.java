package edu.temple.materialdesigntest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.temple.materialdesigntest.R;
import edu.temple.materialdesigntest.activities.BusDetails;
import edu.temple.materialdesigntest.model.Bus;

/**
 * Created by rafaellima on 10/22/15.
 *
 * This class receives a Context and a List<Bus> to populate recycler view.
 * This class also handles on click view for a particular bus list cell
 *
 */
public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;

    List<Bus> buses = Collections.emptyList();


    public BusListAdapter(Context context, List<Bus> buses){


        Log.d("JSON", "Context " + context.toString());


        this.context=context;
        inflater = LayoutInflater.from(context);
        this.buses = buses;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_row, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {

        final Bus currentBus = buses.get(position);

        holder.icon.setImageResource(R.drawable.bus_icon_32);
        holder.busNumber.setText(Integer.toString(currentBus.getBusNumber()));
        holder.busDirection.setText(currentBus.getBusRoute());


        //Click listener for each cell in the bus list.
        //This will change once we have ETA information
        //Pass current bus to next activity.

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BusDetails.class);
                intent.putExtra("Bus", currentBus);

                view.getContext().startActivity(intent);


                Toast.makeText(context, "Clicked on bus: " + position, Toast.LENGTH_SHORT).show();
            }
        });

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
        TextView busDirection;
        ImageView icon;

        public MyViewHolder(View itemView) {

            super(itemView);
            busNumber = (TextView) itemView.findViewById(R.id.textViewBusNumber);
            busDirection = (TextView) itemView.findViewById(R.id.textViewBusDirection);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);


        }
    }



}//end BusListAdapter
