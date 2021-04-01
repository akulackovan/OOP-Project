package com.kulachkova.ServiceOne;

import java.sql.Timestamp;
import java.util.Calendar;

public class Ship {

    private String name_;
    private Timestamp timeEnd_;
    private Timestamp realTimeEnd_;
    private Timestamp timeBegin_;
    private Timestamp realTimeArrival_;
    private Timestamp realTimeBegin_;
    private long waitTime_;
    private Cargo cargo_;
    private long fine_;
    private boolean uploading;
    private int delay_;


    public Ship (String name, Timestamp timeBegin, Cargo cargo) {
        name_ = name;
        cargo_ = cargo;
        timeBegin_ = timeBegin;
        timeEnd_ = stay(timeBegin);
        fine_ = 0;
        waitTime_ = 0;
        uploading = false;
    }

    public Timestamp stay (Timestamp timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        int timeInHours = 0;
        switch (cargo_.type_) {
            case LOOSE -> timeInHours = 1;
            case LIQUID -> timeInHours = 2;
            case CONTAINER -> timeInHours = 3;
        }
        cal.add(Calendar.HOUR, cargo_.number_ * timeInHours);
        timestamp = new Timestamp(cal.getTime().getTime());
        return timestamp;
    }

    public String toString () {
        return "Name: " + name_ + "\nCargo type: " + cargo_.name_ + "\nCargo number: " + cargo_.number_ + "\nTime arrival: "
                + timeBegin_ + "\nTime departure: " + timeEnd_;
    }

    public String getUploadingTime () {
        int timeInHours = 0;
        switch (cargo_.type_) {
            case LOOSE -> timeInHours = 1;
            case LIQUID -> timeInHours = 2;
            case CONTAINER -> timeInHours = 3;
        }
        int time = cargo_.number_ * timeInHours;
        int day = (int) (time / (24));
        int hour = (int) (time % 24);
        return String.format("%02d:%02d:00", day, hour);
    }

    public String getName_ () {
        return name_;
    }

    public void setName_ (String name_) {
        this.name_ = name_;
    }

    public Timestamp getTimeEnd_ () {
        return timeEnd_;
    }

    public void setTimeEnd_ (Timestamp timeEnd_) {
        this.timeEnd_ = timeEnd_;
    }

    public Timestamp getRealTimeEnd_ () {
        return realTimeEnd_;
    }

    public void setRealTimeEnd_ (Timestamp realTimeEnd_) {
        this.realTimeEnd_ = realTimeEnd_;
    }

    public Timestamp getTimeBegin_ () {
        return timeBegin_;
    }

    public void setTimeBegin_ (Timestamp timeBegin_) {
        this.timeBegin_ = timeBegin_;
    }

    public Timestamp getRealTimeArrival_ () {
        return realTimeArrival_;
    }

    public void setRealTimeArrival_ (Timestamp realTimeArrival_) {
        this.realTimeArrival_ = realTimeArrival_;
    }

    public long getFine_ () {
        return fine_;
    }

    public void setFine_ () {
        long milliseconds = timeEnd_.getTime() - realTimeEnd_.getTime();
        if (milliseconds < 0) {
            int hour = -1 * (int) (milliseconds / 1000 / 3600);
            fine_ = hour * 100;
        }
    }

    public String getNameType () {
        return cargo_.name_;
    }

    public int getNumberOfCargo () {
        return cargo_.number_;
    }

    public typeOfCargo getTypeOfCargo () {
        return cargo_.type_;
    }

    public long getWaitTime_ () {
        return waitTime_;
    }

    public String getWaitString () {
        long time = waitTime_;
        int minute = (int) (time / 1000 / 60 % 60);
        int day = (int) (time / (24 * 60 * 60 * 1000));
        int hour = (int) (time / 1000 / 3600 % 60);
        return String.format("%02d:%02d:%02d", day, hour, minute);
    }

    public void setWaitTime_ (long waitTime_) {
        this.waitTime_ = waitTime_;
    }

    public Timestamp getRealTimeBegin_ () {
        return realTimeBegin_;
    }

    public void setRealTimeBegin_ (Timestamp realTimeBegin_) {
        this.realTimeBegin_ = realTimeBegin_;
    }

    public void isUploading (boolean uploading) {
        this.uploading = uploading;
    }

    public boolean getUploading () {
        return uploading;
    }

    public int getDelay_ () {
        return delay_;
    }

    public void setDelay_ (int delay_) {
        this.delay_ = delay_;
    }

    public static class Cargo {

        private String name_;
        private int number_;
        private typeOfCargo type_;

        public Cargo (String name, int number) {
            name_ = name;
            setType_();
            number_ = number;
        }

        public void setType_ () {
            switch (name_) {
                case "loose" -> type_ = typeOfCargo.LOOSE;
                case "liquid" -> type_ = typeOfCargo.LIQUID;
                case "container" -> type_ = typeOfCargo.CONTAINER;
            }
        }
    }

}
