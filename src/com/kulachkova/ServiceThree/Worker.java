package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.Ship;
import com.kulachkova.ServiceOne.typeOfCargo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;

public class Worker {

    private ConcurrentLinkedQueue<Ship> ships = new ConcurrentLinkedQueue<>();
    private typeOfCargo type;
    private long fine;
    List<Thread> threads;

    public Worker (int number, List<Ship> ships, typeOfCargo type) throws InterruptedException {
        this.ships.addAll(ships);
        this.type = type;
        fine = 30000 * number;
        threads = new ArrayList<Thread>();
        for (int i = 0; i < number; i++) {
            threads.add(new Thread(work(i)));
        }
        for (Thread thread: threads) {
            thread.start();
        }
        for (Thread thread: threads) {
            thread.join();
        }
    }

    public long getFine () {
        return fine;
    }

    public Runnable work (int i) throws InterruptedException {
        Crane crane = new Crane(type);
        Runnable task = () -> {
            Ship first = null;
            Ship last;
            while (!this.ships.isEmpty()) {
                synchronized (Worker.class) {
                    last = this.ships.poll();
                }
                if (last != null)
                {
                    if (first != null) {
                        queue(first, last);
                    }
                    crane.unloading(last);
                    last.setFine_();
                    fine += last.getFine_();
                    first = last;
                }
            }
        };
        return task;
    }

    public synchronized void queue (Ship shipFirst, Ship shipSecond) {
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

    public List<Ship> getShips () {
        return (List<Ship>) ships;
    }
}
