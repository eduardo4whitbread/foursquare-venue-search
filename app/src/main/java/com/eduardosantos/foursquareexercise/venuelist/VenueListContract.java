package com.eduardosantos.foursquareexercise.venuelist;

import com.eduardosantos.foursquareexercise.base.BasePresenter;
import com.eduardosantos.foursquareexercise.base.BaseView;

import io.realm.RealmBaseAdapter;

public interface VenueListContract {
    interface View extends BaseView<Presenter> {
        void showSearchPlaceHolder();
        void hideSearchPlaceHolder();
        void showNoResultsPlaceHolder();
        void hideNoResultsPlaceHolder();
        void showVenuesList();
        void hideVenuesList();
        void setAdapter(RealmBaseAdapter adapter);
    }

    interface Presenter extends BasePresenter {
        void doSearch(String searchText);
        void loadResults(String searchText);
    }

}
