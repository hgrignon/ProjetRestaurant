package com.example.restaurant.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.activity.adapter.GalleryAdapter;
import com.example.restaurant.activity.adapter.ReviewAdapter;
import com.example.restaurant.data.Review;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    public RestaurantServices restaurantServices = new RestaurantServicesImpl();
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



        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CameraActivity.class);
                intent.putExtra("restauId", id);
                launchCameraActivity(intent);            }
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
                    Review review = null;
                    try {
                        review = new Review(id,auteur,nombre,avis,getImagesFromURI(listImage));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    restaurantServices.addReview(review);
                    auteurId.setText("");
                    avisId.setText("");
                    numberId.setText("");
                    listImage.clear();
                } else {
                    Toast.makeText(context,"remplissez tout les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public ArrayList<byte[]> getImagesFromURI(ArrayList<Uri> listImage) throws IOException {
        ArrayList<byte[]> listByteImage = new ArrayList<>();
        for(Uri uri : listImage){
            InputStream iStream =   getContentResolver().openInputStream(uri);
            byte[] inputData = getBytes(iStream);
            listByteImage.add(inputData);
        }
        return listByteImage;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        if (extras != null && extras.containsKey("imagePath")) {
                            String path = extras.getString("imagePath");
                            Uri uri = Uri.parse(path);
                            listImage.add(uri);

                            // Update RecyclerView with the new image
                            RecyclerView imageListView = findViewById(R.id.GalleryPhotoInReview);
                            GalleryAdapter galleryAdapter = new GalleryAdapter(context, listImage);
                            imageListView.setAdapter(galleryAdapter);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                            imageListView.setLayoutManager(layoutManager);
                            Log.d("liste liste", listImage.toString());
                        }
                    }
                }
            });

    private void launchCameraActivity(Intent intent) {
        startActivityIntent.launch(intent);
    }

}
