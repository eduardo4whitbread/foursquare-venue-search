package com.eduardosantos.foursquareexercise.data.local;

import com.eduardosantos.foursquareexercise.data.model.local.Venue;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmDatabaseManager implements DatabaseManager {

    @Override
    public RealmResults getVenuesFor(String searchText) {
        return Realm.getDefaultInstance().
                where(Venue.class).
                equalTo("searchText", searchText).
                findAll();
    }

    @Override
    public void saveOrUpdateVenue(Venue venue) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(venue);
        realm.commitTransaction();
        realm.close();
    }
}
