package com.eduardosantos.foursquareexercise.venuelist;

import com.eduardosantos.foursquareexercise.base.BasePresenter;
import com.eduardosantos.foursquareexercise.base.BaseView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmRecyclerViewAdapter;

public interface VenueListContract {
    interface View extends BaseView<Presenter> {
        void showSearchPlaceHolder();
        void showNoResultsPlaceHolder();
        void hidePlaceHolder();
        void showVenueList();
        void hideVenueList();
        void showProgressBar();
        void hideProgressBar();
        void setAdapter(RealmRecyclerViewAdapter adapter);
    }

    interface Presenter extends BasePresenter {
        void searchFor(String searchText);
        void loadResults(String searchText);
    }

}
