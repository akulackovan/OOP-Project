package com.akulackovan.Service.serviceTwo;

import com.akulackovan.Service.entity.Ship;
import com.akulackovan.Service.exception.ServiceFileNotFoundException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@Service
public class ServiceTwoImpl implements ServiceTwo {
    @Override
    public String getShips() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8083/serviceOne/generate";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String resourceName = ".\\src\\main\\resources\\json\\arrivalOfShips.JSON";
        try(FileWriter fileWriter = new FileWriter (resourceName)){
            fileWriter.write(responseEntity.getBody());
            System.out.println("Service 1 was written in JSON");
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return responseEntity.getBody();
    }

    @Override
    public String getShips(String str) {
        String resourceName = ".\\src\\main\\resources\\json\\" + str + ".JSON";
        File file = new File(resourceName);
        if (!file.exists()) {
            throw new ServiceFileNotFoundException("No file");
        }
        try {
            return new String(Files.readAllBytes(file.toPath()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getReport() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8083/serviceThree/report";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String resourceName = ".\\src\\main\\resources\\json\\report.JSON";
        try (FileWriter fileWriter = new FileWriter(resourceName)) {
            fileWriter.write(responseEntity.getBody());
            System.out.println("Service 3 was written in JSON");
        }
        catch (IOException e) {
            throw new ServiceFileNotFoundException("No name file");
        }
        return responseEntity.getBody();
    }

}
