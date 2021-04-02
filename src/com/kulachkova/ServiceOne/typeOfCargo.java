package com.kulachkova.ServiceOne;

public enum typeOfCargo {
    LOOSE(1),
    LIQUID(2),
    CONTAINER(3);

    private int hour;

    typeOfCargo (int i) {
        hour = i;
    }

    public int getHour () {
        return hour;
    }
}

