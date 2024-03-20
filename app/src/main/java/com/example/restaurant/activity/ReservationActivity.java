package com.example.restaurant.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurant.R;
import com.example.restaurant.data.Restaurant;
import com.example.restaurant.services.ReservationService;
import com.example.restaurant.services.ReservationServiceImpl;
import com.example.restaurant.services.RestaurantServices;
import com.example.restaurant.services.RestaurantServicesImpl;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class ReservationActivity extends AppCompatActivity {
    RestaurantServices restaurantServices = new RestaurantServicesImpl();
    ReservationService reservationService = new ReservationServiceImpl();

    String restaurantId;
    Restaurant restaurant;

    String selectedDate;

    ArrayList<Integer> persNumberItems = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    Spinner persNumberDropDown;

    HashMap<String, Integer> daysAndCapacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        restaurantId = getIntent().getStringExtra("restaurantId");
        restaurant = restaurantServices.parseRestaurant(restaurantId);

        daysAndCapacity = reservationService
                .computeCurrentCapacityForEachDays(restaurantId, restaurant.getCapacity());

        setupViews();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    private void setupViews() {
        setupDatePicker();
        setupPersNbPicker();
    }

    // =======================================DATE PICKER===============================================
    private void setupDatePicker() {
        datePickerDialog = new DatePickerDialog();

        sDisableDays();

        sOnDateButtonClicked();
        sOnDateSet();
    }


    private void sDisableDays() {
        List<Calendar> daysFull = new ArrayList<>();
        for (String key : daysAndCapacity.keySet()) {
            if (daysAndCapacity.get(key) >= restaurant.getCapacity()) {
                String[] date = key.split("/");
                daysFull.add(
                        new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]))
                );
            }
        }
        Calendar[] disableDays = new Calendar[] {};
        datePickerDialog.setDisabledDays(daysFull.toArray(disableDays));
    }

    private void sOnDateButtonClicked() {
        (findViewById(R.id.reservation_date_select)).setOnClickListener(
                a -> datePickerDialog.show(getSupportFragmentManager(), "Datepickerdialog")
        );
    }

    private void sOnDateSet() {
        datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            updateSelectedDateAndText(year, month, dayOfMonth);
            updatePersNumberDropDownChoices();
        });
    }
    private void updateSelectedDateAndText(int year, int month, int dayOfMonth) {
        selectedDate = dayOfMonth + "/" + month + "/" + year;
        String selectedDateDisplay = dayOfMonth + "/" + (month+1) + "/" + year;

        ((TextView) findViewById(R.id.reservation_date)).setText(selectedDateDisplay);
    }
    private void updatePersNumberDropDownChoices() {
        persNumberItems.clear();
        for (int i = 1; i < computeCurrentCapacity() + 1; i++)
            persNumberItems.add(i);
        persNumberDropDown.setEnabled(true);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, persNumberItems);
        persNumberDropDown.setAdapter(adapter);
    }
    private int computeCurrentCapacity() {
        if (daysAndCapacity.containsKey(selectedDate))
            return restaurant.getCapacity() - daysAndCapacity.get(selectedDate);
        else
            return restaurant.getCapacity();
    }

    // =======================================NB PERSON PICKER===============================================
    private void setupPersNbPicker() {
        persNumberDropDown = findViewById(R.id.spinner);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, persNumberItems);
        persNumberDropDown.setAdapter(adapter);
        persNumberDropDown.setEnabled(false);
        sOnClickPersDropDown();
    }

    private void sOnClickPersDropDown() {
        ((Button) findViewById(R.id.reservation_confirm)).setOnClickListener(b -> {
            if ( selectedDate != null && persNumberDropDown.getSelectedItemId() != AdapterView.INVALID_ROW_ID ) {
                reservationService.addReservation(
                        restaurantId,
                        selectedDate,
                        persNumberItems.get(Integer.parseInt(String.valueOf(persNumberDropDown.getSelectedItemId()))),
                        ((TextView) findViewById(R.id.reservation_name)).getText().toString());

                Toast toast = Toast.makeText(this, "Reservé", Toast.LENGTH_SHORT);
                toast.show();

                Intent intentMain = new Intent(this, RestaurantDetailsActivity.class);
                intentMain.putExtra("idObject", restaurantId);
                this.startActivity(intentMain);
            }
            else {
                Toast toast = Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    // =======================================MENUS===============================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_list_resto) {
            Intent intentMain = new Intent(this , MainActivity.class);
            this.startActivity(intentMain);
            return true;
        } else
        if (item.getItemId() == R.id.menu_map) {
            Intent intentMain = new Intent(this , MapActivity.class);
            this.startActivity(intentMain);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}