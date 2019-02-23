package com.example.fineart_ds.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fineart_ds.R;
import com.example.fineart_ds.ViewSanPham;
import com.squareup.picasso.Picasso;

public class ProductProperty extends AppCompatActivity {

    Button btnMuaHang;
    String product;
    TextView txtTen,txtGia,txtdes,txttype;
    ImageView imgSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_property);
        btnMuaHang= findViewById(R.id.buttonMuaHang);
        txtTen = findViewById(R.id.textViewTenSP);
        txtGia = findViewById(R.id.textViewGiaTien);
        txtdes = findViewById(R.id.textViewDiaChi);
        txttype = findViewById(R.id.textViewTrangThai);
        imgSP = findViewById(R.id.imageViewSP);

        // Nhận dữ liệu từ tìm kiếm Tượng gỗ phong thủy
        Intent intent1 = getIntent();
        txtTen.setText(intent1.getStringExtra("productNameTG").toString());
        txtGia.setText(intent1.getStringExtra("productPriceTG").toString());
        txtdes.setText(intent1.getStringExtra("productDescriptionTG").toString());
        //txttype.setText(intent.getStringExtra("productTypeID").toString());
        Uri uriTG = Uri.parse(intent1.getStringExtra("productImgTG").toString());
        Picasso.with(this).load(uriTG).into(imgSP);

        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ProductProperty.this);
                dialog.setTitle("Xác nhận thông tin");
                dialog.setContentView(R.layout.custom_dialog_mua_hang);
                Button btnXacNhan = dialog.findViewById(R.id.buttonXacNhan);
                Button btnHuy = dialog.findViewById(R.id.buttonHuy);
                btnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ProductProperty.this,"Đã đặt mua",Toast.LENGTH_LONG).show();
                        dialog.cancel();
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
