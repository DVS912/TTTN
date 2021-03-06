package com.example.fineart_ds.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fineart_ds.R;
import com.example.fineart_ds.ViewSanPham;
import com.example.fineart_ds.model.Product;
import com.example.fineart_ds.util.MyPreferenceHelper;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProductProperty extends AppCompatActivity {
    Button btnMuaHang;
    String product;
    TextView txtTen,txtGia,txtdes,txttype;
    ImageView imgSP, mImgBackPP;
    Toolbar toolbar;
    String url="http://gomynghevn.com/insertBuyProduct.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_property);
        btnMuaHang= findViewById(R.id.buttonMuaHang);
        txtTen = findViewById(R.id.textViewTenSP);
        txtGia = findViewById(R.id.textViewGiaTien);
        txtdes = findViewById(R.id.textViewDiaChi);
        imgSP = findViewById(R.id.imageViewSP);
        mImgBackPP =findViewById(R.id.mImgBackHomePP);
        mImgBackPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Nhận dữ liệu từ tìm kiếm Tượng gỗ phong thủy
        Intent intent1 = getIntent();
        Bundle bundle=intent1.getBundleExtra("BUN");
        Product product= (Product) bundle.getSerializable("TTT");

        txtTen.setText(product.getProductName());
        txtGia.setText(product.getProductPrice().toString());
        txtdes.setText(product.getProductDescription());
        //txttype.setText(intent.getStringExtra("productTypeID").toString());
        Uri uriTG = Uri.parse(product.getProductImage());
        Picasso.with(this).load(uriTG).into(imgSP);
        // nhận dữ liệu

        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ProductProperty.this);
                dialog.setTitle("Xác nhận thông tin");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.setContentView(R.layout.custom_dialog_mua_hang);
                Button btnXacNhan = dialog.findViewById(R.id.buttonXacNhan);
                final EditText mEdtTenhang = dialog.findViewById(R.id.mEdtTenHang);
                String tenhang= txtTen.getText().toString();
                final EditText  mEdtTenKhach = dialog.findViewById(R.id.mEdtTenKhach);
                final EditText mEdtDiachi = dialog.findViewById(R.id.mEdtDiaChi);
                final EditText mEdtSodienThoai = dialog.findViewById(R.id.mEdtSDT);


                // lấy thông tin từ trang thông tin khách hàng
                try {
                    String name = MyPreferenceHelper.getString(MyPreferenceHelper.NAME, getApplicationContext());
                    String phone = MyPreferenceHelper.getString(MyPreferenceHelper.PHONE, getApplicationContext());
                    String address = MyPreferenceHelper.getString(MyPreferenceHelper.ADDRESS, getApplicationContext());

                    if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                        Toast.makeText(ProductProperty.this, "không có thông tin tài khoản", Toast.LENGTH_SHORT).show();
                    } else {
                        mEdtTenKhach.setText(name);
                        mEdtSodienThoai.setText(phone);
                        mEdtDiachi.setText(address);
                    }
                }catch (Exception e){
                    Toast.makeText(ProductProperty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                mEdtTenhang.setText(tenhang);
                Button btnHuy = dialog.findViewById(R.id.buttonHuy);
                Button mBtnTru = dialog.findViewById(R.id.mBtnTru);
                Button mBtnCong = dialog.findViewById(R.id.mBtnCong);
                final EditText mEdtSoluong= dialog.findViewById(R.id.mEdtSoluong);
                mBtnCong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = mEdtSoluong.getText().toString();
                        int soluong = Integer.parseInt(number);
                        soluong = soluong + 1;
                        String show = String.valueOf(soluong);
                        mEdtSoluong.setText(show);
                    }
                });
                mBtnTru.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = mEdtSoluong.getText().toString();
                        int soluong = Integer.parseInt(number);
                        if(soluong>1){
                            soluong = soluong -1;
                            String show = String.valueOf(soluong);
                            mEdtSoluong.setText(show);
                        }else
                            Toast.makeText(ProductProperty.this, "Số lượng lớn hơn hoặc bằng 1", Toast.LENGTH_SHORT).show();
                    }
                });
                btnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hoten = mEdtTenKhach.getText().toString().trim();
                        final String diachi = mEdtDiachi.getText().toString().trim();
                        String sdt = mEdtSodienThoai.getText().toString().trim();
                        String tenhang = mEdtTenhang.getText().toString().trim();
                        String soluong = mEdtSoluong.getText().toString().trim();
                        if(hoten.isEmpty()|| diachi.isEmpty()|| sdt.isEmpty()|| tenhang.isEmpty()||soluong.isEmpty()){
                            Toast.makeText(ProductProperty.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        }else {
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if(response.trim().equals("success")){
                                                Toast.makeText(ProductProperty.this, "Đã đặt mua", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();
                                            }else {
                                                Toast.makeText(ProductProperty.this, "Không thành công", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ProductProperty.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String , String> params = new HashMap<String, String>();
                                    params.put("hoten", mEdtTenKhach.getText().toString().trim());
                                    params.put("diachi", mEdtDiachi.getText().toString().trim());
                                    params.put("sodienthoai", mEdtSodienThoai.getText().toString().trim());
                                    params.put("tenhang", mEdtTenhang.getText().toString().trim());
                                    params.put("soluong", mEdtSoluong.getText().toString().trim());
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ProductProperty.this,"Đã hủy",Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });



    }

}
