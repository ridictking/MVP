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
public enum RequestType {

    NOR(1, "NOR", "Normal Loan Request", "LOAN"),
    SW(2, "SW", "Special Whitelist", "LOAN"),
    HAQ(3, "HAQ", "Highest Amount Query", "QUERY"),
    SL(4, "SL", "Smart Loan", "LOAN"),
    CES(5, "CES", "Credit Eligibility Status", "QUERY"),
    AUTOON(6, "AUTOON", "Automatic Loan Opt-in", "BROADCAST"),
    AUTOOFF(7, "AUTOOFF", "Automatic Loan Opt-out", "BROADCAST"),
    HELP(8, "HELP", "Help Message", "QUERY"),
    ACTIVATELOAN(9, "ACTIVATELOAN", "Remove subscriber from Blacklist", "SYSTEM"),
    DEACTIVATELOAN(10, "DEACTIVATELOAN", "Add Subscriber to Blacklist", "SYSTEM"),;

    private final int id;
    private final String type;
    private final String description;
    private final String category;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    private RequestType(int id, String type, String description, String category) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.category = category;
    }

    public static RequestType forValue(String type) {
        for (RequestType t : values()) {
            if (t.getType().equals(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException("No RequestType value for parameter " + type);
    }
}
