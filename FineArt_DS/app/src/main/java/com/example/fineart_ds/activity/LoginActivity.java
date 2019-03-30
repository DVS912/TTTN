package com.example.fineart_ds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fineart_ds.NguoiDung;
import com.example.fineart_ds.R;
import com.example.fineart_ds.RegistrationActivity;
import com.example.fineart_ds.util.MyPreferenceHelper;
import com.example.fineart_ds.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText mEdtAcount , mEdtPassword , mEdtPhone;
    LinearLayout mLnDangNhap, mLnQuenMK;
    Button btnXacnhan, mBtnQuenMK;
    TextView txtDangky, txtDangNhap , mTvQuenMK;
    CheckBox mCBSave;
    private int check =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBtnQuenMK =findViewById(R.id.mBtnQuenMK);
        mEdtPhone = findViewById(R.id.editTextPhone);
        txtDangNhap = findViewById(R.id.textViewDangNhap);
        btnXacnhan= findViewById(R.id.btnXacNhan);
        txtDangky = findViewById(R.id.textViewDangKy);
        mEdtAcount = findViewById(R.id.editTextTaiKhoan);
        mEdtPassword = findViewById(R.id.editTextMatKhau);
        mCBSave = findViewById(R.id.mCbSave);
        mTvQuenMK = findViewById(R.id.textViewQuenMK);
        mLnDangNhap = findViewById(R.id.mLnDangNhap);
        mLnQuenMK = findViewById(R.id.mLnQuenMK);
        mTvQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLnDangNhap.setVisibility(View.GONE);
                mLnQuenMK.setVisibility(View.VISIBLE);
                mEdtPhone.setText("");
            }
        });
        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLnDangNhap.setVisibility(View.VISIBLE);
                mLnQuenMK.setVisibility(View.GONE);
            }
        });
        mBtnQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                String name = mEdtPhone.getText().toString().trim();
                if(name.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }else{
                    int count = name.length();
                    if(count==10) {
//                        086, 096, 097, 098, 032, 033, 034, 035, 036, 037, 038, 039
                        String number = name.substring(0,3);
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
                            getCustomerPassword(name);

                        }else{
                            Toast.makeText(LoginActivity.this, "Số điện thoại không chính xác", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        count = 0;
                        Toast.makeText(LoginActivity.this, "Nhập đúng số điện thoại. 10 số!", Toast.LENGTH_SHORT).show();

                    }
                }
            }catch (Exception e){
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(mCBSave.isChecked()){
            String acount = MyPreferenceHelper.getString("ACOUNT",LoginActivity.this);
            String pass = MyPreferenceHelper.getString("PASSWORD", LoginActivity.this);
            mEdtAcount.setText(acount);
            mEdtPassword.setText(pass);
            mCBSave.setChecked(true);
        }else {

        }

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acount = mEdtAcount.getText().toString().trim();
                String pass = mEdtPassword.getText().toString().trim();
                if(acount.isEmpty()||pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    getCustomer(acount, pass);
                    mEdtAcount.setText("");
                    mEdtPassword.setText("");
                }

            }
        });

        txtDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }
    private void  getCustomerPassword(final String phone) {
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
                                check =1;
                                String pass = jsonObject.getString("customer_password");
                                Toast.makeText(LoginActivity.this, "Mật khẩu của bạn là:  " + pass, Toast.LENGTH_SHORT).show();
                                mLnDangNhap.setVisibility(View.VISIBLE);
                                mLnQuenMK.setVisibility(View.GONE);
                                break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    if(check ==0){
                        Toast.makeText(LoginActivity.this, "Số điện thoại chưa được đăng ký", Toast.LENGTH_SHORT).show();
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
    private void  getCustomer(final String acount , final String pass) {
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

                            if (acount.equals(jsonObject.getString("customer_name")) &&
                            pass.equals(jsonObject.getString("customer_password"))){
                                check =1;
                                if(mCBSave.isChecked()) {
                                    MyPreferenceHelper.setString(LoginActivity.this, "ACOUNT", acount);
                                    MyPreferenceHelper.setString(LoginActivity.this, "PASSWORD", pass);
                                }
                                int id = jsonObject.getInt("customer_id");
                                Intent intent = new Intent(LoginActivity.this, NguoiDung.class);
                                intent.putExtra("key", id );
                                startActivity(intent);
                                break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    if(check ==0){
                        Toast.makeText(LoginActivity.this, "Thông tin không chính xác", Toast.LENGTH_SHORT).show();
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
}
