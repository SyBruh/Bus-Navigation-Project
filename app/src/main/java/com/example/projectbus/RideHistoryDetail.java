package com.example.projectbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RideHistoryDetail extends AppCompatActivity {

    EditText txtDHisBusID, txtDHisDriverName, txtDHisFeedBack;
    Button btnAddFeedBack, btnBackFeedBack;
    private int RideID, BusID, UserID;
    private String DriverName, FeedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ridehistorydetail);

        txtDHisBusID = findViewById(R.id.txtDHisBusID);
        txtDHisDriverName = findViewById(R.id.txtDHisDriverName);
        txtDHisFeedBack = findViewById(R.id.txtDHisFeedBack);
        btnAddFeedBack = findViewById(R.id.btnAddFeedBack);
        btnBackFeedBack = findViewById(R.id.btnBackFeedBack);

        RideID = getIntent().getIntExtra("RideID",0);
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

        BusDataService busDataService = new BusDataService(RideHistoryDetail.this);

        busDataService.GetRideHistoryDetail(RideID, new BusDataService.VolleyHistoryDetailResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(RideHistoryDetail.this, "Error occur", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONArray response) {
                JSONArray History = response;
                RideHistoryData data = new RideHistoryData();
                try {
                    JSONObject HistoryData = (JSONObject) History.get(0);
                    data.setBusID(HistoryData.getInt("BusID"));
                    data.setDriverName(HistoryData.getString("DriverName"));
                    data.setFeedBack(HistoryData.getString("FeedBack"));

                    txtDHisBusID.setText("" + data.getBusID());
                    txtDHisDriverName.setText(data.getDriverName());
                    txtDHisFeedBack.setText(data.getFeedBack());
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnBackFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),RideHistory.class);
                intent1.putExtra("UserID",UserID);
                startActivity(intent1);
            }
        });
        btnAddFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedBack = txtDHisFeedBack.getText().toString();
                if (FeedBack.matches(""))
                {
                    txtDHisFeedBack.requestFocus();
                    txtDHisFeedBack.setError("No FeedBack to add");
                }
                busDataService.GetFeedBack(RideID, FeedBack, new BusDataService.VolleySendFeedBackResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(RideHistoryDetail.this, "Error occur", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RideHistoryDetail.this, response, Toast.LENGTH_SHORT).show();
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