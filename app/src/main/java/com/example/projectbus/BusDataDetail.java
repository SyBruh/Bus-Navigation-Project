package com.example.projectbus;

class BusDataDetail {
    private int BusTypeID;
    private String StartTime;
    private String StopTime;
    private String StartDestination;
    private String FinalDestination;
    private int NoofGates;
    private int BusNo;
    private int Price;
    private String BusRouteUrl;

    public BusDataDetail(int busTypeID, String startTime, String stopTime, String startDestination, String finalDestination, int noofGates, int busNo, int price, String busRouteUrl) {
        BusTypeID = busTypeID;
        StartTime = startTime;
        StopTime = stopTime;
        StartDestination = startDestination;
        FinalDestination = finalDestination;
        NoofGates = noofGates;
        BusNo = busNo;
        Price = price;
        BusRouteUrl = busRouteUrl;
    }

    public BusDataDetail(){

    }


    @Override
    public String toString() {
        return "BusDataDetail{" +
                "BusTypeID=" + BusTypeID +
                ", StartTime='" + StartTime + '\'' +
                ", StopTime='" + StopTime + '\'' +
                ", StartDestination='" + StartDestination + '\'' +
                ", FinalDestination='" + FinalDestination + '\'' +
                ", NoofGates=" + NoofGates +
                ", BusNo=" + BusNo +
                ", Price=" + Price +
                ", BusRouteUrl='" + BusRouteUrl + '\'' +
                '}';
    }

    public int getBusTypeID() {
        return BusTypeID;
    }

    public void setBusTypeID(int busTypeID) {
        BusTypeID = busTypeID;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getStopTime() {
        return StopTime;
    }

    public void setStopTime(String stopTime) {
        StopTime = stopTime;
    }

    public String getStartDestination() {
        return StartDestination;
    }

    public void setStartDestination(String startDestination) {
        StartDestination = startDestination;
    }

    public String getFinalDestination() {
        return FinalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        FinalDestination = finalDestination;
    }

    public int getNoofGates() {
        return NoofGates;
    }

    public void setNoofGates(int noofGates) {
        NoofGates = noofGates;
    }

    public int getBusNo() {
        return BusNo;
    }

    public void setBusNo(int busNo) {
        BusNo = busNo;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getBusRouteUrl() {
        return BusRouteUrl;
    }

    public void setBusRouteUrl(String busRouteUrl) {
        BusRouteUrl = busRouteUrl;
    }
}
