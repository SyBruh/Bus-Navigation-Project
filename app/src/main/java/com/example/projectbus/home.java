package com.example.projectbus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class home extends AppCompatActivity {

    EditText txtstartstop, txtendstop;
    Button BtnSearch;
    ListView ListRoute;
    private String StartStopHolder , EndStopHolder, StartStop, EndStop, SaveStop1, SaveStop2;
    private MainViewModel mainViewModel;
    private int UserID;
//    private String StartStopHolder , EndStopHolder ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        txtstartstop = findViewById(R.id.txtstartstop);
        txtendstop = findViewById(R.id.txtendstop);
        BtnSearch = findViewById(R.id.BtnSearch);
        ListRoute = findViewById(R.id.ListRoute);

//        if (savedInstanceState != null){
//            StartStop = savedInstanceState.getString("Save");
//            txtstartstop.setText(StartStop);
//            savedInstanceState.get(EndStop);
//            txtendstop.setText(EndStop);
//        }

        StartStopHolder = getIntent().getStringExtra("StartStop");
        if (StartStopHolder != null){
            txtstartstop.setText(StartStopHolder);
        }

        EndStopHolder = getIntent().getStringExtra("EndStop");
        if (EndStopHolder != null){
            txtendstop.setText(EndStopHolder);
        }
        UserID = getIntent().getIntExtra("UserID",0);

//        StartStop = txtstartstop.getText().toString();
//        EndStop = txtendstop.getText().toString();
        Log.i("StartStopHolder", "StartStopHolder is " + StartStopHolder);
        Log.i("EndStopHolder", "EndStopHolder is " + EndStopHolder);

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

        txtstartstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),BusStopList.class));
                Intent intent = new Intent(home.this,BusStopList.class);
                intent.putExtra("StartStop",StartStopHolder);
                intent.putExtra("EndStop",EndStopHolder);
                startActivity(intent);
            }
        });

        txtendstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),BusStopList.class));
                Intent intent = new Intent(home.this,BusStopListEnd.class);
                intent.putExtra("StartStop",StartStopHolder);
                intent.putExtra("EndStop",EndStopHolder);
                startActivity(intent);
            }
        });

        BtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String StartDestination = txtstartstop.getText().toString();
                String FinalDestination = txtendstop.getText().toString();
                if (StartDestination.matches(""))
                {
                    txtstartstop.requestFocus();
                    txtstartstop.setError("StartDestination Required");
                }
                else if (FinalDestination.matches(""))
                {
                    txtendstop.requestFocus();
                    txtendstop.setError("FinalDestination Required");
                }
                else
                {
                    BusDataService busDataService = new BusDataService(home.this);

                    busDataService.GetRoute(StartDestination, FinalDestination, new BusDataService.VolleyRouteResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(home.this, "Error occur", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(List<RouteData> RouteReport,List ID) {
                            ArrayAdapter arrayAdapter = new ArrayAdapter(home.this, android.R.layout.simple_list_item_1, RouteReport);
                            ListRoute.setAdapter(arrayAdapter);
                            Log.i("alert",ID.get(0).toString());

                            ListRoute.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String Stop = RouteReport.get(position).toString();
                                    int FRouteID = (int) ID.get(position);
                                    Intent intent = new Intent(home.this,RouteDetail.class);
                                    intent.putExtra("UserID",UserID);
                                    intent.putExtra("FRouteID",FRouteID);
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("SaveStart",StartStopHolder);
        outState.putString("SaveEnd",EndStopHolder);
        Log.i("StartStop", "onSaveInstantState" );
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("Bruh", "OnRestoreInstantstate" );
        StartStop = savedInstanceState.getString("SaveStart");
        txtstartstop.setText(StartStop);
        EndStop = savedInstanceState.getString("SaveEnd");
        txtendstop.setText(EndStop);
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
