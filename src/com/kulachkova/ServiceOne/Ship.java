package com.kulachkova.ServiceOne;

import java.sql.Timestamp;
import java.util.Calendar;

public class Ship {

    public Ship (String name, Timestamp timeBegin, Cargo cargo) {
        name_ = name;
        cargo_ = cargo;
        timeBegin_ = timeBegin;
        timeEnd_ = stay(timeBegin);
        fine_ = 0;
        waitTime_ = "00:00:00";
        uploading = false;
    }

    public Ship (String name, Timestamp timeBegin, Timestamp timeEnd, String nameOfCargo, int numberOfCargo) {
        name_ = name;
        cargo_ = new Cargo(nameOfCargo, numberOfCargo);
        timeBegin_ = timeBegin;
        timeEnd_ = timeEnd;
        fine_ = 0;
        waitTime_ = "00:00:00";
        uploading = false;
    }

    public Timestamp stay (Timestamp timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        int timeInHours = switch (cargo_.type_) {
            case LOOSE -> 1;
            case LIQUID -> 2;
            case CONTAINER -> 1;
        };
        cal.add(Calendar.HOUR, cargo_.number_ * timeInHours);
        timestamp = new Timestamp(cal.getTime().getTime());
        return timestamp;
    }

    public String toString () {
        return "Name: " + name_ + "\nCargo type: " + cargo_.name_ + "\nCargo number: " + cargo_.number_ + "\nTime arrival: "
                + timeBegin_ + "\nTime departure: " + timeEnd_;
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

    public int getFine_ () {
        return fine_;
    }

    public void setFine_ (int fine_) {
        this.fine_ = fine_;
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

    public String getWaitTime_ () {
        return waitTime_;
    }

    public void setWaitTime_ (String waitTime_) {
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

    public static class Cargo {

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

        private String name_;
        private int number_;
        private typeOfCargo type_;
    }

    private String name_;
    private Timestamp timeEnd_;
    private Timestamp realTimeEnd_;
    private Timestamp timeBegin_;
    private Timestamp realTimeArrival_;
    private Timestamp realTimeBegin_;
    private String waitTime_;
    private Cargo cargo_;
    private int fine_;
    private boolean uploading;
}
