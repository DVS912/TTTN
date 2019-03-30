package com.example.fineart_ds;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
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
import com.example.fineart_ds.adapter.HistoryAdapter;
import com.example.fineart_ds.model.Product;
import com.example.fineart_ds.model.ProductHis;
import com.example.fineart_ds.util.MyPreferenceHelper;
import com.example.fineart_ds.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NguoiDung extends AppCompatActivity {

    private EditText mEdtName , mEdtPass, mEdtAddress;
    private TextView mTvPhone, mTvSuaThongTin, mTvLichSu;
    private LinearLayout mLnThongTin, mLnLichSu;
    private ImageView mImgBackHome;
    private ImageView mImgDelete;
    private ListView listViewProduct;
    private ArrayList<String> listHis;
    private ArrayList<ProductHis> productArrayList;
    private HistoryAdapter historyAdapter;
    private Button mBtnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);
        mImgBackHome = findViewById(R.id.mImgBackHome);
        mEdtName =findViewById(R.id.mEdtName);
        mEdtAddress = findViewById(R.id.mEdtAddress);
        mEdtPass = findViewById(R.id.mEdtPass);
        mTvPhone = findViewById(R.id.mTvPhone);
        mBtnUpdate = findViewById(R.id.mBtnUpdate);
        listViewProduct = findViewById(R.id.mLvHistory);
        mTvSuaThongTin = findViewById(R.id.mTvSuaThongTin);
        mTvLichSu = findViewById(R.id.mTvLichSu);
        mLnThongTin = findViewById(R.id.mLnThongTin);
        mLnLichSu = findViewById(R.id.mLnLichSu);
        mImgDelete = findViewById(R.id.mImgDelete);
        mTvSuaThongTin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mLnThongTin.setVisibility(View.VISIBLE);
                mLnLichSu.setVisibility(View.GONE);
                mTvLichSu.setTextColor(getResources().getColor(R.color.colorGray));
                mTvSuaThongTin.setTextColor(getResources().getColor(R.color.colorBack));
            }
        });
        mTvLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLnThongTin.setVisibility(View.GONE);
                mLnLichSu.setVisibility(View.VISIBLE);
                mTvSuaThongTin.setTextColor(getResources().getColor(R.color.colorGray));
                mTvLichSu.setTextColor(getResources().getColor(R.color.colorBack));
                getBill(listHis);
            }
        });
        productArrayList = new ArrayList<ProductHis>();
        historyAdapter = new HistoryAdapter(getApplicationContext(), productArrayList);
        listViewProduct.setAdapter(historyAdapter);
        listHis = new ArrayList<>();
//        mImgDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(NguoiDung.this, "Xóa", Toast.LENGTH_SHORT).show();
//            }
//        });
        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(NguoiDung.this);
                dialog.setTitle("Xác nhận");
                dialog.setIcon(R.drawable.hoi_cham);

                dialog.setMessage("Bạn có muốn xóa không ?");
                dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProductHis pro = (ProductHis) listViewProduct.getItemAtPosition(position);
                        String name  = pro.getNameProduct();
                        String phone = mTvPhone.getText().toString().trim();
                        deleteBill(name,phone);
                        historyAdapter.notifyDataSetChanged();
                        restartActivity(NguoiDung.this);
                    }
                });
                dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });

        BackHome();
        Intent intent = getIntent();
        int id = (int) intent.getIntExtra("key",0);
        getCustomer(id);
//        getBill(listHis);
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCustomer(Server.urlUpdateCustomer);
            }
        });

    }
    public static void restartActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }
    private void  getCustomer(final int id) {
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

                            if (id == jsonObject.getInt("customer_id")){
                                mEdtName.setText(jsonObject.getString("customer_name"));
                                mEdtAddress.setText(jsonObject.getString("customer_address"));
//                                Toast.makeText(NguoiDung.this, jsonObject.getString("customer_password"), Toast.LENGTH_SHORT).show();
                                mEdtPass.setText(jsonObject.getString("customer_password"));
                                mTvPhone.setText(jsonObject.getString("customer_phone"));
                                MyPreferenceHelper.setString(getApplicationContext(),MyPreferenceHelper.NAME,jsonObject.getString("customer_name"));
                                MyPreferenceHelper.setString(getApplicationContext(),MyPreferenceHelper.PHONE,jsonObject.getString("customer_phone"));
                                MyPreferenceHelper.setString(getApplicationContext(),MyPreferenceHelper.ADDRESS,jsonObject.getString("customer_address"));
                                break;
                            }

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
    private void  getProductHis(final ArrayList<String> listHis) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlGetProductHis, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Log.e("AMBE12030405", response.getJSONObject(0).getString("customer_name")+ " aaaa");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int id = 0;
                String name = "";
                String price = "";
                String image = "";
                String mota ="";
                if(response != null) {
                    Log.e("Duong1", listHis.size()+"");
                    for (int j = 0; j < listHis.size(); j++) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
//                                name = jsonObject.getString("product_name");
//                                Log.e("Duong2",name );
                                if(listHis.get(j).equals(jsonObject.getString("product_name"))){
                                    Log.e("duong",listHis.get(j));
                                    name = jsonObject.getString("product_name");
                                    price = jsonObject.getString("product_price");
                                    image = jsonObject.getString("product_image");
                                    mota = jsonObject.getString("product_description");
                                    productArrayList.add(new ProductHis(name, price,mota,image));
                                    historyAdapter.notifyDataSetChanged();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

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
    private void  getBill(final ArrayList<String> listHis) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlGetBill, new Response.Listener<JSONArray>() {
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
                            String phone = mTvPhone.getText().toString();
                            if (phone.equals(jsonObject.getString("bill_customer_phone"))){
                                listHis.add(jsonObject.getString("bill_product_name"));
                                Log.e("Duong", jsonObject.getString("bill_product_name"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    getProductHis(listHis);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    
    private void updateCustomer(String urlUpdate){
        RequestQueue requestQueue = Volley.newRequestQueue(NguoiDung.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name = mEdtName.getText().toString().trim();
                String pass = mEdtPass.getText().toString().trim();
                String address = mEdtAddress.getText().toString().trim();
                if(name.isEmpty()||pass.isEmpty()||address.isEmpty()){
                    Toast.makeText(NguoiDung.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if(response.equals("success")){
                        Toast.makeText(NguoiDung.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(NguoiDung.this, "Thông tin không chính xác", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NguoiDung.this, error.toString(), Toast.LENGTH_SHORT).show();
                
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String name = mEdtName.getText().toString().trim();
                String pass = mEdtPass.getText().toString().trim();
                String address = mEdtAddress.getText().toString().trim();
                String phone = mTvPhone.getText().toString().trim();
                Log.e("Duongffff", name);
                params.put("name", name);
                params.put("address", address);
                params.put("phone", phone);
                params.put("password", pass);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void deleteBill(final String name, final String phone){
        RequestQueue requestQueue = Volley.newRequestQueue(NguoiDung.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlDeleteBill, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name = mEdtName.getText().toString().trim();
                String pass = mEdtPass.getText().toString().trim();
                String address = mEdtAddress.getText().toString().trim();
                    if(response.equals("success")){
                        Toast.makeText(NguoiDung.this, "Xóa thành công", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(NguoiDung.this, "Không xóa được lịch sử", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NguoiDung.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.e("abc",name);
                Log.e("abc",phone);
                params.put("phonehis", phone);
                params.put("namehis", name);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void BackHome(){
        mImgBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NguoiDung.this, MainActivity.class));

            }
        });
    }




}
