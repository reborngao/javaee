package com.visabao.machine.entity;

import java.io.Serializable;
import java.util.List;


public class OrderService implements Serializable {
    private List<CsServices> csService;
    private List<InServices> inService;

    public List<CsServices> getCsService() {
        return csService;
    }

    public void setCsService(List<CsServices> csService) {
        this.csService = csService;
    }

    public List<InServices> getInService() {
        return inService;
    }

    public void setInService(List<InServices> inService) {
        this.inService = inService;
    }
}
