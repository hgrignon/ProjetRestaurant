package com.example.restaurant.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.restaurant.R;

public class FiltreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtre);

        byte[] bytes = getIntent().getByteArrayExtra("image");


        if (bytes != null) {
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            ImageView imageView = findViewById(R.id.PhotoPrise);
            imageView.setImageBitmap(imageBitmap);
        }

    }
}
