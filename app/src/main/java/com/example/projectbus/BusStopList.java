package com.example.projectbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class BusStopList extends AppCompatActivity {

    EditText txtbusstop;
    ListView ListBusStop;
    private ArrayAdapter arrayAdapter;
    private String StartStopHolder, EndStopHolder;
    private int UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busstoplist);

        txtbusstop = findViewById(R.id.txtbusstop);
        ListBusStop = findViewById(R.id.ListBusStop);
        StartStopHolder = getIntent().getStringExtra("StartStop");
        EndStopHolder = getIntent().getStringExtra("EndStop");
//        boolean DifferHolder = getIntent().getBooleanExtra("Differ");
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

        BusDataService busDataService = new BusDataService(BusStopList.this);

            busDataService.GetBusStop(new BusDataService.VolleyBusStopResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(BusStopList.this, "Error occur", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(List<BusStopData> BusStopReport) {
//                        Toast.makeText(bus.this, busData.toString(), Toast.LENGTH_SHORT).show();
                    arrayAdapter = new ArrayAdapter(BusStopList.this, android.R.layout.simple_list_item_1, BusStopReport);
                    ListBusStop.setAdapter(arrayAdapter);

                    ListBusStop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String Stop = BusStopReport.get(position).toString();
                            Intent intent = new Intent(BusStopList.this,home.class);
                            intent.putExtra("StartStop",Stop);
                            intent.putExtra("EndStop",EndStopHolder);
                            startActivity(intent);
                        }
                    });

                }
            });



        txtbusstop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (BusStopList.this).arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        ListBusStop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String Stop =
//            }
//        });


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