package com.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    private final int id;
    private final Object[] result;

    public Response(@JsonProperty("id") int id,
                    @JsonProperty("result") Object[] result){
        this.id = id;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public Object[] getResult() {
        return result;
    }
}
