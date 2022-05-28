package com.example.javaproject.thread;

import com.example.javaproject.entity.counter.RequestCounter;

public class RequestCounterThread extends Thread {
    public RequestCounterThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        RequestCounter.inc();
    }
}
