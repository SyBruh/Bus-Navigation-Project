package com.example.projectbus;

class RideHistoryData {
    private int BusNo;
    private String DriverName;
    private String FeedBack;
    private int BusID;
    private int RideID;

    public RideHistoryData(int busNo, String driverName, String feedBack, int busID, int rideID) {
        BusNo = busNo;
        DriverName = driverName;
        FeedBack = feedBack;
        BusID = busID;
        RideID = rideID;
    }

    public  RideHistoryData()
    {

    }

    @Override
    public String toString() {
        return "BusNo :" + BusNo + '\n' +
                "DriverName :" + DriverName + '\n' +
                "RideID :" + RideID;
    }

    public int getBusNo() {
        return BusNo;
    }

    public void setBusNo(int busNo) {
        BusNo = busNo;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public String getFeedBack() {
        return FeedBack;
    }

    public void setFeedBack(String feedBack) {
        FeedBack = feedBack;
    }

    public int getBusID() {
        return BusID;
    }

    public void setBusID(int busID) {
        BusID = busID;
    }

    public int getRideID() {
        return RideID;
    }

    public void setRideID(int rideID) {
        RideID = rideID;
    }
}
