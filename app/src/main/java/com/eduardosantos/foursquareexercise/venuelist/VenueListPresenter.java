package com.eduardosantos.foursquareexercise.venuelist;

import com.eduardosantos.foursquareexercise.FourSquareExerciseApplication;
import com.eduardosantos.foursquareexercise.base.BaseScheduler;
import com.eduardosantos.foursquareexercise.data.local.DatabaseManager;
import com.eduardosantos.foursquareexercise.data.model.local.Venue;
import com.eduardosantos.foursquareexercise.data.model.remote.RestResponse;
import com.eduardosantos.foursquareexercise.data.model.remote.RestResponseVenue;
import com.eduardosantos.foursquareexercise.data.remote.FourSquareApi;
import com.eduardosantos.foursquareexercise.data.remote.NetworkClient;
import com.eduardosantos.foursquareexercise.data.utils.RemoteToLocalMapper;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.realm.OrderedRealmCollection;
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
        //no-op
    }

    @Override
    public void searchFor(final String searchText) {
        view.hidePlaceHolder();
        view.hideVenueList();
        view.showProgressBar();


        FourSquareApi searchApi = networkClient.getRetrofit().create(FourSquareApi.class);
        Single<RestResponse> searchResultsObservable =
                searchApi.searchForVenues(searchText, 50);
        searchResultsObservable.subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<RestResponse>() {
                    @Override
                    public void onSuccess(RestResponse searchRestResponse) {
                        RestResponseVenue venues[] = searchRestResponse.getResponse().getVenues();
                        for (int i = 0; i < venues.length; i++) {
                            RestResponseVenue responseVenue = venues[i];
                            Venue venue = RemoteToLocalMapper.from(responseVenue, searchText);
                            databaseManager.saveOrUpdateVenue(venue);
                        }

                        loadResults(searchText);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideProgressBar();
                        view.showNoResultsPlaceHolder();
                    }
                });


    }

    @Override
    public void loadResults(String searchText) {
        view.hidePlaceHolder();
        view.hideProgressBar();
        view.showVenueList();

        VenueAdapter venueAdapter = new VenueAdapter(
                (OrderedRealmCollection<Venue>) databaseManager.getVenuesFor(searchText),
                true,
                true);
        view.setAdapter(venueAdapter);
    }

}
