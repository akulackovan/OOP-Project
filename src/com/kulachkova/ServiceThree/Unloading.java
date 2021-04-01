package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.Ship;
import com.kulachkova.ServiceOne.typeOfCargo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Phaser;

public class Unloading {

    private List<Ship> looseList;
    private List<Ship> liquidList;
    private List<Ship> containerList;
    private long fine = 0;
    private int numberOfShips = 0;
    private int numberOfShipsInQueue = 0;
    private int numberOfCrane = 0;
    private long time = 0;
    private long timeMin = 0;
    private long timeMax = 0;
    private int maxDelay = 0;
    private int allDelay = 0;


    public Unloading (List<Ship> ships) throws InterruptedException {
        ArrivalOfShips arrivalOfShips = new ArrivalOfShips(ships);
        looseList = arrivalOfShips.getLoose();
        liquidList = arrivalOfShips.getLiquid();
        containerList = arrivalOfShips.getContainer();
        int loose = findOptimal(typeOfCargo.LOOSE, looseList);
        int liquid = findOptimal(typeOfCargo.LOOSE, looseList);
        int container = findOptimal(typeOfCargo.LOOSE, looseList);
        System.out.println(loose);
        System.out.println(liquid);
        System.out.println(container);
        Thread.sleep(5000);
        Worker worker = new Worker(loose, looseList, typeOfCargo.LOOSE);
        Worker worker1 = new Worker(liquid, liquidList, typeOfCargo.LIQUID);
        Worker worker2 = new Worker(container, containerList, typeOfCargo.CONTAINER);
        Thread.sleep(2000);
        looseList = worker.getShips();
        liquidList = worker1.getShips();
        containerList = worker2.getShips();
        looseList.sort(Comparator.comparing(Ship::getRealTimeArrival_));
        liquidList.sort(Comparator.comparing(Ship::getRealTimeArrival_));
        containerList.sort(Comparator.comparing(Ship::getRealTimeArrival_));
    }


    public int findOptimal (typeOfCargo type, List<Ship> ships) throws InterruptedException {
        long fine = 0;
        for (int i = 1; i < ships.size(); i++) {
            Worker worker = new Worker(i, ships, type);
            Thread.sleep(500);
            if (i == 1) {
                fine = worker.getFine();
            } else if (fine < worker.getFine()) {
                return i - 1;
            }
        }
        return ships.size();
    }


    public List<Ship> getListAll () {
        List<Ship> all = new ArrayList<>();
        all.addAll(0, looseList);
        all.addAll(looseList.size(), liquidList);
        all.addAll(liquidList.size(), containerList);
        return all;
    }

    public String timeUploading (Ship ship) {
        Timestamp need = ship.getRealTimeBegin_();
        Timestamp read = ship.getRealTimeEnd_();
        long milliseconds = read.getTime() - need.getTime();
        return convertTime(milliseconds);
    }

    public String convertTime (long time) {
        int day = (int) (time / (24 * 60 * 60 * 1000));
        int hour = (int) (time / 1000 / 3600 % 24);
        int min = (int) (time / 1000 / 60 % 60);
        return String.format("%02d:%02d:%02d", day, hour, min);
    }


}
