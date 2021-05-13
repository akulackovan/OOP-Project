package com.akulackovan.Service.serviceThree;

import com.akulackovan.Service.entity.Ship;
import com.akulackovan.Service.serviceOne.ServiceOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("serviceThree")
public class ServerThreeController {

    private ServiceThree serviceThree;

    @Autowired
    public void setServiceThree(ServiceThree serviceThree) {
        this.serviceThree = serviceThree;
    }

    @GetMapping("report")
    public List<Ship> getReport() throws InterruptedException {
        Unloading unloading = new Unloading(serviceThree.getList());
        return unloading.getList();
    }
}
