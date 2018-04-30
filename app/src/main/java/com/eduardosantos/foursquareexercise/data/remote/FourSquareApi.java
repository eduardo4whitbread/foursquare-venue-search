package com.eduardosantos.foursquareexercise.data.remote;

import com.eduardosantos.foursquareexercise.data.model.remote.RestResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FourSquareApi {
    @GET("venues/search")
    Single<RestResponse> searchForVenues(
            @Query(value = "near", encoded = true) String nearQueryText,
            @Query("limit") int resultsLimit);//hardcap of 50 items imposed by api
}
