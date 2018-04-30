package com.eduardosantos.foursquareexercise.data.model.remote;

public class RestResponseVenueCategory {
    private String pluralName;
    private String name;
    private RestResponseVenueCategoryIcon icon;
    private String id;
    private String shortName;
    private boolean primary;

    public String getPluralName() {
        return this.pluralName;
    }

    public void setPluralName(String pluralName) {
        this.pluralName = pluralName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RestResponseVenueCategoryIcon getIcon() {
        return this.icon;
    }

    public void setIcon(RestResponseVenueCategoryIcon icon) {
        this.icon = icon;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public boolean getPrimary() {
        return this.primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}
