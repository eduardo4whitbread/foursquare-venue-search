package com.eduardosantos.foursquareexercise.data.model.remote;

public class RestResponseVenueLocationLabeledLatLng {
    private double lng;
    private String label;
    private double lat;

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
