package com.example.fineart_ds.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fineart_ds.MainActivity;
import com.example.fineart_ds.R;
import com.felipecsl.gifimageview.library.GifImageView;

import java.io.IOException;
import java.io.InputStream;

public class LoadingActivity extends AppCompatActivity {

    private GifImageView mGImgLoad;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mGImgLoad = findViewById(R.id.mGImgLoad);
        try {
            InputStream is = getAssets().open("loading.gif");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            is.close();
            mGImgLoad.setBytes(bytes);
            mGImgLoad.startAnimation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}
