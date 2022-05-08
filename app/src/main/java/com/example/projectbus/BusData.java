package com.example.projectbus;

class BusData {
    private int BusTypeID;
    private int BusNo;

    public BusData(int busTypeID, int busNo) {
        BusTypeID = busTypeID;
        BusNo = busNo;
    }

    @Override
    public String toString() {
        return "                                     BusNo\n" +
                "                                           " + BusNo;
    }

    public BusData(){

    }

    public int getBusTypeID() {
        return BusTypeID;
    }

    public void setBusTypeID(int busTypeID) {
        BusTypeID = busTypeID;
    }

    public int getBusNo() {
        return BusNo;
    }

    public void setBusNo(int busNo) {
        BusNo = busNo;
    }
}
