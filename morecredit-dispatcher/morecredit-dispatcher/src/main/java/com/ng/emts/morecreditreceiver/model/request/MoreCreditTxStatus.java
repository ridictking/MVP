/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. * @author victor.akinola

 */
package com.ng.emts.morecreditreceiver.model.request;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "EasyCreditTxStatus")
public class MoreCreditTxStatus implements Serializable {

    private static final long serialVersionUID = 5181686745751241945L;

    private Long msisdn;
    private Long amountRequested;
    private Long amountGranted;//add to db
    private Long amountRecovered;//add to db
    private Long outstandingDebt;//will be updated externally from ocs/evc log.
    private boolean creditWorthy;//add to db
    private String ipAddress;
    private String hostName;
    private Timestamp transactionDate;
    private Timestamp dispatchModifiedDate; //to record the last dispatch activity for sub //add to db
    private String subType;
    private Integer recordCount;//add to db

    public MoreCreditTxStatus() {
        outstandingDebt = 0L;
        amountGranted = 0L;
        amountRecovered = 0L;
        recordCount = 0;
    }

    @Id
    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public Long getAmountRequested() {
        return amountRequested;
    }

    public void setAmountRequested(Long amountRequested) {
        this.amountRequested = amountRequested;
    }

    public boolean isCreditWorthy() {
        return creditWorthy;
    }

    public void setCreditWorthy(boolean creditWorthy) {
        this.creditWorthy = creditWorthy;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Long getOutstandingDebt() {
        return outstandingDebt;
    }

    public void setOutstandingDebt(Long outstandingDebt) {
        this.outstandingDebt = outstandingDebt;
    }

    public Timestamp getDispatchModifiedDate() {
        return dispatchModifiedDate;
    }

    public void setDispatchModifiedDate(Timestamp dispatchModifiedDate) {
        this.dispatchModifiedDate = dispatchModifiedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msisdn != null ? msisdn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MoreCreditTxStatus)) {
            return false;
        }
        MoreCreditTxStatus other = (MoreCreditTxStatus) object;
        return (this.msisdn != null || other.msisdn == null) && (this.msisdn == null || this.msisdn.equals(other.msisdn));
    }

    @Override
    public String toString() {
        return "EasyCreditTxStatus{" + "msisdn=" + msisdn + ", amountRequested=" + amountRequested + ", amountGranted=" + amountGranted + ", amountRecovered=" + amountRecovered + ", outstandingDebt=" + outstandingDebt + ", creditWorthy=" + creditWorthy + ", transactionDate=" + transactionDate + ", dispatchModifiedDate=" + dispatchModifiedDate + ", subType=" + subType + ", recordCount=" + recordCount + '}';
    }

    

    public Long getAmountGranted() {
        return amountGranted;
    }

    public void setAmountGranted(Long amountGranted) {
        this.amountGranted = amountGranted;
    }

    public Long getAmountRecovered() {
        return amountRecovered;
    }

    public void setAmountRecovered(Long amountRecovered) {
        this.amountRecovered = amountRecovered;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }
}
