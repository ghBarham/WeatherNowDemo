package com.madfooat.weather.android.demo.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.madfooat.weather.android.demo.app.R;
import com.madfooat.weather.android.demo.app.beans.City;
import com.madfooat.weather.android.demo.app.beans.Country;

import java.util.List;

public class CitiesAdapter extends ArrayAdapter<City> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<City> countriesList;
    private final int mResource;

    public CitiesAdapter(Context context, int resource, List<City> countriesList) {
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

        TextView  cityNameTV = view.findViewById(R.id.dataTV);
        City city = countriesList.get(position);
        cityNameTV.setText(city.getLabel());
        return view;
    }
}