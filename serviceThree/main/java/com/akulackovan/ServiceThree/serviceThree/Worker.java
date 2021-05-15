package com.akulackovan.ServiceThree.serviceThree;

import com.akulackovan.ServiceThree.entity.Ship;
import com.akulackovan.ServiceThree.entity.typeOfCargo;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Worker {
    private final CountDownLatch latch;
    private final ConcurrentLinkedQueue<Ship> ships = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Ship> shipsProcessed = new ConcurrentLinkedQueue<>();
    private final typeOfCargo type;
    private long fine;

    public Worker(int number, List<Ship> ships, typeOfCargo type) throws InterruptedException {
        this.ships.addAll(ships);
        this.type = type;
        fine = 30000 * number;
        ExecutorService executorService = Executors.newFixedThreadPool(number);
        latch = new CountDownLatch(ships.size());
        for (int i = 0; i < number; i++) {
            executorService.submit(unload());
        }
        latch.await();
        executorService.shutdown();
    }

    public long getFine() {
        return fine;
    }

    public Runnable unload() {
        Crane crane = new Crane(type);
        Runnable task = () -> {
            Ship first = null;
            Ship last;
            while (!ships.isEmpty()) {
                last = ships.poll();
                if (last != null) {
                    if (first != null) {
                        makeQueue(first, last);
                    }
                    crane.unloading(last);
                    last.setFine_();
                    synchronized (Worker.class) {
                        fine += last.getFine_();
                    }
                    first = last;
                    shipsProcessed.add(last);
                    latch.countDown();
                }
            }
        };
        return task;
    }

    public synchronized void makeQueue(Ship shipFirst, Ship shipSecond) {
        Timestamp first = shipFirst.getRealTimeEnd_();
        Timestamp second = shipSecond.getRealTimeArrival_();
        long milliseconds = second.getTime() - first.getTime();
        if (milliseconds < 0) {
            milliseconds *= -1;
            int minute = (int) (milliseconds / 1000 / 60 % 60);
            int day = (int) (milliseconds / (24 * 60 * 60 * 1000));
            int hour = (int) (milliseconds / 1000 / 3600 % 60);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(second.getTime());
            cal.add(Calendar.MINUTE, minute);
            cal.add(Calendar.HOUR, hour);
            cal.add(Calendar.DAY_OF_YEAR, day);
            second = new Timestamp(cal.getTime().getTime());
            shipSecond.setWaitTime_(milliseconds);
        }
        shipSecond.setRealTimeBegin_(second);
    }

    public List<Ship> getShips() {
        return Arrays.asList(shipsProcessed.toArray(new Ship[shipsProcessed.size()]));
    }
}
