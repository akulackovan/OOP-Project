package com.kulachkova;

import com.kulachkova.ServiceSecond.JavaJson;
import com.kulachkova.ServiceThree.Unloading;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        JavaJson jsonJava = new JavaJson();
        jsonJava.runServiceOne();
        System.out.println("\n\n============================================================\n                   UPLOADING");
        Unloading unloading = new Unloading();
        Thread.sleep(1000);
        jsonJava.getServiceThree(unloading.getListAll());
        unloading.getResult();
    }
}
