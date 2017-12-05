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
    String requestId;

    @Column(name = "request_name", updatable = true, nullable = false)
    String requestName;

    public abstract void verify();
}
