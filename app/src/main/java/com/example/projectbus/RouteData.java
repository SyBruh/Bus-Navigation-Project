package com.example.projectbus;

class RouteData {
    private int FRouteID;
    private String StartDestination;
    private String FinalDestination;
    private String FRoute;
    private String FRouteUrl;

    public RouteData(int FRouteID, String startDestination, String finalDestination, String FRoute, String FRouteUrl) {
        this.FRouteID = FRouteID;
        StartDestination = startDestination;
        FinalDestination = finalDestination;
        this.FRoute = FRoute;
        this.FRouteUrl = FRouteUrl;
    }

    public RouteData() {

    }

    @Override
    public String toString() {
        return "StartDestination :" + StartDestination + '\n' +
                "FinalDestination :" + FinalDestination + '\n' +
                FRoute;
    }

    public int getFRouteID() {
        return FRouteID;
    }

    public void setFRouteID(int FRouteID) {
        this.FRouteID = FRouteID;
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

    public String getFRoute() {
        return FRoute;
    }

    public void setFRoute(String FRoute) {
        this.FRoute = FRoute;
    }

    public String getFRouteUrl() {
        return FRouteUrl;
    }

    public void setFRouteUrl(String FRouteUrl) {
        this.FRouteUrl = FRouteUrl;
    }
}
