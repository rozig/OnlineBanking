package com.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Request {
    @Id
    @GeneratedValue
    @Column(name = "Id", updatable = false, nullable = false)
    private String requestId;

    @Column(name = "request_name", updatable = true, nullable = false)
    private String requestName;

    public abstract void verify();

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }
}
