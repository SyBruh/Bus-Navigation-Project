package com.example.projectbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BusDetail extends AppCompatActivity {

    private int positionholder, BusNo;
    TextView txtBusNo, txtStartTime, txtStopTime, txtStartDestination1, txtFinalDestination1, txtPrice, txtNoofGates;
    Button btnbusdetailtext, btnbusdetailmap;
    ListView ListDetailBusStop;
    private int UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busdetail);

        txtBusNo = findViewById(R.id.txtBusNo);
        txtStartTime = findViewById(R.id.txtStartTime);
        txtStopTime = findViewById(R.id.txtStopTime);
        txtStartDestination1 = findViewById(R.id.txtStartDestination1);
        txtFinalDestination1 = findViewById(R.id.txtFinalDestination1);
        txtPrice = findViewById(R.id.txtPrice);
        txtNoofGates = findViewById(R.id.txtNoofGates);
        btnbusdetailtext = findViewById(R.id.btnbusdetailtext);
        btnbusdetailmap = findViewById(R.id.btnbusdetailmap);
        ListDetailBusStop = findViewById(R.id.ListDetailBusStop);
        positionholder = getIntent().getIntExtra("position",0);
        UserID = getIntent().getIntExtra("UserID",0);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()){
                    case R.id.dashboard:
                        Intent intentdash = new Intent(getApplicationContext(),home.class);
                        intentdash.putExtra("UserID",UserID);
                        startActivity(intentdash);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.about:
                        Intent intent = new Intent(getApplicationContext(),payment.class);
                        intent.putExtra("UserID",UserID);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        BusDataService busDataService = new BusDataService(BusDetail.this);

        busDataService.GetBusDetail(positionholder, new BusDataService.VolleyBusDetailResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(BusDetail.this, "Error occur", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONArray response) {
                JSONArray BusDetail = response;
                BusDataDetail data = new BusDataDetail();
                try {
                    JSONObject dataBusDetail = (JSONObject) BusDetail.get(0);
                    data.setBusTypeID(dataBusDetail.getInt("BusTypeID"));
                    data.setStartTime(dataBusDetail.getString("StartTime"));
                    data.setStopTime(dataBusDetail.getString("StopTime"));
                    data.setStartDestination(dataBusDetail.getString("StartDestination"));
                    data.setFinalDestination(dataBusDetail.getString("FinalDestination"));
                    data.setNoofGates(dataBusDetail.getInt("NoofGates"));
                    data.setBusNo(dataBusDetail.getInt("BusNo"));
                    data.setPrice(dataBusDetail.getInt("Price"));
                    data.setBusRouteUrl(dataBusDetail.getString("BusRouteUrl"));

                    BusNo = data.getBusNo();
                    Log.i("all", "Bus No is " + BusNo);

                    txtBusNo.setText("BusNo is " + BusNo);
                    txtStartTime.setText(data.getStartTime());
                    txtStopTime.setText(data.getStopTime());
                    txtStartDestination1.setText(data.getStartDestination());
                    txtFinalDestination1.setText(data.getFinalDestination());
                    txtPrice.setText("" + data.getPrice());
                    txtNoofGates.setText("" + data.getNoofGates());
                }catch (JSONException e) {
                    e.printStackTrace();
                }

//                txtBusNo.setText(data.getBusNo());
//                txtStartTime.setText(data.getStartTime());
//                txtStopTime.setText(data.getStopTime());
//                txtStartDestination1.setText(data.getStartDestination());
//                txtFinalDestination1.setText(data.getFinalDestination());
//                txtPrice.setText(data.getPrice());
//                txtNoofGates.setText(data.getNoofGates());


            }
        });

        busDataService.GetBusStopbyposition(positionholder, new BusDataService.VolleyBusStopbypositionResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(BusDetail.this, "Error occur", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<BusStopData> BusStopReport) {
                ArrayAdapter arrayAdapter = new ArrayAdapter(BusDetail.this, android.R.layout.simple_list_item_1, BusStopReport);
                ListDetailBusStop.setAdapter(arrayAdapter);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent intent = new Intent(getApplicationContext(),UserInfo.class);
                intent.putExtra("UserID",UserID);
                startActivity(intent);
                overridePendingTransition(0,0);
                return true;
            case R.id.History:
                Intent intent1 = new Intent(getApplicationContext(),RideHistory.class);
                intent1.putExtra("UserID",UserID);
                startActivity(intent1);
                overridePendingTransition(0,0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}