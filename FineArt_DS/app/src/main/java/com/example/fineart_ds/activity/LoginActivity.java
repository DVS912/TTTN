package com.example.fineart_ds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

    EditText mEdtAcount , mEdtPassword;
    Button btnXacnhan;
    TextView txtDangky, txtForget;
    CheckBox mCBSave;
    private int check =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnXacnhan= findViewById(R.id.btnXacNhan);
        txtDangky = findViewById(R.id.textViewDangKy);
        mEdtAcount = findViewById(R.id.editTextTaiKhoan);
        mEdtPassword = findViewById(R.id.editTextMatKhau);
        txtForget = findViewById(R.id.textViewQuenMK);
        mCBSave = findViewById(R.id.mCbSave);

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
