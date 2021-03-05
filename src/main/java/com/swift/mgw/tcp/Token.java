package com.swift.mgw.tcp;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Token implements Serializable {
    private String key;
    private Message message;

    public Token(String key, Message message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    Message getMessage() {
        return message;
    }

    Token setMessage(Message message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Message {key='" + key + ", message = '" + message + "}";
    }
}
