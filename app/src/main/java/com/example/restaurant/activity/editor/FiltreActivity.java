package com.example.restaurant.activity.editor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.restaurant.R;

import java.io.IOException;

public class FiltreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtre);

        String path = getIntent().getStringExtra("imagePath");
        Uri uri = Uri.parse(path);
        Log.d("uri",uri.getPath());


        if (path != null) {
            ImageView imageView = findViewById(R.id.PhotoPrise);
            // Charger l'image à partir du chemin absolu et l'afficher dans l'ImageView
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Bitmap bitmap2 = bitmap.copy(bitmap.getConfig(), true);
            imageView.setImageBitmap(bitmap2);
        }

    }
}
