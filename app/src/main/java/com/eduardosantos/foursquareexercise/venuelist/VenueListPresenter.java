package com.eduardosantos.foursquareexercise.venuelist;

import com.eduardosantos.foursquareexercise.FourSquareExerciseApplication;
import com.eduardosantos.foursquareexercise.base.BaseScheduler;
import com.eduardosantos.foursquareexercise.data.local.DatabaseManager;
import com.eduardosantos.foursquareexercise.data.remote.NetworkClient;

import javax.inject.Inject;

import toothpick.Toothpick;

public class VenueListPresenter implements VenueListContract.Presenter {
    @Inject
    NetworkClient networkClient;
    @Inject
    BaseScheduler scheduler;
    @Inject
    DatabaseManager databaseManager;

    private VenueListContract.View view;

    public VenueListPresenter(VenueListContract.View view) {
        this.view = view;
        Toothpick.inject(this, Toothpick.openScope(FourSquareExerciseApplication.APPLICATION_SCOPE));
    }

    @Override
    public void start() {
    }

    @Override
    public void doSearch(String searchText) {

    }

    @Override
    public void loadResults(String searchText) {

    }

}
