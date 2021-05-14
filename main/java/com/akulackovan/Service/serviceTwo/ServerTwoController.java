package com.akulackovan.Service.serviceTwo;

import com.akulackovan.Service.entity.Ship;
import com.akulackovan.Service.exception.ServiceFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/serviceTwo")
public class ServerTwoController {

    private ServiceTwo serviceTwo;

    @Autowired
    public void setServiceTwo (ServiceTwo serviceTwo) {
        this.serviceTwo = serviceTwo;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Ship>> getList () {
        JavaJson javaJson = new JavaJson();
        List<Ship> ships = javaJson.readServiceOne(serviceTwo.getShips());
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @GetMapping("/get/{filename}")
    public String getList (@PathVariable("filename") String filename) {
        JavaJson javaJson = new JavaJson();
        try {
            return serviceTwo.getShips(filename);
        }
        catch (ServiceFileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }
    }

    @PostMapping("report")
    public String getReport () {
        try {
            return serviceTwo.getReport();
        }
        catch (ServiceFileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }
    }


}
