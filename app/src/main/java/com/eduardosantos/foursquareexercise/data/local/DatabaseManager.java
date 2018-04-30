package com.eduardosantos.foursquareexercise.data.local;

import com.eduardosantos.foursquareexercise.data.model.local.Venue;

public interface DatabaseManager {
    Object getVenuesFor(String searchText);
    void saveOrUpdateVenue(Venue venue);
}
