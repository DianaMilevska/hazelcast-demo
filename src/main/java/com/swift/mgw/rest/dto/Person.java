package com.swift.mgw.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@SuppressWarnings("unused")
@Data
@AllArgsConstructor
public class Person implements Serializable {
    private String name;

    @Override
    public String toString() {
        return "Person {name='" + name + "'}";
    }
}
