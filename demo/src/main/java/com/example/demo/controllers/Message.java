package com.example.demo.controllers;

import java.util.Date;

public class Message {
    private int id;
    private int  idSender;
    private int idReciever;
    private String message;

    public int getId() {
        return id;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public int getIdReciever() {
        return idReciever;
    }

    public void setIdReciever(int idReciever) {
        this.idReciever = idReciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    private Date createdAt;

    public Message(int id, int idSender, int idReciever, String message, Date createdAt) {
        this.id = id;
        this.idSender = idSender;
        this.idReciever = idReciever;
        this.message = message;
        this.createdAt = createdAt;
    }
}
