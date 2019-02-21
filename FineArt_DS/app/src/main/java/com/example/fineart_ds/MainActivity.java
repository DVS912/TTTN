package com.example.fineart_ds;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.example.fineart_ds.adapter.ProductAdapter;
import com.example.fineart_ds.adapter.ProductTypeAdapter;
import com.example.fineart_ds.model.Product;
import com.example.fineart_ds.model.ProductType;
import com.example.fineart_ds.util.CheckConnection;
import com.example.fineart_ds.util.Server;
import com.squareup.picasso.Picasso;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recHome;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listHome;
    DrawerLayout drawerLayout;
    ArrayList<ProductType> arrayListProductType;
    ProductTypeAdapter productTypeAdapter;
    int productTypeID = 0;
    String productTypeName= "";
    String productTypeImage= "";
    ArrayList<Product> arrayListProduct;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.homeToolbar);
        recHome=findViewById(R.id.recViewHome);
        viewFlipper=findViewById(R.id.viewFlipper);
        navigationView=findViewById(R.id.naviView);
        listHome=findViewById(R.id.listHome);
        drawerLayout=findViewById(R.id.drawerLayout);
        arrayListProductType = new ArrayList<>();
        arrayListProductType.add(0,new ProductType(0, "Trang Chính",
                "http://gomynghevn.com/image/icon/home_icon.png"));
        arrayListProductType.add(1,new ProductType(0, "Đăng nhập",
                "http://gomynghevn.com/image/icon/login_icon.png"));
        productTypeAdapter = new ProductTypeAdapter(arrayListProductType, getApplicationContext());
        listHome.setAdapter(productTypeAdapter);
        arrayListProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(), arrayListProduct);
        recHome.setHasFixedSize(true);
        recHome.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recHome.setAdapter(productAdapter);
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            actionBar();
            actionViewFlipper();
            getProductTypeData();
            getNewProductData();
        }else {
            CheckConnection.showToast(getApplicationContext(),"Ứng dụng này yêu cầu kết nối mạng."+"\r\n"
            +"Vui lòng kiểm tra lại kết nối !");
            finish();
        }

    }

    private void getNewProductData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlNewProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int productID = 0;
                    String productName = "";
                    String productPrice = "";
                    String productImage = "";
                    String productDescription;
                    int productTypeID = 0;

                    for(int i = 0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            productID = jsonObject.getInt("product_id");
                            productName = jsonObject.getString("product_name");
                            productPrice = jsonObject.getString("product_price");
                            productImage = jsonObject.getString("product_image");
                            productDescription = jsonObject.getString("product_description");
                            productTypeID = jsonObject.getInt("product_type_id");
                            arrayListProduct.add(new Product(productID,productName, productPrice, productImage, productDescription, productTypeID));
                            productAdapter.notifyDataSetChanged();
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

    private void getProductTypeData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlProductType, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int i;
                    for(i = 0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            productTypeID = jsonObject.getInt("product_type_id");
                            productTypeName = jsonObject.getString("product_type_name");
                            productTypeImage = jsonObject.getString("product_type_image");
                            arrayListProductType.add(new ProductType(productTypeID, productTypeName, productTypeImage));
                            productTypeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    arrayListProductType.add(i+2,new ProductType(0, "Chính sách thanh toán và vận chuyển",
                            "http://gomynghevn.com/image/icon/chinh_sach_van_chuyen.png"));
                    arrayListProductType.add(i+3,new ProductType(0, "Thông tin liên hệ",
                            "http://gomynghevn.com/image/icon/info_icon.jpg"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void actionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    private void actionViewFlipper() {
        ArrayList<String> quangcao = new ArrayList<>();
        quangcao.add("https://drive.google.com/uc?id=159XnylDYFXjIgdP2AfRPC5Y7BbtEL-Z0");
        quangcao.add("https://drive.google.com/uc?id=1nY_xp5s1CRK95iOZqus-voNFaa91ENBU");
        quangcao.add("https://drive.google.com/uc?id=1XsgMVb8QeAhL2ldUmiPR7wRx8igBwI_m");
        for (int i=0; i<quangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(quangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        viewFlipper.setInAnimation(animation_slide_in);
//        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
//        viewFlipper.setInAnimation(animation_slide_out);
    }


}