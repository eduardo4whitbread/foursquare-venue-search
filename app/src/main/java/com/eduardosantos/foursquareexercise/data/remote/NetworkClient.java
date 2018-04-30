package com.eduardosantos.foursquareexercise.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.eduardosantos.foursquareexercise.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    private static final String BASE_URL = "https://api.foursquare.com/v2/";

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    @Inject
    public NetworkClient() {
        okHttpClient = getBaseHttpClientBuilder().build();
        retrofit = getBaseRetrofitBuilder().client(okHttpClient).build();

    }

    private Retrofit.Builder getBaseRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    private OkHttpClient.Builder getBaseHttpClientBuilder() {
        OkHttpClient.Builder builder =
                new OkHttpClient().newBuilder()
                        .readTimeout(10, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(new NetworkClientInterceptor());

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(getInterceptorForDebugging());
        }

        return builder;
    }

    private HttpLoggingInterceptor getInterceptorForDebugging() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    @VisibleForTesting
    public void useMockApiWithResponseCode(@NonNull Context context, int responseCode, String jsonFile) {
        okHttpClient = getBaseHttpClientBuilder()
                .addInterceptor(new OfflineMockInterceptor(context, responseCode, jsonFile))
                .build();
        retrofit = getBaseRetrofitBuilder()
                .client(okHttpClient)
                .build();
    }

    private class NetworkClientInterceptor implements Interceptor {

        public NetworkClientInterceptor() {
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("client_id", BuildConfig.CLIENT_ID)
                    .addQueryParameter("client_secret", BuildConfig.CLIENT_SECRET)
                    .addQueryParameter("v", BuildConfig.CLIENT_VERSION)
                    .build();

            Request.Builder requestBuilder = original.newBuilder().url(url);
            return chain.proceed(requestBuilder.build());
        }
    }

}
