package com.madfooat.weather.android.demo.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.madfooat.weather.android.demo.app.R;
import com.madfooat.weather.android.demo.app.beans.Country;

import java.util.List;

public class CountriesAdapter extends ArrayAdapter<Country> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<Country> countriesList;
    private final int mResource;

    public CountriesAdapter(Context context, int resource, List<Country> countriesList) {
        super(context, resource, countriesList);

        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mResource = resource;
        this.countriesList = countriesList;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);

        TextView  countryNameTV = view.findViewById(R.id.dataTV);
        Country country = countriesList.get(position);
        countryNameTV.setText(country.getCountry_name() +"  ["+country.getCountry_code()+"]");
        return view;
    }
}