package com.eduardosantos.foursquareexercise.data.model.remote;

public class RestResponseData {
    private RestResponseVenue[] venues;

    public RestResponseVenue[] getVenues() {
        return this.venues;
    }

    public void setVenues(RestResponseVenue[] venues) {
        this.venues = venues;
    }
}
