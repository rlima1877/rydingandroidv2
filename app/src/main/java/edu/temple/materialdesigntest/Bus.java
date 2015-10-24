package edu.temple.materialdesigntest;


/**
 * Created by rafaellima on 10/21/15.
 */
public class Bus {

    private int busID;
    private int busNumber;
    private String busRoute;
    private double geoLat;
    private double geoLong;

    public double getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(double geoLong) {
        this.geoLong = geoLong;
    }

    public double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(double geoLat) {
        this.geoLat = geoLat;
    }

    public Bus(){
        //empty
    }

    public Bus(int busID, int busNumber, String busRoute){

        this.busID = busID;
        this.busNumber = busNumber;
        this.busRoute = busRoute;
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

    public String getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(String busRoute) {
        this.busRoute = busRoute;
    }


    @Override
    public String toString() {
        return "Bus{" +
                "busID=" + busID +
                ", busNumber=" + busNumber +
                ", busRoute='" + busRoute + '\'' +
                '}';
    }
}