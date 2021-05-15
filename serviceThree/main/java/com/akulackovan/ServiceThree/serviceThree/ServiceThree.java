package com.akulackovan.ServiceThree.serviceThree;

import com.akulackovan.ServiceThree.entity.Ship;

import java.text.ParseException;
import java.util.List;

public interface ServiceThree {
    List<Ship> getList() throws ParseException;
}
