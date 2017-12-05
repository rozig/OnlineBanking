package com.models;

public abstract class Request {
    String requestId;
    String requestName;

    public abstract void verify();
}
