package com.madfooat.weather.android.demo.app.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.madfooat.weather.android.demo.app.R;
import com.madfooat.weather.android.demo.app.adapter.CitiesAdapter;
import com.madfooat.weather.android.demo.app.adapter.CountriesAdapter;
import com.madfooat.weather.android.demo.app.beans.City;
import com.madfooat.weather.android.demo.app.beans.Country;
import com.madfooat.weather.android.demo.app.common.Constant;
import com.madfooat.weather.android.demo.app.service.DataService;

import java.util.ArrayList;
import java.util.List;


/**
 * @author gbarham
 */
public class MainActivity extends Activity {

    private Spinner countiesSpinner, citiesSpinner;
    private List<Country> countriesList = new ArrayList<Country>();
    private List<City> citiesList = new ArrayList<City>();
    private DataService dataService = null;
    private Dialog loadingDialog = null;
    private CountriesAdapter countriesAdapter = null;
    private CitiesAdapter citiesAdapter = null;
    private RelativeLayout mainRL = null;
    private TextView cityWeatherTV = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initServices();
        initActivity();
        setupListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //TODO: save data[cities/countries] to SQLiteDB after the first fitch
        //TODO: try to fetch data from an SQLite DB if is empty fill data from provider
        //TODO: get client geoLocation and set the current location as the default values on start
        //TODO: allow client to add cities to shortcut list of favorite list
        if (this.countriesList.isEmpty()) {
            fillViews();
        }
    }

    private void initServices() {
        this.dataService = new DataService();
    }

    private void initActivity() {

        // views
        this.countiesSpinner = findViewById(R.id.countriesSpinner);
        this.citiesSpinner = findViewById(R.id.citiesSpinner);
        this.mainRL = findViewById(R.id.mainRL);
        this.cityWeatherTV = findViewById(R.id.cityWeatherTV);

        // adapters
        countriesAdapter = new CountriesAdapter(MainActivity.this, R.layout.data_spinner_row, countriesList);
        citiesAdapter = new CitiesAdapter(MainActivity.this, R.layout.data_spinner_row, citiesList);
        countiesSpinner.setAdapter(countriesAdapter);
        citiesSpinner.setAdapter(citiesAdapter);
    }

    private void setupListeners() {
        countiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String countryIso2 = countriesList.get(position).getCountry_code();
                String url = Constant.API_URLS.GET_CITIES_URL + countryIso2;
                dataService.getGeoLocationData(url, City.class, citiesAdapter, citiesList, loadingDialog, null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dataService.getCityWeather(MainActivity.this, citiesList.get(position), cityWeatherTV, mainRL);
                Toast.makeText(MainActivity.this, citiesList.get(position).getLabel(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fillViews() {
        showLoadingDialog();
        dataService.getGeoLocationData(Constant.API_URLS.GET_COUNTRIES_URL, Country.class, countriesAdapter, countriesList, loadingDialog, citiesSpinner);
    }

    private void showLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.show();
            return;
        }

        loadingDialog = new Dialog(MainActivity.this);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.data_spinner_row);

        TextView contentTV = loadingDialog.findViewById(R.id.dataTV);
        contentTV.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        contentTV.setTextSize(20);
        contentTV.setText("please wait ..");
        loadingDialog.show();
    }
}
