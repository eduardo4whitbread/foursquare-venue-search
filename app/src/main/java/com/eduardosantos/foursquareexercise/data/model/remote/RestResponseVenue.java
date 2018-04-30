package com.eduardosantos.foursquareexercise.data.model.remote;

public class RestResponseVenue {
    private RestResponseVenuePage venuePage;
    private String name;
    private RestResponseVenueLocation location;
    private String id;
    private RestResponseVenueCategory[] categories;

    public RestResponseVenuePage getVenuePage() {
        return this.venuePage;
    }

    public void setVenuePage(RestResponseVenuePage venuePage) {
        this.venuePage = venuePage;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RestResponseVenueLocation getLocation() {
        return this.location;
    }

    public void setLocation(RestResponseVenueLocation location) {
        this.location = location;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RestResponseVenueCategory[] getCategories() {
        return this.categories;
    }

    public void setCategories(RestResponseVenueCategory[] categories) {
        this.categories = categories;
    }
}
