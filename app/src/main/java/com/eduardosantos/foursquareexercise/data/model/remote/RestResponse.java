package com.eduardosantos.foursquareexercise.data.model.remote;

public class RestResponse {
    private RestResponseMetadata meta;
    private RestResponseData response;

    public RestResponseMetadata getMeta() {
        return this.meta;
    }

    public void setMeta(RestResponseMetadata meta) {
        this.meta = meta;
    }

    public RestResponseData getResponse() {
        return this.response;
    }

    public void setResponse(RestResponseData response) {
        this.response = response;
    }
}
