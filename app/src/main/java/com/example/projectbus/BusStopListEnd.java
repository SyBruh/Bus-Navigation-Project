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

public class BusStopListEnd extends AppCompatActivity {

    EditText txtbusstopend;
    ListView ListBusStopEnd;
    private ArrayAdapter arrayAdapter;
    private String StartStopHolder, EndStopHolder;
    private int UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busstoplistend);

        txtbusstopend = findViewById(R.id.txtbusstopend);
        ListBusStopEnd = findViewById(R.id.ListBusStopEnd);
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

        BusDataService busDataService = new BusDataService(BusStopListEnd.this);

        busDataService.GetBusStop(new BusDataService.VolleyBusStopResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(BusStopListEnd.this, "Error occur", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<BusStopData> BusStopReport) {
//                        Toast.makeText(bus.this, busData.toString(), Toast.LENGTH_SHORT).show();
                arrayAdapter = new ArrayAdapter(BusStopListEnd.this, android.R.layout.simple_list_item_1, BusStopReport);
                ListBusStopEnd.setAdapter(arrayAdapter);

                ListBusStopEnd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String Stop = BusStopReport.get(position).toString();
                        Intent intent = new Intent(BusStopListEnd.this,home.class);
                        intent.putExtra("EndStop",Stop);
                        intent.putExtra("StartStop",StartStopHolder);
                        startActivity(intent);
                    }
                });

            }
        });



        txtbusstopend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (BusStopListEnd.this).arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

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