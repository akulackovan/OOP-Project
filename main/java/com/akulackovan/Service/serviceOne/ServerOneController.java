package com.akulackovan.Service.serviceOne;

import com.akulackovan.Service.entity.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("serviceOne")
public class ServerOneController {

    private ServiceOne serviceOne;

    @Autowired
    public void setServiceOne(ServiceOne serviceOne) {
        this.serviceOne = serviceOne;
    }

    @GetMapping("generate")
    public List<Ship> list() throws IOException {
        return serviceOne.getList();
    }
}
