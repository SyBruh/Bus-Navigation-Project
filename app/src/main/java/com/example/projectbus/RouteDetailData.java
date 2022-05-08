package com.example.projectbus;

class RouteDetailData {
    private String BusStop;
    private int BusNo;

    public RouteDetailData(String busStop, int busNo) {
        BusStop = busStop;
        BusNo = busNo;
    }

    @Override
    public String toString() {
        return "BusStop :" + BusStop + '\n' +
                "BusNo :" + BusNo;
    }

    public RouteDetailData(){

    }

    public String getBusStop() {
        return BusStop;
    }

    public void setBusStop(String busStop) {
        BusStop = busStop;
    }

    public int getBusNo() {
        return BusNo;
    }

    public void setBusNo(int busNo) {
        BusNo = busNo;
    }
}
