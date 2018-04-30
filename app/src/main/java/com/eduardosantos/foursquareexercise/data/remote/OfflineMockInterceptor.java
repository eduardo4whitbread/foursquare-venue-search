package com.eduardosantos.foursquareexercise.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@VisibleForTesting
public class OfflineMockInterceptor implements Interceptor {

    private static final MediaType MEDIA_JSON = MediaType.parse("application/json");

    private final Context context;
    private final int code;
    private final String jsonFile;


    public OfflineMockInterceptor(@NonNull Context context, int code, String jsonFile) {
        this.context = context;
        this.code = code;
        this.jsonFile = jsonFile;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        InputStream stream = context.getAssets().open("mockData/" + jsonFile);
        String json = parseStream(stream);

        return new Response.Builder()
                .body(ResponseBody.create(MEDIA_JSON, json))
                .message("no message needed")
                .request(request)
                .protocol(Protocol.HTTP_2)
                .code(code)
                .build();
    }

    private String parseStream(InputStream stream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            builder.append(line);
        }
        in.close();
        return builder.toString();
    }
}