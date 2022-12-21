package com.example.j_gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class FullImageActivity extends AppCompatActivity {

    ImageView imageView;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);


        intent = getIntent();

        Uri imgUri = Uri.parse(intent.getStringExtra("img"));

        imageView = findViewById(R.id.fullImagle);

        imageView.setImageURI(imgUri);


    }
}