package com.swift.mgw.rest;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Person implements Serializable {

    private String name;

    Person(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    Person setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Person {name='" + name + "'}";
    }
}
