package com.example.demo.client;

import static com.example.demo.DemoApplication.initDatabaseConnectionPool;

public class Main {
    public static void main(String[] args) {
        initDatabaseConnectionPool();

            new Launch();
    }
}
