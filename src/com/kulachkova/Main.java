package com.kulachkova;

import com.kulachkova.ServiceSecond.JavaJson;
import com.kulachkova.ServiceThree.Unloading;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        JavaJson jsonJava = new JavaJson();
        System.out.println("\n\n============================================================\n                   UPLOADING");
        Unloading unloading = new Unloading(jsonJava.runServiceOne());
        Thread.sleep(10000);
        jsonJava.write(unloading.getListAll());
    }
}
