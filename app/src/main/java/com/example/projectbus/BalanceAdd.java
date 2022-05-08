package com.example.projectbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BalanceAdd extends AppCompatActivity {

    EditText txtamount, txtBankEmail, txtBankPassword;
    Button btnBankAdd, btnBackAdd;
    private String Email, Password;
    private int UserID, Amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balanceadd);

        txtamount = findViewById(R.id.txtamount);
        txtBankEmail = findViewById(R.id.txtBankEmail);
        txtBankPassword = findViewById(R.id.txtBankPassword);
        btnBankAdd = findViewById(R.id.btnBankAdd);
        btnBackAdd = findViewById(R.id.BtnBackAdd);

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

        btnBackAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserInfo.class);
                intent.putExtra("UserID",UserID);
                startActivity(intent);
            }
        });

        BusDataService busDataService = new BusDataService(BalanceAdd.this);

        btnBankAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = txtBankEmail.getText().toString();
                Password = txtBankPassword.getText().toString();
                String StringAmount = txtamount.getText().toString();
                Amount = Integer.parseInt(StringAmount);
                if (Email.matches(""))
                {
                    txtBankEmail.requestFocus();
                    txtBankEmail.setError("Bank Email Required");
                }
                else if (Password.matches(""))
                {
                    txtBankPassword.requestFocus();
                    txtBankPassword.setError("Bank Password Required");
                }
                else if(StringAmount.matches(""))
                {
                    txtamount.requestFocus();
                    txtamount.setError("Amount Required");
                }
                else if(Amount<1000 || Amount>5000)
                {
                    txtamount.requestFocus();
                    txtamount.setError("Amount Limit Exceed or Low");
                }
                else
                {
                    busDataService.GetConnectionBank(Email, Password, Amount, new BusDataService.VolleyConnectBankResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(BalanceAdd.this, "Error occur", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response) {
                            Log.i("response",response);
                            Log.i("Password",Password);
                            Log.i("Amount","" + Amount);
                            if (response.matches("1"))
                            {
                                busDataService.GetBalanceAdd(UserID, Amount, new BusDataService.VolleyBalanceAddResponseListener() {
                                    @Override
                                    public void onError(String message) {
                                        Toast.makeText(BalanceAdd.this, "Error occur", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(BalanceAdd.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(BalanceAdd.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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