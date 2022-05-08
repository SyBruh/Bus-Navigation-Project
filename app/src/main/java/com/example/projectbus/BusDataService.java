package com.example.projectbus;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusDataService {

    Context context;

    public BusDataService(Context context) {
        this.context = context;
    }

    public  interface VolleyResponseListener{
        void onError(String message);

        void onResponse(List<BusData> BusReport);
    }

    public void GetBus(VolleyResponseListener volleyResponseListener)
    {
        List<BusData> BusReport = new ArrayList<>();
        String Url = "http://192.168.1.8/BusProject/MobileBus.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                volleyResponseListener.onResponse(response);
                JSONArray Bus = response;


                try {
                    for (int i = 0; i<Bus.length(); i++){
                        BusData data = new BusData();
                        JSONObject databus = (JSONObject) Bus.get(i);
                        data.setBusTypeID(databus.getInt("BusTypeID"));
                        data.setBusNo(databus.getInt("BusNo"));
                        BusReport.add(data);
                    }

                    volleyResponseListener.onResponse(BusReport);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                volleyResponseListener.onResponse("Error Occur");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
////                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
//                        volleyResponseListener.onResponse(response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Toast.makeText(context, "Error Occur", Toast.LENGTH_SHORT).show();
//                volleyResponseListener.onResponse("Error Occur");
//            }
//        });
//        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public  interface VolleyBusStopResponseListener{
        void onError(String message);

        void onResponse(List<BusStopData> BusStopReport);
    }

    public void GetBusStop(VolleyBusStopResponseListener volleyBusStopResponseListener)
    {
        List<BusStopData> BusStopReport = new ArrayList<>();
        String Url = "http://192.168.1.8/BusProject/MobileBusStop.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray BusStop = response;

                try {
                    for (int i = 0; i<BusStop.length(); i++) {
                        BusStopData data = new BusStopData();
                        JSONObject databusstop = (JSONObject) BusStop.get(i);
                        data.setBusStopID(databusstop.getInt("BusStopID"));
                        data.setBusStop(databusstop.getString("BusStop"));
                        BusStopReport.add(data);
                    }
                    volleyBusStopResponseListener.onResponse(BusStopReport);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public  interface VolleyRouteResponseListener{
        void onError(String message);

        void onResponse(List<RouteData> RouteReport,List ID);
    }

    public void GetRoute(String StartDestination, String FinalDestination, VolleyRouteResponseListener volleyRouteResponseListener)
    {
        List<RouteData> RouteReport = new ArrayList<>();
        List ID = new ArrayList();
        String Url = "http://192.168.1.8/BusProject/MobileRouteSelect.php?StartDestination=" + StartDestination +"&FinalDestination=" + FinalDestination;
//        Log.i("all", Url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray Route = response;

                try {
                    for (int i = 0; i<Route.length(); i++) {
                        RouteData data = new RouteData();
                        JSONObject dataroute = (JSONObject) Route.get(i);
                        data.setFRouteID(dataroute.getInt("FRouteID"));
                        data.setStartDestination(dataroute.getString("StartDestination"));
                        data.setFinalDestination(dataroute.getString("FinalDestination"));
                        data.setFRoute(dataroute.getString("FRoute"));
                        data.setFRouteUrl(dataroute.getString("FRouteUrl"));
                        RouteReport.add(data);
                        ID.add(data.getFRouteID());
                    }
                    volleyRouteResponseListener.onResponse(RouteReport,ID);
                    Log.i("all", ID.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public  interface VolleyBusDetailResponseListener{
        void onError(String message);

        void onResponse(JSONArray response);
    }

    public void GetBusDetail(int position, VolleyBusDetailResponseListener volleyBusDetailResponseListener)
    {
        String Url = "http://192.168.1.8/BusProject/MobileBusDetailRoute.php?position=" + position;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("all", response.toString());
                volleyBusDetailResponseListener.onResponse(response);
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface VolleyBusStopbypositionResponseListener{
        void onError(String message);

        void onResponse(List<BusStopData> BusStopReport);
    }

    public void GetBusStopbyposition(int position, VolleyBusStopbypositionResponseListener volleyBusStopbypositionResponseListener)
    {
        List<BusStopData> BusStopReport = new ArrayList<>();
        String Url = "http://192.168.1.8/BusProject/MobileBusDetailStop.php?position=" + position;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray BusStop = response;

                try {
                    for (int i = 0; i<BusStop.length(); i++) {
                        BusStopData data = new BusStopData();
                        JSONObject databusstop = (JSONObject) BusStop.get(i);
                        data.setBusStop(databusstop.getString("BusStop"));
                        BusStopReport.add(data);
                    }
                    volleyBusStopbypositionResponseListener.onResponse(BusStopReport);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface VolleyRouteDetailResponseListener{
        void onError(String message);

        void onResponse(List<RouteDetailData> RouteDetailReport);
    }

    public void GetRouteDetail(int FRouteID,VolleyRouteDetailResponseListener volleyRouteDetailResponseListener)
    {
        List<RouteDetailData> RouteDetailReport = new ArrayList<>();
        String Url = "http://192.168.1.8/BusProject/MobileRouteDetail.php?FRouteID=" + FRouteID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray RouteDetail = response;

                try {
                    for (int i = 0; i<RouteDetail.length(); i++) {
                        RouteDetailData data = new RouteDetailData();
                        JSONObject dataroutedetail = (JSONObject) RouteDetail.get(i);
                        data.setBusStop(dataroutedetail.getString("BusStop"));
                        data.setBusNo(dataroutedetail.getInt("BusNo"));
                        RouteDetailReport.add(data);
                    }
                    volleyRouteDetailResponseListener.onResponse(RouteDetailReport);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public  interface VolleyLoginStatusResponseListener{
        void onError(String message);

        void onResponse(JSONArray response);
    }

    public void GetLoginStatus(String Email, String Password,VolleyLoginStatusResponseListener volleyLoginStatusResponseListener )
    {
        String Url = "http://192.168.1.8/BusProject/MobileLogin.php?Email=" + Email + "&Password=" + Password;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("all", response.toString());
                volleyLoginStatusResponseListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public  interface VolleyRegisterResponseListener{
        void onError(String message);

        void onResponse(JSONObject response);
    }

    public void GetRegister(String UserName, String Email, String PhoneNumber, String Password,VolleyRegisterResponseListener volleyRegisterResponseListener) throws JSONException {
        JSONObject MyData = new JSONObject();
        MyData.put("UserName",UserName);
        MyData.put("Email", Email);
        MyData.put("PhoneNumber", PhoneNumber);
        MyData.put("Password", Password);
//
//        JSONArray Data;
//        try {
//            Data = new JSONArray(MyData);
//        }catch (JSONException e){
//            e.printStackTrace();
//        }

        Log.i("Call",MyData.toString());
        String Url = "http://192.168.1.8/BusProject/MobileRegister.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, MyData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                volleyRegisterResponseListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("UserName", UserName);
                MyData.put("Email", Email);
                MyData.put("PhoneNumber", PhoneNumber);
                MyData.put("Password", Password);//Add the data you'd like to send to the server.
                Log.i("array", MyData.toString());
                return MyData;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public  interface VolleyUserInfoResponseListener{
        void onError(String message);

        void onResponse(JSONArray response);
    }

    public void GetUserInfo(int UserID, VolleyUserInfoResponseListener volleyUserInfoResponseListener)
    {
        String Url = "http://192.168.1.8/BusProject/MobileUserInfo.php?UserID=" + UserID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("all", response.toString());
                volleyUserInfoResponseListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public  interface VolleyUserUpdateResponseListener{
        void onError(String message);

        void onResponse(JSONObject response);
    }

    public void GetUpdateUser(Integer UserID,String UserName, String Email, String PhoneNumber, String Password,VolleyUserUpdateResponseListener volleyUserUpdateResponseListener) throws JSONException {
        JSONObject MyData = new JSONObject();
        MyData.put("UserID",UserID);
        MyData.put("UserName",UserName);
        MyData.put("Email", Email);
        MyData.put("PhoneNumber", PhoneNumber);
        MyData.put("Password", Password);
//
//        JSONArray Data;
//        try {
//            Data = new JSONArray(MyData);
//        }catch (JSONException e){
//            e.printStackTrace();
//        }

        Log.i("Call",MyData.toString());
        String Url = "http://192.168.1.8/BusProject/MobileUserUpdate.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, MyData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                volleyUserUpdateResponseListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("UserName", UserName);
                MyData.put("Email", Email);
                MyData.put("PhoneNumber", PhoneNumber);
                MyData.put("Password", Password);//Add the data you'd like to send to the server.
                Log.i("array", MyData.toString());
                return MyData;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public  interface VolleyPaymentResponseListener{
        void onError(String message);

        void onResponse(String response);
    }

    public void GetPayment(int UserID, String BusID, VolleyPaymentResponseListener volleyPaymentResponseListener)
    {
        final String[] message = new String[1];
        String Url = "http://192.168.1.8/BusProject/MobilePayment.php?UserID=" + UserID + "&BusID=" + BusID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray PaymentStatus = response;
                try {
                    message[0] = PaymentStatus.getString(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyPaymentResponseListener.onResponse(message[0]);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public  interface VolleyRideHistoryResponseListener{
        void onError(String message);

        void onResponse(List<RideHistoryData> RideHistoryData, List ID);
    }

    public void GetRideHistory(int UserID, VolleyRideHistoryResponseListener volleyRideHistoryResponseListener)
    {
        List<RideHistoryData> RideHistoryData = new ArrayList<>();
        List ID = new ArrayList();
        String Url = "http://192.168.1.8/BusProject/MobileRideHistory.php?UserID=" + UserID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray History = response;

                try {
                    for (int i = 0; i<History.length(); i++) {
                        RideHistoryData data = new RideHistoryData();
                        JSONObject datahistory = (JSONObject) History.get(i);
                        data.setBusNo(datahistory.getInt("BusNo"));
                        data.setDriverName(datahistory.getString("DriverName"));
                        data.setFeedBack(datahistory.getString("FeedBack"));
                        data.setBusID(datahistory.getInt("BusID"));
                        data.setRideID(datahistory.getInt("RideID"));
                        RideHistoryData.add(data);
                        ID.add(data.getRideID());
                    }
                    volleyRideHistoryResponseListener.onResponse(RideHistoryData, ID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public  interface VolleyHistoryDetailResponseListener{
        void onError(String message);

        void onResponse(JSONArray response);
    }

    public void GetRideHistoryDetail(Integer RideID,VolleyHistoryDetailResponseListener volleyHistoryDetailResponseListener )
    {
        String Url = "http://192.168.1.8/BusProject/MobileRideHistoryDetail.php?RideID=" + RideID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("all", response.toString());
                volleyHistoryDetailResponseListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public  interface VolleySendFeedBackResponseListener{
        void onError(String message);

        void onResponse(String response);
    }

    public void GetFeedBack(int RideID,String FeedBack, VolleySendFeedBackResponseListener volleySendFeedBackResponseListener)
    {
        final String[] message = new String[1];
        String Url = "http://192.168.1.8/BusProject/MobileFeedBack.php?RideID=" + RideID + "&FeedBack=" + FeedBack;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray FeedBackStatus = response;
                try {
                    message[0] = FeedBackStatus.getString(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleySendFeedBackResponseListener.onResponse(message[0]);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public  interface VolleyConnectBankResponseListener{
        void onError(String message);

        void onResponse(String response);
    }

    public void GetConnectionBank(String Email,String Password,int Amount, VolleyConnectBankResponseListener volleyConnectBankResponseListener)
    {
        final String[] message = new String[1];
        String Url = "http://192.168.1.8/BusProject/MobileGetBank.php?Email=" + Email + "&Password=" + Password + "&Amount=" + Amount;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray Connection = response;
                try {
                    BankConnectionData data = new BankConnectionData();
                    JSONObject dataconnection = (JSONObject) Connection.get(0);
                    data.setBankResponse(dataconnection.getString("message"));
                    message[0] = data.getBankResponse();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyConnectBankResponseListener.onResponse(message[0]);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public  interface VolleyBalanceAddResponseListener{
        void onError(String message);

        void onResponse(String response);
    }

    public void GetBalanceAdd(int UserID,int Amount, VolleyBalanceAddResponseListener volleyBalanceAddResponseListener)
    {
        final String[] message = new String[1];
        String Url = "http://192.168.1.8/BusProject/MobileBalanceAdd.php?UserID=" + UserID + "&Amount=" + Amount;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray BalanceAdd = response;
                try {
                    message[0] = BalanceAdd.getString(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyBalanceAddResponseListener.onResponse(message[0]);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
