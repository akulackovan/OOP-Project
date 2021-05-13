package com.akulackovan.Service.serviceTwo;

import com.akulackovan.Service.entity.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("serviceTwo")
public class ServerTwoController {

    private ServiceTwo serviceTwo;

    @Autowired
    public void setServiceTwo (ServiceTwo serviceTwo) {
        this.serviceTwo = serviceTwo;
    }

    @GetMapping("get")
    public List<Ship> getList () throws IOException {
        return serviceTwo.getShips();
    }


}
