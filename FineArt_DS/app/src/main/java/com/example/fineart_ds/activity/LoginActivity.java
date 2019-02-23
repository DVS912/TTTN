package com.example.fineart_ds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fineart_ds.NguoiDung;
import com.example.fineart_ds.R;
import com.example.fineart_ds.Registration;
import com.example.fineart_ds.User;

public class LoginActivity extends AppCompatActivity {

    Button btnXacnhan;
    TextView txtDangky;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnXacnhan= findViewById(R.id.btnXacNhan);
        txtDangky = findViewById(R.id.textViewDangKy);

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NguoiDung.class);
                startActivity(intent);
            }
        });

        txtDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Registration.class);
                startActivity(intent);
            }
        });

    }
}
