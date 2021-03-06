package com.swift.mgw.tcp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@SuppressWarnings("unused")
@Data
@AllArgsConstructor
public class Message implements Serializable {

    private String uetr;
    private String body;

    @Override
    public String toString() {
        return "Message {uetr='" + uetr + ", body = '" + body + "}";
    }
}
