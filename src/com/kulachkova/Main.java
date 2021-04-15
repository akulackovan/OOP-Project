package com.kulachkova;

import com.kulachkova.ServiceSecond.JavaJson;
import com.kulachkova.ServiceThree.Unloading;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        JavaJson jsonJava = new JavaJson();
        Unloading unloading = new Unloading(jsonJava.runServiceOne());
        jsonJava.write(unloading);
        //unloading.report();
    }
}
