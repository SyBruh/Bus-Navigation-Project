package com.example.projectbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class RouteDetail extends AppCompatActivity {

    Button btnRouteDetail,btnRouteDetailmap;
    ListView ListRouteDetail;
    private int FRouteID;
    private int UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routedetail);

        btnRouteDetail = findViewById(R.id.btnRouteDetail);
        btnRouteDetailmap = findViewById(R.id.btnRouteDetailmap);
        ListRouteDetail = findViewById(R.id.ListRouteDetail);

        FRouteID = getIntent().getIntExtra("FRouteID",0);
        UserID = getIntent().getIntExtra("UserID",0);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);

        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()){
                    case R.id.dashboard:
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
        BusDataService busDataService = new BusDataService(RouteDetail.this);

        busDataService.GetRouteDetail(FRouteID, new BusDataService.VolleyRouteDetailResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(RouteDetail.this, "Error occur", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<RouteDetailData> RouteDetailReport) {
                ArrayAdapter arrayAdapter = new ArrayAdapter(RouteDetail.this, android.R.layout.simple_list_item_1, RouteDetailReport);
                ListRouteDetail.setAdapter(arrayAdapter);
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