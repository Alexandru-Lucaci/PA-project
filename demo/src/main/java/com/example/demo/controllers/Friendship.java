package com.example.demo.controllers;

import com.example.demo.database.ContDAO;

import java.sql.SQLException;

public class Friendship {

    private int idFirst;
    private int idSecond;
    private String nameFirst;
    private String nameSecond;

    public int getIdFirst() {
        return idFirst;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "idFirst=" + idFirst +
                ", idSecond=" + idSecond +
                ", nameFirst='" + nameFirst + '\'' +
                ", nameSecond='" + nameSecond + '\'' +
                '}';
    }

    public Friendship(int idFirst, int idSecond) {
        this.idFirst = idFirst;
        try {
            nameFirst= new ContDAO().findById(idFirst);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.idSecond = idSecond;
        try {
            nameSecond= new ContDAO().findById(idSecond);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Friendship() {
    }

    public Friendship(int idFirst, int idSecond, String nameFrist, String nameSecond) {
        this.idFirst = idFirst;
        this.idSecond = idSecond;
        this.nameFirst = nameFrist;
        this.nameSecond = nameSecond;
    }

    public void setIdFirst(int idFirst) {
        this.idFirst = idFirst;
    }

    public int getIdSecond() {
        return idSecond;
    }

    public void setIdSecond(int idSecond) {
        this.idSecond = idSecond;
    }

    public String getNameFrist() {
        return nameFirst;
    }

    public void setNameFrist(String nameFrist) {
        this.nameFirst = nameFrist;
    }

    public String getNameSecond() {
        return nameSecond;
    }

    public void setNameSecond(String nameSecond) {
        this.nameSecond = nameSecond;
    }
}
