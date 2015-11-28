package edu.temple.materialdesigntest.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.temple.materialdesigntest.model.Bus;
import edu.temple.materialdesigntest.model.BusStop;

public class ReadJSON {

    public ArrayList<Bus> readBusJSON(InputStream in) throws IOException {
        ArrayList<Bus> busList = new ArrayList<>();
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String tempStr = "";
        while((tempStr = bufferedReader.readLine()) != null){
            stringBuilder.append(tempStr);
        }

        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("buses");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject busObject = jsonArray.getJSONObject(i);
                Bus bus = new Bus();
                bus.setBusNumber(busObject.getInt("BusNumber"));
                bus.setBusRoute(busObject.getString("Direction"));
                bus.setBusID(busObject.getInt("BusID"));
                busList.add(bus);
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return busList;
    }

    public ArrayList<BusStop> readBusStopJSON(InputStream in) throws IOException {
        ArrayList<BusStop> busStopList = new ArrayList<>();
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String tempStr = "";
        while((tempStr = bufferedReader.readLine()) != null){
            stringBuilder.append(tempStr);
        }

        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("points");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject busStopObject = jsonArray.getJSONObject(i);
                BusStop busStop = new BusStop();
                busStop.setBusNumber(busStopObject.getInt("BusNumber"));
                busStop.setBusID(busStopObject.getInt("BusID"));
                busStop.setName(busStopObject.getString("Name"));
                busStop.setGeoLat(busStopObject.getDouble("Latitude"));
                busStop.setGeoLong(busStopObject.getDouble("Longitude"));
                busStopList.add(busStop);
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return busStopList;
    }

    public String readTravelTimeJSON(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String tempStr = "";
        String result = "";
        while((tempStr = bufferedReader.readLine()) != null){
            stringBuilder.append(tempStr);
        }

        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            result = jsonObject.getString("time");

        } catch (JSONException je) {
            je.printStackTrace();
        }
        return result;
    }

    public Bus readBusLocationJSON(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String tempStr = "";
        Bus bus = new Bus();
        while((tempStr = bufferedReader.readLine()) != null){
            stringBuilder.append(tempStr);
        }

        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            bus.setGeoLat(jsonObject.getDouble("Latitude"));
            bus.setGeoLong(jsonObject.getDouble("Longitude"));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return bus;
    }

    public String readBusLocationUpdateJSON(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String tempStr = "";
        while((tempStr = bufferedReader.readLine()) != null){
            stringBuilder.append(tempStr);
        }

        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            tempStr = jsonObject.getString("status");
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return tempStr;
    }
}