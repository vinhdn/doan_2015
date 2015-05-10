/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package bk.vinhdo.taxiads.models;

import org.json.JSONException;
import org.json.JSONObject;

import bk.vinhdo.taxiads.utils.Keys;

/**
 * Created by vinhdo on 2/27/15.
 */
public class Location {

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCrossStreet() {
        return crossStreet;
    }

    public void setCrossStreet(String crossStreet) {
        this.crossStreet = crossStreet;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String address;
    private String crossStreet;
    private double lat;
    private double lng;
    private String city;

    public Location(JSONObject joL) throws JSONException {
        if(!joL.isNull(Keys.address))
        address = joL.getString(Keys.address);
        if(!joL.isNull(Keys.crossStreet))
            crossStreet = joL.getString(Keys.crossStreet);
        lat = joL.getDouble(Keys.lat);
        lng = joL.getDouble(Keys.lng);
    }

}
