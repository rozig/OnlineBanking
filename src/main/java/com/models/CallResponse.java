package com.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CallResponse {
    private final int id;
    private final Object[] result;

    public CallResponse(@JsonProperty("id") int id,
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
