package com.example.restaurant.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.restaurant.R;
import com.example.restaurant.activity.adapter.ReviewAdapter;
import com.example.restaurant.data.Review;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    public RestaurantServices restaurantServices = new RestaurantServicesImpl();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_review);
        String id = getIntent().getStringExtra("idRestaurant");

        ListView listReview = (ListView) findViewById(R.id.ListeAvis);
        ArrayList<Review> reviews = restaurantServices.parseReviews(id);
        ReviewAdapter adapter = new ReviewAdapter(this, reviews);
        listReview.setAdapter(adapter);

        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CameraActivity.class);
                context.startActivity(intent);
            }
        });

        findViewById(R.id.photoeditor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoEditorActivity.class);
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
                int nombre = Integer.valueOf(numberId.getText().toString());

                if(!auteur.isEmpty() && !avis.isEmpty() && nombre>0 && nombre<=5){
                    Review review = new Review(id,auteur,nombre,avis);
                    restaurantServices.addReview(review);
                    auteurId.setText("");
                    avisId.setText("");
                    numberId.setText("");
                }
            }
        });

    }
}
