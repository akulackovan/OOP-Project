package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.Ship;
import com.kulachkova.ServiceOne.typeOfCargo;
import jdk.jshell.execution.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Unloading {

    private List<Ship> looseList;
    private List<Ship> liquidList;
    private List<Ship> containerList;
    private long fine;
    private int numberOfShips = 0;
    private int numberOfCraneLoose;
    private int numberOfCraneLiquid;
    private int numberOfCraneContainer;
    private long time = 0;
    private int maxDelay = 0;
    private int allDelay = 0;

    public Unloading (List<Ship> ships) throws InterruptedException {
        ArrivalOfShips arrivalOfShips = new ArrivalOfShips(ships);
        looseList = arrivalOfShips.getLoose();
        liquidList = arrivalOfShips.getLiquid();
        containerList = arrivalOfShips.getContainer();
        working("Loose", typeOfCargo.LOOSE, looseList);
        working("Liquid", typeOfCargo.LIQUID, liquidList);
        working("Container", typeOfCargo.CONTAINER, containerList);
        //Thread.sleep(10);
        //fine = worker.getFine() + worker1.getFine() + worker2.getFine() + (loose + container + liquid) * 30000;
        //numberOfCraneLoose = loose;
        //numberOfCraneLiquid = liquid;
        //numberOfCraneContainer = container;
        //looseList = worker.getShips();
        //liquidList = worker1.getShips();
        //containerList = worker2.getShips();
        numberOfShips += looseList.size() + liquidList.size() + containerList.size();
    }

    public List<Ship> working (String typeString, typeOfCargo type, List<Ship> ships) throws InterruptedException {
        System.out.println(typeString);
        int number = findOptimal(type, ships);
        Worker worker = new Worker(number, ships, type);
        //ships = worker.getShips();
        return ships;
    }

    public int findOptimal (typeOfCargo type, List<Ship> ships) throws InterruptedException {
        long fine = 0;
        Worker worker;
        for (int i = 1; i < ships.size(); i++) {
            worker = new Worker(i, ships, type);
            System.out.println("Crane " + i + " Fine " + worker.getFine());
            if (i != 1 && fine < worker.getFine()) {
                return i - 1;
            }
            fine = worker.getFine();
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

    public void report () {
        List<Ship> ships = getListAll();
        for (Ship ship : ships) {
            System.out.println("\nName: " + ship.getName_() + "\nTime arrive: " + ship.getRealTimeArrival_()
                    + "\nTime wait: " + ship.getWaitString() + "\nTime real begin: "
                    + ship.getRealTimeBegin_() + "\nTime uploading: " + ship.getUploadingTime());
        }
        System.out.println(
                "\n\nREPORT\nNumber of ships: " + ships.size() + "\nAll delay: " + getAllDelay() + "\nMax delay: "
                        + getMaxDelay() + "\nNumber of crane loose: " + numberOfCraneLoose + "\nNumber of crane liquid: "
                        + numberOfCraneLiquid + "\nNumber of crane container: " + numberOfCraneContainer + "\nFine: " + fine + "\nAverage waiting time: " + getTimeWait());
    }

    public long getFine () {
        return fine;
    }

    public int getNumberOfShips () {
        return numberOfShips;
    }

    public int getNumberOfCraneLoose () {
        return numberOfCraneLoose;
    }

    public int getNumberOfCraneLiquid () {
        return numberOfCraneLiquid;
    }

    public int getNumberOfCraneContainer () {
        return numberOfCraneContainer;
    }

    public int getMaxDelay () {
        List<Ship> ships = getListAll();
        for (Ship ship : ships) {
            if (ship.getDelay_() > maxDelay) {
                maxDelay = ship.getDelay_();
            }
        }
        return maxDelay;
    }

    public int getAllDelay () {
        List<Ship> ships = getListAll();
        for (Ship ship : ships) {
            allDelay += ship.getDelay_();
        }
        return allDelay / ships.size();
    }

    public String getTimeWait () {
        List<Ship> ships = getListAll();
        for (Ship ship : ships) {
            time += ship.getWaitTime_();
        }
        time /= ships.size();
        int minute = (int) (time / 1000 / 60 % 60);
        int day = (int) (time / (24 * 60 * 60 * 1000));
        int hour = (int) (time / 1000 / 3600 % 60);
        return String.format("%02d:%02d:%02d", day, hour, minute);
    }
}
