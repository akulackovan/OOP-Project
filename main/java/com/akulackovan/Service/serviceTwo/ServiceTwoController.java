package com.akulackovan.Service.serviceTwo;

import com.akulackovan.Service.exception.ServiceFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileWriter;
import java.io.IOException;

@RestController
@RequestMapping("/serviceTwo")
public class ServiceTwoController {

    private ServiceTwo serviceTwo;

    @Autowired
    public void setServiceTwo(ServiceTwo serviceTwo) {
        this.serviceTwo = serviceTwo;
    }

    @GetMapping("/get")
    public String getList() {
        return serviceTwo.getShips();
    }

    @GetMapping("/get/{filename}")
    public String getList(@PathVariable("filename") String filename) {
        try {
            String str = serviceTwo.getShips(filename);
            if (str.equals(""))
            {
                return "File empty";
            }
            return str;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }
    }

    @PostMapping("/getReport")
    public String getReport(@RequestBody String string) {
        try {
            if (string.equals("\"report\""))
            {
                return serviceTwo.getReport();
            }
            else {
                FileWriter file = new FileWriter(".\\src\\main\\resources\\report.JSON");
                file.write("");
                return null;
            }
        }
        catch (ServiceFileNotFoundException | IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }
    }

}
