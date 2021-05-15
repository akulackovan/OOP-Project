package com.akulackovan.ServiceOne.serviceOne;

import com.akulackovan.ServiceOne.entity.Ship;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceOneImpl implements ServiceOne{

    private final ShipGenerator shipGenerator = new ShipGenerator(100);

    @Override
    public List<Ship> getList () {
        return shipGenerator.getShips();
    }
}
