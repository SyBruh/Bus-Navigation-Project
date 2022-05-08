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

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateUsers extends AppCompatActivity {

    EditText txtUpdateUserName, txtUpdateEmailadress, txtUpdatePhoneNumber, txtUpdatePasswordR;
    Button btnUpdate, btnBackUpdate;
    private String UserName, Email, PhoneNumber, Password;
    private int UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateusers);

        txtUpdateUserName = findViewById(R.id.txtUpdateUserName);
        txtUpdateEmailadress = findViewById(R.id.txtUpdateEmailadress);
        txtUpdatePhoneNumber = findViewById(R.id.txtUpdatePhoneNumber);
        txtUpdatePasswordR = findViewById(R.id.txtUpdatePasswordR);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBackUpdate = findViewById(R.id.btnBackUpdate);

        UserID = getIntent().getIntExtra("UserID",0);
        txtUpdateUserName.setText(getIntent().getStringExtra("UserName"));
        txtUpdateEmailadress.setText(getIntent().getStringExtra("Email"));
        txtUpdatePhoneNumber.setText(getIntent().getStringExtra("PhoneNumber"));
        txtUpdatePasswordR.setText(getIntent().getStringExtra("Password"));

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

        btnBackUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserInfo.class);
                intent.putExtra("UserID",UserID);
                startActivity(intent);
            }
        });

        BusDataService busDataService =new BusDataService(UpdateUsers.this);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserName = txtUpdateUserName.getText().toString();
                Email = txtUpdateEmailadress.getText().toString();
                PhoneNumber = txtUpdatePhoneNumber.getText().toString();
                Password = txtUpdatePasswordR.getText().toString();
                if (UserName.matches(""))
                {
                    txtUpdateUserName.requestFocus();
                    txtUpdateUserName.setError("Username Required");
                }
                else if (Email.matches(""))
                {
                    txtUpdateEmailadress.requestFocus();
                    txtUpdateEmailadress.setError("Email Required");
                }
                else if (PhoneNumber.matches(""))
                {
                    txtUpdatePhoneNumber.requestFocus();
                    txtUpdatePhoneNumber.setError("PhoneNumber Required");
                }
                else if (Password.matches(""))
                {
                    txtUpdatePasswordR.requestFocus();
                    txtUpdatePasswordR.setError("Password Required");
                }
                else
                {
                    try {
                        busDataService.GetUpdateUser(UserID, UserName, Email, PhoneNumber, Password, new BusDataService.VolleyUserUpdateResponseListener() {
                            @Override
                            public void onError(String message) {
                                Toast.makeText(UpdateUsers.this, "Error occur", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(UpdateUsers.this, response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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