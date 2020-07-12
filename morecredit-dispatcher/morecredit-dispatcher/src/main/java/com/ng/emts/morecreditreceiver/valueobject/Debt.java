/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.valueobject;

import java.io.Serializable;

/**
 *
 * @author victor.akinola
 */
public class Debt implements Serializable {

    private int numOfRecords;
    private Long[] debts;
    private long totalOutstanding;

    public int getNumOfRecords() {
        return numOfRecords;
    }

    public void setNumOfRecords(int numOfRecords) {
        this.numOfRecords = numOfRecords;
    }

    public Long[] getDebts() {
        return debts;
    }

    public void setDebts(Long[] debts) {
        this.debts = debts;
    }

    public long getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(long totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }

    @Override
    public String toString() {
        return "Debt{" + "numOfRecords=" + numOfRecords + ", totalOutstanding=" + totalOutstanding + '}';
    }
}
