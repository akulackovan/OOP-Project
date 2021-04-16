package com.kulachkova;

import com.kulachkova.ServiceSecond.JavaJson;
import com.kulachkova.ServiceThree.Unloading;

import java.util.concurrent.ExecutionException;


public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        JavaJson jsonJava = new JavaJson();
        Unloading unloading = new Unloading(jsonJava.runServiceOne(30));
        jsonJava.write(unloading);
        unloading.report();
    }
}
