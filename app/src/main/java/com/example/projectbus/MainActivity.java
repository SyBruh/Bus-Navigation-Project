package com.example.projectbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText txtemail, txtpassword;
    TextView text;
    Button btnLogin;
    private String Email, Password, EmailCheck, PasswordCheck;
    private int UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);
        btnLogin = findViewById(R.id.btnLogin);
        text = findViewById(R.id.textView);

        BusDataService busDataService = new BusDataService(MainActivity.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = txtemail.getText().toString();
                Password = txtpassword.getText().toString();
                if (Email.matches(""))
                {
                    txtemail.requestFocus();
                    txtemail.setError("Email Required");
                }
                else if (Password.matches(""))
                {
                    txtpassword.requestFocus();
                    txtpassword.setError("Password Required");
                }
                else
                {
                    busDataService.GetLoginStatus(Email, Password, new BusDataService.VolleyLoginStatusResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(MainActivity.this, "Error occur", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(JSONArray response) {
                            JSONArray Login = response;
                            UserData data = new UserData();
                            try {
                                JSONObject LoginData = (JSONObject) Login.get(0);
                                data.setUserID(LoginData.getInt("UserID"));
                                data.setUserName(LoginData.getString("UserName"));
                                data.setEmail(LoginData.getString("Email"));
                                data.setPhoneNumber(LoginData.getString("PhoneNumber"));
                                data.setPassword(LoginData.getString("Password"));
                                data.setBalance(LoginData.getInt("Balance"));

                                EmailCheck = data.getEmail();
                                PasswordCheck = data.getEmail();
                                UserID = data.getUserID();
                                if (EmailCheck == "")
                                {
//                                    Toast.makeText(MainActivity.this, "Wrong User information. Please Check the Email and Password again", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Intent intent = new Intent(MainActivity.this,home.class);
                                    intent.putExtra("UserID",UserID);
                                    startActivity(intent);
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    Log.i("bruh", "My Email is " + EmailCheck);
                    if (EmailCheck == null)
                    {
                        Toast.makeText(MainActivity.this, "Wrong User information. Please Check the Email and Password again", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });

    }
}