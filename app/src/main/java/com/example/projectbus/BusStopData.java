package com.example.projectbus;

class BusStopData {
    private int BusStopID;
    private String BusStop;

    public BusStopData(int busStopID, String busStop) {
        BusStopID = busStopID;
        BusStop = busStop;
    }

    @Override
    public String toString() {
        return  BusStop ;
    }

    public BusStopData()
    {

    }

    public int getBusStopID() {
        return BusStopID;
    }

    public void setBusStopID(int busStopID) {
        BusStopID = busStopID;
    }

    public String getBusStop() {
        return BusStop;
    }

    public void setBusStop(String busNo) {
        BusStop = busNo;
    }
}
