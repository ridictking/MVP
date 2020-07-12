/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.valueobject;

/**
 *
 * @author victor.akinola
 */
public enum CustomerType {

    PREPAID(1, "PREPAID"),
    POSTPAID(2, "POSTPAID"),
    HYBRID(3, "HYBRID"),;

    private final String customerType;
    private final Integer id;

    public String getCustomerType() {
        return customerType;
    }

    public Integer getId() {
        return id;
    }

    private CustomerType(Integer id, String customerType) {
        this.id = id;
        this.customerType = customerType;
    }

    public static CustomerType forCustomerTypeValue(String customerType) {
        for (CustomerType c : values()) {
            if (c.getCustomerType().equals(customerType)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unconfigured CustomerType  specified :: " + customerType);
    }

    public static CustomerType forCustomerTypeId(Integer id) {
        for (CustomerType c : values()) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unconfigured CustomerType  ID specified :: " + id);
    }
}
