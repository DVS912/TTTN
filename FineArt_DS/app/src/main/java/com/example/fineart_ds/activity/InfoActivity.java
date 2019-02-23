package com.example.fineart_ds.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fineart_ds.R;

public class InfoActivity extends AppCompatActivity {

    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        btnCall = findViewById(R.id.buttonCall);
        final TextView txtSDT = findViewById(R.id.textViewSDT);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = txtSDT.getText().toString();
                if(!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }else {
                    Toast.makeText(InfoActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
