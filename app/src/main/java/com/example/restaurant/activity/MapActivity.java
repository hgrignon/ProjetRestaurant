package com.example.restaurant.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.R;
import com.example.restaurant.data.Restaurant;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;
import com.wdullaer.materialdatetimepicker.BuildConfig;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;

// https://osmdroid.github.io/osmdroid/
public class MapActivity extends AppCompatActivity {
    private MapView map = null;
    private LinearLayout bot_menu_layout;
    RestaurantServices restaurantServices = new RestaurantServicesImpl();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        setupToolBar();

        setupMapConfigs();
        setupMarkerClickMenu();
        setupMapMarkers();
    }

    private void setupToolBar() {
        setSupportActionBar(findViewById(R.id.my_toolbar));
    }

    private void setupMapConfigs() {
        IConfigurationProvider provider = Configuration.getInstance();
        provider.setUserAgentValue(BuildConfig.APPLICATION_ID);

        map = findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);

        IMapController mapController = map.getController();
        mapController.setZoom(15.0);
        mapController.setCenter(new GeoPoint(43.60426, 1.44367));
    }

    private void setupMarkerClickMenu() {
        bot_menu_layout = findViewById(R.id.bot_menu);
        bot_menu_layout.setVisibility(View.INVISIBLE);

    }

    private void setupMapMarkers() {
        List<Restaurant> restaurantList = restaurantServices.parseRestaurants();
        for (Restaurant currentRestaurant : restaurantList) {
            if (currentRestaurant.getPosition() != null) {
                Marker marker = new Marker(map);
                marker.setPosition(currentRestaurant.getPosition());
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                setOnMarkerClick(marker, currentRestaurant);
                map.getOverlays().add(marker);
            }
        }
    }

    private void setOnMarkerClick(Marker marker, Restaurant currentRestaurant) {
        marker.setOnMarkerClickListener((m, mv) -> {
            ((TextView) findViewById(R.id.bot_menu_text))
                    .setText(currentRestaurant.getNom());
            setOnClickDetailButton(currentRestaurant);
            bot_menu_layout.setVisibility(View.VISIBLE);
            return true;
        });
    }

    private void setOnClickDetailButton(Restaurant currentRestaurant) {
        findViewById(R.id.bot_button_details)
                .setOnClickListener(a -> {
                    Intent intentMain = new Intent(MapActivity.this,
                            RestaurantDetailsActivity.class);
                    intentMain.putExtra("idObject", currentRestaurant.getObjectId());
                    MapActivity.this.startActivity(intentMain);
                });
    }

    public void onResume(){
        super.onResume();
        if (map != null)
            map.onResume();
    }

    public void onPause(){
        super.onPause();
        if (map != null)
            map.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_list_resto) {
            Intent intentMain = new Intent(MapActivity.this , MainActivity.class);
            MapActivity.this.startActivity(intentMain);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
