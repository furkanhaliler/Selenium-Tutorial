package com.example.selenium;

public class Util {

    public static void sleepUntilSeconds(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
