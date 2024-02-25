package com.example.restaurant.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.R;

import java.io.IOException;

public class FiltreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtre);

        Uri photoUri = getIntent().getParcelableExtra(MediaStore.EXTRA_OUTPUT);
        ImageView image = findViewById(R.id.PhotoPrise);

        if (photoUri != null) {
            image.setImageURI(photoUri);
        }

    }
}
