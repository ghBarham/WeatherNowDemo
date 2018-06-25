package com.madfooat.weather.android.demo.app.beans;

import java.io.Serializable;

public class Country implements Serializable {

    private String country_code;
    private String country_name;


    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "country_code='" + country_code + '\'' +
                ", country_name='" + country_name + '\'' +
                '}';
    }
}
