package com.eduardosantos.foursquareexercise.data.model.remote;

public class RestResponseMetadata {
    private int code;
    private String requestId;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
