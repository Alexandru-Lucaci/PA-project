package com.example.demo.client;

import static com.example.demo.DemoApplication.initDatabaseConnectionPool;

public class Main1 {
    public static void main(String[] args) {
        initDatabaseConnectionPool();

            new Launch();
    }
}
