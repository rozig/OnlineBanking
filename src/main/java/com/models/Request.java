package com.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
    private final long id;
    private final String method;
    private final Object[] params;

    public Request(@JsonProperty("id") long id,
                   @JsonProperty("method") String method,
                   @JsonProperty("params") Object[] params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }


    public long getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public Object[] getParams() {
        return params;
    }
}
