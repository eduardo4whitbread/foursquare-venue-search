package com.eduardosantos.foursquareexercise.data.model.local;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Venue extends RealmObject {

    @PrimaryKey
    private String id;
    @Required
    private String searchText;
    private String name;
    private String address;
    private RealmList<String> categoryIcons;

    public Venue() {
    }

    public Venue(String id, String searchText, String name, String address, RealmList<String> categoryIcons) {
        this.id = id;
        this.searchText = searchText;
        this.name = name;
        this.address = address;
        this.categoryIcons = categoryIcons;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RealmList<String> getCategoryIcons() {
        return categoryIcons;
    }

    public void setCategoryIcons(RealmList<String> categoryIcons) {
        this.categoryIcons = categoryIcons;
    }
}
