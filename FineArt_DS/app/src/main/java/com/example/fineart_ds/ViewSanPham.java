package com.example.fineart_ds;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ViewSanPham extends AppCompatActivity {
    Toolbar toolbar;
    Button btnMuaHang;
    String product;
    TextView txtTen,txtGia,txtdes,txttype;
    ImageView imgSP;
    String url="http://gomynghevn.com/insertBuyProduct.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_san_pham);
        btnMuaHang= findViewById(R.id.buttonMuaHang);
        txtTen = findViewById(R.id.textViewTenSP);
        txtGia = findViewById(R.id.textViewGiaTien);
        txtdes = findViewById(R.id.textViewDiaChi);
        imgSP = findViewById(R.id.imageViewSP);
        toolbar = findViewById(R.id.toolbarViewSP);
        actionToolbar();

        //Nhận dữ liệu từ Main
        Intent intent = getIntent();
        txtTen.setText(intent.getStringExtra("productName").toString());
        txtGia.setText(intent.getStringExtra("productPrice").toString());
        txtdes.setText(intent.getStringExtra("productDescription").toString());
        //txttype.setText(intent.getStringExtra("productTypeID").toString());
        Uri uri = Uri.parse(intent.getStringExtra("productImg").toString());
        Picasso.with(this).load(uri).into(imgSP);





        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ViewSanPham.this);

                //TextView txtTitle = dialog.findViewById(R.id.title);
                //Toast.makeText(ViewSanPham.this, " xxx  "+txtTitle.getText(), Toast.LENGTH_SHORT).show();
                dialog.setTitle("Xác nhận thông tin");
                dialog.setContentView(R.layout.custom_dialog_mua_hang);
                Button btnXacNhan = dialog.findViewById(R.id.buttonXacNhan);
                final EditText mEdtTenhang = dialog.findViewById(R.id.mEdtTenHang);
                String tenhang= txtTen.getText().toString();
                final EditText  mEdtTenKhach = dialog.findViewById(R.id.mEdtTenKhach);
                final EditText mEdtDiachi = dialog.findViewById(R.id.mEdtDiaChi);
                final EditText mEdtSodienThoai = dialog.findViewById(R.id.mEdtSDT);
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
                            Toast.makeText(ViewSanPham.this, "Số lượng lớn hơn hoặc bằng 1", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ViewSanPham.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        }else {
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if(response.trim().equals("success")){
                                                Toast.makeText(ViewSanPham.this, "Đã đặt mua", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();
                                            }else {
                                                Toast.makeText(ViewSanPham.this, "Không thành công", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ViewSanPham.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ViewSanPham.this,"Đã hủy",Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });



    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
