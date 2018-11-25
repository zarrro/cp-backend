package com.spp.cp.rest;

import com.spp.cp.domain.entities.Order;
import com.spp.cp.domain.entities.Address;

import java.util.Date;
import java.util.List;

public class CreateOrderParams {

    private long customerCompanyId;

    private long contractorCompanyId;

    private Order.Type type;

    private String goods;

    private Date deadline;

    private List<Address> addresses;

    public long getCustomerCompanyId() {
        return customerCompanyId;
    }

    public void setCustomerCompanyId(long customerCompanyId) {
        this.customerCompanyId = customerCompanyId;
    }

    public long getContractorCompanyId() {
        return contractorCompanyId;
    }

    public void setContractorCompanyId(long contractorCompanyId) {
        this.contractorCompanyId = contractorCompanyId;
    }

    public Order.Type getType() {
        return type;
    }

    public void setType(Order.Type type) {
        this.type = type;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
