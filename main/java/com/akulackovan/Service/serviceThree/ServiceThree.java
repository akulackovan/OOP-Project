package com.akulackovan.Service.serviceThree;

import com.akulackovan.Service.entity.Ship;
import net.minidev.json.parser.ParseException;

import java.util.List;

public interface ServiceThree {
    List<Ship> getList() throws ParseException;
}
