package com.example.fineart_ds.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fineart_ds.R;
import com.example.fineart_ds.adapter.TuongGoPhongThuyAdapter;
import com.example.fineart_ds.model.Product;
import com.example.fineart_ds.util.CheckConnection;
import com.example.fineart_ds.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TuongGoPhongThuyActivity extends AppCompatActivity {
    Toolbar toolbarTuongGo;
    ListView listViewTuongGo;
    TuongGoPhongThuyAdapter tuongGoPhongThuyAdapter;
    ArrayList<Product> arrayListTuongGo;
    int idTuongGo = 1;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuong_go_phong_thuy);
        toolbarTuongGo=findViewById(R.id.toolbarTuongGoPhongThuy);
        listViewTuongGo=findViewById(R.id.listviewTuongGoPhongThuy);
        arrayListTuongGo = new ArrayList<>();
        tuongGoPhongThuyAdapter = new TuongGoPhongThuyAdapter(getApplicationContext(), arrayListTuongGo);
        listViewTuongGo.setAdapter(tuongGoPhongThuyAdapter);
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            getIDProductType();
            actionToolbar();
            getData(page);
        }else {
            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
            finish();
        }

    }

    private void getData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.urlTuongGo+String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String name = "";
                String price = "";
                String image = "";
                String mota ="";
                int idloaisanpham = 1;
                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id= jsonObject.getInt("product_id");
                            name =jsonObject.getString("product_name");
                            price =jsonObject.getString("product_price");
                            image =jsonObject.getString("product_image");
                            mota = jsonObject.getString("product_description");
                            idloaisanpham = jsonObject.getInt("product_type_id");

                            arrayListTuongGo.add(new Product(id, name, price, image, mota, idloaisanpham));
                            tuongGoPhongThuyAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("productTypeID",String.valueOf(idTuongGo));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarTuongGo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTuongGo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIDProductType() {
        idTuongGo = getIntent().getIntExtra("productTypeID", 1);
        Log.d("giatriloaisp", idTuongGo+"");
    }
}
