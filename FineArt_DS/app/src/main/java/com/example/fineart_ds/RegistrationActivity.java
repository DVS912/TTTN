package com.example.fineart_ds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fineart_ds.activity.LoginActivity;
import com.example.fineart_ds.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mEdtAcount, mEdtPassword , mEdtAddress, mEdtPhone;
    private Button mBtnCheck, mBtnHuy;
    private String url="http://gomynghevn.com/insertCustomer.php";
    private int check=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mEdtAcount = findViewById(R.id.mEdtAcount);
        mEdtAddress = findViewById(R.id.mEdtAddress);
        mEdtPassword = findViewById(R.id.mEdtPassword);
        mEdtPhone = findViewById(R.id.mEdtPhone);
        mBtnCheck = findViewById(R.id.mBtnCheck);
        mBtnHuy = findViewById(R.id.mBtnHuy);
        mBtnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acount = mEdtAcount.getText().toString().trim();
                String address = mEdtAddress.getText().toString().trim();
                String phone = mEdtPhone.getText().toString().trim();
                String password = mEdtPassword.getText().toString().trim();
                if(acount.isEmpty()|| address.isEmpty()|| phone.isEmpty()||password.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    int count = phone.length();
                    if(count==10) {
//                        086, 096, 097, 098, 032, 033, 034, 035, 036, 037, 038, 039
                        String number = phone.substring(0,3);
                        if(number.equals("086")|| number.equals("096")|| number.equals("097")||
                                number.equals("098")||
                                number.equals("032")||
                                number.equals("033")||
                                number.equals("034")||
                                number.equals("035")||
                                number.equals("036")||
                                number.equals("037")||
                                number.equals("038")||
                                number.equals("039")){
                            getCustomer(phone);
                        }else{
                            Toast.makeText(RegistrationActivity.this, "Số điện thoại không chính xác", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        count = 0;
                        Toast.makeText(RegistrationActivity.this, "Nhập đúng số điện thoại. 10 số!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    private void  getCustomer(final String phone) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlGetCustomer, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Log.e("AMBE12030405", response.getJSONObject(0).getString("customer_name")+ " aaaa");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(response != null){

                    for(int i = 0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            if (phone.equals(jsonObject.getString("customer_phone"))){
                                check = 1;
                                break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    if (check == 0){
                        insertCustomer();
                        finish();

                    } else {
                        Toast.makeText(RegistrationActivity.this, "Số điện thoại đã được đăng ký", Toast.LENGTH_SHORT).show();
                        check = 0;
                    }




                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void insertCustomer(){
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")){
                    Toast.makeText(RegistrationActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity( new Intent(RegistrationActivity.this, LoginActivity.class));
                }else{
                    Toast.makeText(RegistrationActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> param = new HashMap<>();
                param.put("name", mEdtAcount.getText().toString().trim());
                param.put("address", mEdtAddress.getText().toString().trim());
                param.put("phone", mEdtPhone.getText().toString().trim());
                param.put("password", mEdtPassword.getText().toString().trim());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
