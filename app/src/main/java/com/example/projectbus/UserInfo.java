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

public class UserInfo extends AppCompatActivity {

    EditText txtViewUsername, txtViewEmail, txtViewPhoneNumber, txtViewPassword, txtViewBalance;
    Button btnupdateuser, btnbalanceadd;
    private String UserName, Email, PhoneNumber, Password;
    private int UserID, Balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);

        txtViewUsername = findViewById(R.id.txtViewUsername);
        txtViewEmail = findViewById(R.id.txtViewEmail);
        txtViewPhoneNumber = findViewById(R.id.txtViewPhoneNumber);
        txtViewPassword = findViewById(R.id.txtViewPassword);
        txtViewBalance = findViewById(R.id.txtViewBalance);
        btnupdateuser = findViewById(R.id.btnupdateuser);
        btnbalanceadd = findViewById(R.id.btnbalanceadd);

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

        BusDataService busDataService = new BusDataService(UserInfo.this);

        busDataService.GetUserInfo(UserID, new BusDataService.VolleyUserInfoResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(UserInfo.this, "Error occur", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONArray response) {
                JSONArray UserInfo = response;
                UserData data = new UserData();
                try {
                    JSONObject dataUserInfo = (JSONObject) UserInfo.get(0);
                    data.setUserID(dataUserInfo.getInt("UserID"));
                    data.setUserName(dataUserInfo.getString("UserName"));
                    data.setEmail(dataUserInfo.getString("Email"));
                    data.setPhoneNumber(dataUserInfo.getString("PhoneNumber"));
                    data.setPassword(dataUserInfo.getString("Password"));
                    data.setBalance(dataUserInfo.getInt("Balance"));

                    txtViewUsername.setText("" + data.getUserName());
                    txtViewEmail.setText("" + data.getEmail());
                    txtViewPhoneNumber.setText("" + data.getPhoneNumber());
                    txtViewPassword.setText("" + data.getPassword());
                    txtViewBalance.setText("" + data.getBalance());

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnupdateuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserName = txtViewUsername.getText().toString();
                Email = txtViewEmail.getText().toString();
                PhoneNumber = txtViewPhoneNumber.getText().toString();
                Password = txtViewPassword.getText().toString();
                Intent intent = new Intent(UserInfo.this,UpdateUsers.class);
                intent.putExtra("UserID",UserID);
                intent.putExtra("UserName",UserName);
                intent.putExtra("Email",Email);
                intent.putExtra("PhoneNumber",PhoneNumber);
                intent.putExtra("Password",Password);
                startActivity(intent);
            }
        });

        btnbalanceadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfo.this,BalanceAdd.class);
                intent.putExtra("UserID",UserID);
                startActivity(intent);
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