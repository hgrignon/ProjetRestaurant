package com.example.restaurant.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.example.restaurant.R;
import com.example.restaurant.data.Review;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;

public class ReviewActivity extends AppCompatActivity {

    public RestaurantServices restaurantServices = new RestaurantServicesImpl();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        String id = getIntent().getStringExtra("idRestaurant");
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
