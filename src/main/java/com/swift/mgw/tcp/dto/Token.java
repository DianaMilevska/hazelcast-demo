package com.swift.mgw.tcp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@SuppressWarnings("unused")
@Data
@AllArgsConstructor
public class Token implements Serializable {
    private String key;
    private Message message;

    @Override
    public String toString() {
        return "Message {key='" + key + ", message = '" + message + "}";
    }
}
