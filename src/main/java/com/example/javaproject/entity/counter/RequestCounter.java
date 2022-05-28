package com.example.javaproject.entity.counter;

public class RequestCounter {
    private static int requests = 0;

    public static synchronized void inc() {
        requests++;
    }

    public static int getRequestsCount() {
        return requests;
    }
}
