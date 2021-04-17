package com.kulachkova.ServiceOne;

public enum typeOfCargo {
    LOOSE(1),
    LIQUID(2),
    CONTAINER(1);

    private final int hour;

    typeOfCargo (int i) {
        hour = i;
    }

    public int getHour () {
        return hour;
    }
}

