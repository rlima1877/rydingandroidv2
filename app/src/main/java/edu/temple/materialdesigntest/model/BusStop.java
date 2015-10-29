package edu.temple.materialdesigntest.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rafaellima on 10/21/15.
 */
public class BusStop implements Parcelable {

    private int busID;
    private int busNumber;
    private String name;
    private double latitude;
    private double longitude;

    public double getGeoLong() {
        return longitude;
    }

    public void setGeoLong(double geoLong) {
        this.longitude = geoLong;
    }

    public double getGeoLat() {
        return latitude;
    }

    public void setGeoLat(double geoLat) {
        this.latitude = geoLat;
    }

    public int getBusID() {
        return busID;
    }

    public void setBusID(int busID) {
        this.busID = busID;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public BusStop(){
        //empty
    }

    public BusStop(int busNumber, int busID, String name, double lat, double lon){
        this.busNumber = busNumber;
        this.busID = busID;
        this.name = name;
        this.latitude = lat;
        this.longitude = lon;
    }

    public BusStop(Parcel in) {
        this.busNumber = in.readInt();
        this.busID = in.readInt();
        this.name =  in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(busNumber);
        dest.writeInt(busID);
        dest.writeString(name);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public static final Creator<BusStop> CREATOR = new Creator<BusStop>()
    {
        public BusStop createFromParcel(Parcel in)
        {
            return new BusStop(in);
        }
        public BusStop[] newArray(int size)
        {
            return new BusStop[size];
        }
    };
}