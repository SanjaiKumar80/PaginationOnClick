package com.app.pagination;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import androidx.appcompat.app.AppCompatActivity;

public class Detal extends AppCompatActivity {
    ImageView img;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal);
        img = findViewById(R.id.imageview);
        txt = findViewById(R.id.textid);

        String imageurl = getIntent().getStringExtra("URL");
        String txts = getIntent().getStringExtra("name");
        Glide.with(Detal.this)
                .load(imageurl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img);
        txt.setText(txts);

    }
}