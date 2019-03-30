package com.example.fineart_ds;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fineart_ds.util.MyPreferenceHelper;
import com.example.fineart_ds.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class ChinhSachActivity extends AppCompatActivity {

    private TextView mTvChinhSach, mTvDamBao, mTvDoiTra, mTvVanChuyen , mTvShow;
    private ImageView mImgBackCS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sach);
        mTvChinhSach = findViewById(R.id.mTvChinhSach);
        mImgBackCS = findViewById(R.id.mImgBackCS);
        mTvDamBao = findViewById(R.id.mTvDamBao);
        mTvDoiTra = findViewById(R.id.mTvDoiTra);
        mTvVanChuyen = findViewById(R.id.mTvVanChuyen);
        mTvShow = findViewById(R.id.mTvShow);
        mTvShow.setText("Loading.............");
        getChinhSach();
        String show = MyPreferenceHelper.getString(MyPreferenceHelper.CHINHSACH,ChinhSachActivity.this);
        mTvShow.setText(show);
        mTvChinhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvChinhSach.setTextColor(getResources().getColor(R.color.colorBack));
                mTvDamBao.setTextColor(getResources().getColor(R.color.colorGray));
                mTvDoiTra.setTextColor(getResources().getColor(R.color.colorGray));
                mTvVanChuyen.setTextColor(getResources().getColor(R.color.colorGray));
                mTvShow.setText("Loading.............");
                String show = MyPreferenceHelper.getString(MyPreferenceHelper.CHINHSACH,ChinhSachActivity.this);
                mTvShow.setText(show);
            }
        });
        mTvDoiTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvChinhSach.setTextColor(getResources().getColor(R.color.colorGray));
                mTvDamBao.setTextColor(getResources().getColor(R.color.colorGray));
                mTvDoiTra.setTextColor(getResources().getColor(R.color.colorBack));
                mTvVanChuyen.setTextColor(getResources().getColor(R.color.colorGray));
                mTvShow.setText("Loading.............");
                String show = MyPreferenceHelper.getString(MyPreferenceHelper.DOITRA,ChinhSachActivity.this);
                mTvShow.setText(show);
            }
        });
        mTvDamBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvChinhSach.setTextColor(getResources().getColor(R.color.colorGray));
                mTvDamBao.setTextColor(getResources().getColor(R.color.colorBack));
                mTvDoiTra.setTextColor(getResources().getColor(R.color.colorGray));
                mTvVanChuyen.setTextColor(getResources().getColor(R.color.colorGray));
                mTvShow.setText("Loading.............");
                String show = MyPreferenceHelper.getString(MyPreferenceHelper.DAMBAO,ChinhSachActivity.this);
                mTvShow.setText(show);
            }
        });
        mTvVanChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvChinhSach.setTextColor(getResources().getColor(R.color.colorGray));
                mTvDamBao.setTextColor(getResources().getColor(R.color.colorGray));
                mTvDoiTra.setTextColor(getResources().getColor(R.color.colorGray));
                mTvVanChuyen.setTextColor(getResources().getColor(R.color.colorBack));
                mTvShow.setText("Loading.............");
                String show = MyPreferenceHelper.getString(MyPreferenceHelper.VANCHUYEN,ChinhSachActivity.this);
                mTvShow.setText(show);
            }
        });



        mImgBackCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void getChinhSach(){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlGetPolicy, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    if(response != null){

                        for(int i = 0; i<response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                MyPreferenceHelper.setString(ChinhSachActivity.this,MyPreferenceHelper.CHINHSACH,jsonObject.getString("policy_policy"));
                                MyPreferenceHelper.setString(ChinhSachActivity.this,MyPreferenceHelper.DAMBAO,jsonObject.getString("policy_guaranteed"));
                                MyPreferenceHelper.setString(ChinhSachActivity.this,MyPreferenceHelper.DOITRA,jsonObject.getString("policy_change"));
                                MyPreferenceHelper.setString(ChinhSachActivity.this,MyPreferenceHelper.VANCHUYEN,jsonObject.getString("policy_transport"));
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

