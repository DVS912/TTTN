package com.example.fineart_ds.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fineart_ds.R;
import com.example.fineart_ds.adapter.TranhGoAdapter;
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

public class TranhGoActivity extends AppCompatActivity {
    Toolbar toolbarTranhGo;
    ListView listViewTranhGo;
    TranhGoAdapter tranhGoAdapter;
    ArrayList<Product> arrayListTranhGo;
    int idTranhGo = 0;
    int page = 1;
    View footerView;
    boolean isLoading = false;
    mHandler mHandler;
    boolean limitData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tranh_go);
        toolbarTranhGo=findViewById(R.id.toolbarTranhGo);
        listViewTranhGo=findViewById(R.id.listViewTranhGo);
        arrayListTranhGo = new ArrayList<>();
        tranhGoAdapter = new TranhGoAdapter(getApplicationContext(), arrayListTranhGo);
        listViewTranhGo.setAdapter(tranhGoAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            getIDProductType();
            actionToolbar();
            getData(page);
            loadmoreData();
            mHandler = new mHandler();

        }else {
            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
            finish();
        }

    }

    private void loadmoreData() {
        listViewTranhGo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductProperty.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("TTT", arrayListTranhGo.get(position));
                intent.putExtra("BUN", bundle);

                startActivity(intent);
            }
        });
        listViewTranhGo.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading ==false && limitData == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void getData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.urlLoadProduct+String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String name = "";
                String price = "";
                String image = "";
                String mota ="";
                int idloaisanpham = 0;
                if(response != null && response.length() != 2){
                    listViewTranhGo.removeFooterView(footerView);
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

                            arrayListTranhGo.add(new Product(id, name, price, image, mota, idloaisanpham));
                            tranhGoAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitData = true;
                    listViewTranhGo.removeFooterView(footerView);
                    CheckConnection.showToast(getApplicationContext(),"Đã hết dữ liệu !");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param;
                param = new HashMap<String, String>();
                param.put("product_type_id",String.valueOf(idTranhGo));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarTranhGo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTranhGo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIDProductType() {
        idTranhGo = getIntent().getIntExtra("product_type_id", -1);
        Log.d("giatriloaisp", idTranhGo+"");
    }

    public class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listViewTranhGo.addFooterView(footerView);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
