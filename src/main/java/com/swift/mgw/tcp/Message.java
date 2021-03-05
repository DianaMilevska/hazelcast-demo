package com.swift.mgw.tcp;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Message implements Serializable {

    private String uetr;
    private String body;

    public Message(String uetr, String body) {
        this.uetr = uetr;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public Message setBody(String body) {
        this.body = body;
        return this;
    }

    public String getName() {
        return uetr;
    }

    public Message setName(String uetr) {
        this.uetr = uetr;
        return this;
    }

    @Override
    public String toString() {
        return "Message {uetr='" + uetr + ", body = '" + body + "}";
    }
}
