package com.example.fineart_ds;

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

import com.squareup.picasso.Picasso;

public class ViewSanPham extends AppCompatActivity {

    Button btnMuaHang;
    String product;
    TextView txtTen,txtGia,txtdes,txttype;
    ImageView imgSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_san_pham);
        btnMuaHang= findViewById(R.id.buttonMuaHang);
        txtTen = findViewById(R.id.textViewTenSP);
        txtGia = findViewById(R.id.textViewGiaTien);
        txtdes = findViewById(R.id.textViewDiaChi);
        txttype = findViewById(R.id.textViewTrangThai);
        imgSP = findViewById(R.id.imageViewSP);


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

                dialog.setContentView(R.layout.custom_dialog_mua_hang);
                Button btnXacNhan = dialog.findViewById(R.id.buttonXacNhan);
                Button btnHuy = dialog.findViewById(R.id.buttonHuy);
                btnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ViewSanPham.this,"Đã đặt mua",Toast.LENGTH_LONG).show();
                        dialog.cancel();
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
}
