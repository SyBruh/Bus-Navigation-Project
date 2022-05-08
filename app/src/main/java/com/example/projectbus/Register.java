package com.example.projectbus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    EditText txtUserName, txtEmailadress, txtPhoneNumber, txtPasswordR, txtPasswordRconfirm;
    Button btnRegister, btnBack;
    TextView txtviewregister;
    private String UserName, Email, PhoneNumber, Password, ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        txtUserName = findViewById(R.id.txtUserName);
        txtEmailadress = findViewById(R.id.txtEmailadress);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtPasswordR = findViewById(R.id.txtPasswordR);
        txtPasswordRconfirm = findViewById(R.id.txtPasswordRconfirm);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);
        txtviewregister = findViewById(R.id.txtviewregister);

        BusDataService busDataService = new BusDataService(Register.this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("click","you Click");
                UserName = txtUserName.getText().toString();
                Email = txtEmailadress.getText().toString();
                PhoneNumber = txtPhoneNumber.getText().toString();
                Password = txtPasswordR.getText().toString();
                ConfirmPassword = txtPasswordRconfirm.getText().toString();
                if (UserName.matches(""))
                {
                    txtUserName.requestFocus();
                    txtUserName.setError("Username Required");
                }
                else if (Email.matches(""))
                {
                    txtEmailadress.requestFocus();
                    txtEmailadress.setError("Email Required");
                }
                else if (PhoneNumber.matches(""))
                {
                    txtPhoneNumber.requestFocus();
                    txtPhoneNumber.setError("PhoneNumber Required");
                }
                else if (Password.matches(""))
                {
                    txtPasswordR.requestFocus();
                    txtPasswordR.setError("Password Required");
                }
                else if (ConfirmPassword.matches(""))
                {
                    txtPasswordRconfirm.requestFocus();
                    txtPasswordRconfirm.setError("Confirm Password Required");
                }
                else if (!Password.matches(ConfirmPassword))
                {
                    txtPasswordRconfirm.requestFocus();
                    txtPasswordRconfirm.setError("Wrong Confirm Password");
                }
                else
                {
                    try {
                        busDataService.GetRegister(UserName, Email, PhoneNumber, Password, new BusDataService.VolleyRegisterResponseListener() {
                            @Override
                            public void onError(String message) {
                                Toast.makeText(Register.this, "Error occur", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(Register.this, response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEmailadress.setText("");
                txtPasswordR.setText("");
                txtPasswordRconfirm.setText("");
                txtPhoneNumber.setText("");
                txtUserName.setText("");
            }
        });
    }
}