package com.kulachkova;

import com.kulachkova.ServisSecond.JavaJson;
import com.kulachkova.ServisThree.Unloading;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        JavaJson jsonJava = new JavaJson();
        jsonJava.runServisOne();
        System.out.println("\n\n============================================================\n                   UPLOADING");
        Unloading unloading = new Unloading();
        Thread.sleep(1000);
        unloading.getResult();
    }
}
