package com.example.projectbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class RideHistory extends AppCompatActivity {

    private int UserID;
    Button btnbackhistory;
    ListView ListRideHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ridehistory);

        btnbackhistory = findViewById(R.id.btnbackhistory);
        ListRideHistory = findViewById(R.id.ListRideHistory);
        UserID = getIntent().getIntExtra("UserID",0);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);

        bottomNavigationView.setSelectedItemId(R.id.dashboard);

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
                        Intent intenthome = new Intent(getApplicationContext(),bus.class);
                        intenthome.putExtra("UserID",UserID);
                        startActivity(intenthome);
                        overridePendingTransition(0,0);
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

        btnbackhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),home.class);
                intent.putExtra("UserID",UserID);
                startActivity(intent);
            }
        });

        BusDataService busDataService = new BusDataService(RideHistory.this);

        busDataService.GetRideHistory(UserID, new BusDataService.VolleyRideHistoryResponseListener(){
            @Override
            public void onError(String message) {
                Toast.makeText(RideHistory.this, "Error occur", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<RideHistoryData> RideHistoryData, List ID) {
//                        Toast.makeText(bus.this, busData.toString(), Toast.LENGTH_SHORT).show();
                ArrayAdapter arrayAdapter = new ArrayAdapter(RideHistory.this, android.R.layout.simple_list_item_1, RideHistoryData);
                ListRideHistory.setAdapter(arrayAdapter);

                ListRideHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String Stop = BusReport.get(position).toString();
                        int RideID = (int) ID.get(position);
                        Intent intent = new Intent(RideHistory.this,RideHistoryDetail.class);
                        intent.putExtra("RideID",RideID);
                        startActivity(intent);
                    }
                });
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}