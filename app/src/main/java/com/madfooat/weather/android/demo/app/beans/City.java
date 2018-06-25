package com.madfooat.weather.android.demo.app.beans;

import java.io.Serializable;

public class City implements Serializable {

    private String id;
    private String label;
    private String value;
    private String lat;
    private String lon;
    private String poi_name;
    private String parent_id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getPoi_name() {
        return poi_name;
    }

    public void setPoi_name(String poi_name) {
        this.poi_name = poi_name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", value='" + value + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", poi_name='" + poi_name + '\'' +
                ", parent_id='" + parent_id + '\'' +
                '}';
    }
}
