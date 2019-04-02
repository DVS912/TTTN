package com.example.fineart_ds.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fineart_ds.R;
import com.example.fineart_ds.util.MyPreferenceHelper;
import com.example.fineart_ds.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {


    private Button btnCall;
    private TextView txtSDT, mTvTen, mTvDiaChi, mTvEmail;
    private ImageView mImgBackLH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        btnCall = findViewById(R.id.buttonCall);
        txtSDT = findViewById(R.id.textViewSDT);
        mImgBackLH = findViewById(R.id.mImgBackLH);
        mTvTen = findViewById(R.id.textViewTenAdmin);
        mTvDiaChi = findViewById(R.id.mTvDiaChi);
        mTvEmail =findViewById(R.id.textViewEmail);
        getAdmin();
        mImgBackLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callPhone();
            }
        });
    }
    private void callPhone(){
        String phoneNo = txtSDT.getText().toString().trim();
        try{
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+ phoneNo));//change the number
            startActivity(callIntent);

        }catch (Exception e){
            Toast.makeText(InfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void  getAdmin() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlGetAdmin, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){

                    for(int i = 0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            mTvTen.setText(jsonObject.getString("user_account"));
                            mTvDiaChi.setText(jsonObject.getString("user_address"));
                            mTvEmail.setText(jsonObject.getString("user_email"));
                            txtSDT.setText(jsonObject.getString("user_phone"));
                            break;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

