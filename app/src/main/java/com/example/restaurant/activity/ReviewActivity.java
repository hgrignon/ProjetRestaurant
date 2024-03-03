package com.example.restaurant.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.activity.adapter.GalleryAdapter;
import com.example.restaurant.activity.adapter.ReviewAdapter;
import com.example.restaurant.data.Review;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    public RestaurantServices restaurantServices = new RestaurantServicesImpl();
    final int THUMBSIZE = 64;
    Context context;
    ArrayList<Uri> listImage= new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_review);
        String id = getIntent().getStringExtra("idRestaurant");

        ListView listReview = (ListView) findViewById(R.id.ListeAvis);
        ArrayList<Review> reviews = restaurantServices.parseReviews(id);
        ReviewAdapter adapter = new ReviewAdapter(this, reviews);
        listReview.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if(extras.containsKey("imagePath")) {
            String path = getIntent().getStringExtra("imagePath");
            Uri uri = Uri.parse(path);
            listImage.add(uri);
        }
        RecyclerView imageListView = (RecyclerView) findViewById(R.id.Gallery);
        GalleryAdapter galleryAdapter = new GalleryAdapter(this, listImage);
        imageListView.setAdapter(galleryAdapter);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        imageListView.setLayoutManager(layoutManager);
        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CameraActivity.class);
                context.startActivity(intent);
            }
        });

        findViewById(R.id.envoiAvis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText auteurId = (EditText)findViewById(R.id.AuteurAvis);
                String auteur = auteurId.getText().toString();

                EditText avisId = (EditText)findViewById(R.id.AvisDescription);
                String avis = avisId.getText().toString();

                EditText numberId = (EditText)findViewById(R.id.NombreAvis);
                String nb = numberId.getText().toString();


                if(!auteur.isEmpty() && !avis.isEmpty() && !nb.isEmpty()){
                    int nombre = Integer.valueOf(numberId.getText().toString());
                    Review review = new Review(id,auteur,nombre,avis);
                    restaurantServices.addReview(review);
                    auteurId.setText("");
                    avisId.setText("");
                    numberId.setText("");
                } else {
                    Toast.makeText(context,"remplissez tout les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
