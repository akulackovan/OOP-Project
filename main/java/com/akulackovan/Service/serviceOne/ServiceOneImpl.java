package com.akulackovan.Service.serviceOne;

import com.akulackovan.Service.entity.Ship;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceOneImpl implements ServiceOne{
    @Override
    public List<Ship> getList () {
        ShipGenerator shipGenerator = new ShipGenerator(100);
        return shipGenerator.getShips();
    }
}
