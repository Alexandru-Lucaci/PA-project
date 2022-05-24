package com.example.demo.controllers;

import java.util.Date;

public class Person {
    private final int id;
    private final String name;
    private final String password;
    private  Date created_at;

    public Person(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password=password;
    }
    public Person(int id, String name, String password,Date created_at) {
        this.id = id;
        this.name = name;
        this.password=password;
        this.created_at=created_at;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }



}
