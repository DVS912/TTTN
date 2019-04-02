package com.example.fineart_ds;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.example.fineart_ds.activity.BanGheActivity;
import com.example.fineart_ds.activity.BanTrangDiemActivity;
import com.example.fineart_ds.activity.CayBonSaiGoActivity;
import com.example.fineart_ds.activity.CustomAdapterSanPham;
import com.example.fineart_ds.activity.DongHoActivity;
import com.example.fineart_ds.activity.GiuongNguActivity;
import com.example.fineart_ds.activity.InfoActivity;
import com.example.fineart_ds.activity.LocBinhPhongThuyActivity;
import com.example.fineart_ds.activity.LoginActivity;
import com.example.fineart_ds.activity.SanPhamGoMyNgheKhacActivity;
import com.example.fineart_ds.activity.SapTuCheActivity;
import com.example.fineart_ds.activity.TranhGoActivity;
import com.example.fineart_ds.activity.TuKeTiviActivity;
import com.example.fineart_ds.activity.TuQuanAoActivity;
import com.example.fineart_ds.activity.TuRuouActivity;
import com.example.fineart_ds.activity.TuongGoPhongThuyActivity;
import com.example.fineart_ds.activity.TuongLinhVatActivity;
import com.example.fineart_ds.activity.TusachActivity;
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
    GridView gridSanPham;
    CustomAdapterSanPham customAdapterSanPham;
    private final int RECORD_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Permission();
        toolbar=findViewById(R.id.homeToolbar);
        viewFlipper=findViewById(R.id.viewFlipper);
        navigationView=findViewById(R.id.naviView);
        listHome=findViewById(R.id.listHome);
        drawerLayout=findViewById(R.id.drawerLayout);
        gridSanPham = findViewById(R.id.GripViewSanPham);
        arrayListProductType = new ArrayList<>();
        arrayListProductType.add(0,new ProductType(0, "Trang Chính",
                "http://gomynghevn.com/image/icon/home_icon.png"));
        arrayListProductType.add(1,new ProductType(0, "Đăng nhập",
                "http://gomynghevn.com/image/icon/login.png"));
        productTypeAdapter = new ProductTypeAdapter(arrayListProductType, getApplicationContext());
        listHome.setAdapter(productTypeAdapter);


        //productAdapter = new ProductAdapter(getApplicationContext(), arrayListProduct);
        //recHome.setHasFixedSize(true);
        //recHome.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        //recHome.setAdapter(productAdapter);

        // Custom adapter product
        arrayListProduct = new ArrayList<>();
        customAdapterSanPham = new CustomAdapterSanPham(this,R.layout.line_new_product,arrayListProduct);
        gridSanPham.setAdapter(customAdapterSanPham);


        // sự kiện click và gửi thông tin Intent
        gridSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewSanPham.class);
                intent.putExtra("productName",arrayListProduct.get(position).getProductName());
                intent.putExtra("productDescription",arrayListProduct.get(position).getProductDescription());
                intent.putExtra("productImg",arrayListProduct.get(position).getProductImage());
                intent.putExtra("productPrice",arrayListProduct.get(position).getProductPrice());
                intent.putExtra("productTypeID",arrayListProduct.get(position).getProductTypeID());


                startActivity(intent);
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            actionBar();
            actionViewFlipper();
            getProductTypeData();
            getNewProductData();
            catchOnItemListView();
        }else {
            CheckConnection.showToast(getApplicationContext(),"Ứng dụng này yêu cầu kết nối mạng."+"\r\n"
            +"Vui lòng kiểm tra lại kết nối !");
            finish();
        }

    }

    //Sự kiện click item thanh menu
    private void catchOnItemListView() {
        listHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                   case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                           Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, TuongGoPhongThuyActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, TuongLinhVatActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, TranhGoActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 5:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, CayBonSaiGoActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 6:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LocBinhPhongThuyActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 7:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, BanGheActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 8:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, TuKeTiviActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 9:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, TusachActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 10:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, TuRuouActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 11:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, DongHoActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 12:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, SapTuCheActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 13:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, GiuongNguActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 14:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, TuQuanAoActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 15:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, BanTrangDiemActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 16:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, SanPhamGoMyNgheKhacActivity.class);
                            intent.putExtra("product_type_id",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 17:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ChinhSachActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 18:
                       if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                           Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                           startActivity(intent);
                       }else {
                            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối internet !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    //Lấy dữ liệu sản phẩm
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
                            customAdapterSanPham.notifyDataSetChanged();
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

    //Lấy dữ liệu kiểu sản phẩm
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
                            productTypeName = jsonObject.getString("product_type_name").trim();
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
                            "http://gomynghevn.com/image/icon/info.png"));
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
//                drawerLayout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            }
        });
    }

    //Lấy dữ liệu cho banner
    private void actionViewFlipper() {
        ArrayList<String> quangcao = new ArrayList<>();
        quangcao.add("http://gomynghevn.com/image/banner/banner1.jpg");
        quangcao.add("http://gomynghevn.com/image/banner/banner2.png");
        quangcao.add("http://gomynghevn.com/image/banner/banner3.png");
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

    private void Permission(){
        int permission_call_phone = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);

        if (permission_call_phone != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }

    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CALL_PHONE}, RECORD_REQUEST_CODE);
    }



}
