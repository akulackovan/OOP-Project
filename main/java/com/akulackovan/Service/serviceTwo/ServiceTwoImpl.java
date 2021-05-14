package com.akulackovan.Service.serviceTwo;

import com.akulackovan.Service.exception.ServiceFileNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

@Service
public class ServiceTwoImpl implements ServiceTwo {
    @Override
    public String getShips () {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8083/serviceOne/generate";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        return responseEntity.getBody();
    }

    @Override
    public String getShips (String str) {
        try {
            String content = new Scanner(new File("D:\\Test\\ServiceOne\\src\\main\\resources\\" + str + ".JSON")).useDelimiter("\\Z").next();
            return content;
        }
        catch (FileNotFoundException e) {
            throw new ServiceFileNotFoundException("No name file");
        }

    }

    @Override
    public String getReport () {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8083/serviceThree/report";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        try (FileWriter file = new FileWriter("D:\\Test\\ServiceOne\\src\\main\\resources\\report.JSON")) {
            file.write(responseEntity.getBody());
            System.out.println("Service 3 was written in JSON");
        }
        catch (IOException e) {
            throw new ServiceFileNotFoundException("No name file");
        }
        return responseEntity.getBody();
    }
}
