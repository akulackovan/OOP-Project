package com.akulackovan.Service.serviceOne;

import com.akulackovan.Service.entity.Ship;

import java.io.IOException;
import java.util.List;

public interface ServiceOne {
    List<Ship> getList() throws IOException;
}
