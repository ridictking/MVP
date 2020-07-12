/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.model.request;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author victor.akinola
 */
@Entity
@Table(name = "EasyCreditErrorTx")
public class MoreCreditErrorTx implements Serializable {

    private static final long serialVersionUID = 3095504788007611546L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long txId;
    private String msisdn;
    private String requestString;
    private Long amountRequested;
    private Long amountCredited;
    private Long amountDebited;
    private Long debit;
    private Timestamp transactionDate;
    private String ipAddress;
    private String hostName;
    private String errorMsg;
    private String errorCode;
    private String additional_info;
    private String vendorId;
    private String sourceIp; //ip address of the vendor

    public Long getTxId() {
        return txId;
    }

    public void setTxId(Long txId) {
        this.txId = txId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getRequestString() {
        return requestString;
    }

    public void setRequestString(String requestString) {
        this.requestString = requestString;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getAmountRequested() {
        return amountRequested;
    }

    public void setAmountRequested(Long amountRequested) {
        this.amountRequested = amountRequested;
    }

    public Long getAmountCredited() {
        return amountCredited;
    }

    public void setAmountCredited(Long amountCredited) {
        this.amountCredited = amountCredited;
    }

    public Long getAmountDebited() {
        return amountDebited;
    }

    public void setAmountDebited(Long amountDebited) {
        this.amountDebited = amountDebited;
    }

    public Long getDebit() {
        return debit;
    }

    public void setDebit(Long debit) {
        this.debit = debit;
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txId != null ? txId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MoreCreditErrorTx)) {
            return false;
        }
        MoreCreditErrorTx other = (MoreCreditErrorTx) object;
        return (this.txId != null || other.txId == null) && (this.txId == null || this.txId.equals(other.txId));
    }

    @Override
    public String toString() {
        return "com.ng.emts.eng.vas.sercom.easycredit.persistence.entities.EasyCreditErrorTx[ id=" + txId + " ]";
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
}
