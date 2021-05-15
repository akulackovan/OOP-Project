package com.akulackovan.ServiceThree.entity;

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

