package com.kulachkova;

import com.kulachkova.ServiceSecond.JavaJson;
import com.kulachkova.ServiceThree.Unloading;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        JavaJson jsonJava = new JavaJson();
        jsonJava.runServiceOne(50);
        Unloading unloading = new Unloading(jsonJava.readServiceOne());
        jsonJava.writeServiceThree(unloading);
        unloading.report();
    }
}
