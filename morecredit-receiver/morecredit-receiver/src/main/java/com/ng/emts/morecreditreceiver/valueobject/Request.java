/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.valueobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author victor.akinola
 */
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class Request implements Serializable {

    private static final long serialVersionUID = -1938346151664797179L;

    @XmlElement
    private String msisdn;
    @XmlElement
    private String vendorid;
    @XmlElement
    private String creditgranted;
    @XmlElement
    private String amountdebited;
    @XmlElement
    private String outstandingdebt;
    @XmlElement
    private String statusCode;
    @XmlElement
    private String statusDescription;
    @XmlElement
    private String returnMode;
    @XmlElement
    private String transactionId;
    @XmlElement
    private String eventType;
    @XmlElement
    private String amountqualified;
    @XmlElement
    private String amountrequested;
    @XmlElement
    private String transactionType;
    @XmlElement
    private String serviceEventType;

    @XmlElement
    private String channelType;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

    public String getCreditgranted() {
        return creditgranted;
    }

    public void setCreditgranted(String creditgranted) {
        this.creditgranted = creditgranted;
    }

    public String getAmountdebited() {
        return amountdebited;
    }

    public void setAmountdebited(String amountdebited) {
        this.amountdebited = amountdebited;
    }

    public String getOutstandingdebt() {
        return outstandingdebt;
    }

    public void setOutstandingdebt(String outstandingdebt) {
        this.outstandingdebt = outstandingdebt;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getReturnMode() {
        return returnMode;
    }

    public void setReturnMode(String returnMode) {
        this.returnMode = returnMode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getAmountqualified() {
        return amountqualified;
    }

    public void setAmountqualified(String amountqualified) {
        this.amountqualified = amountqualified;
    }

    public String getAmountrequested() {
        return amountrequested;
    }

    public void setAmountrequested(String amountrequested) {
        this.amountrequested = amountrequested;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getServiceEventType() {
        return serviceEventType;
    }

    public void setServiceEventType(String serviceEventType) {
        this.serviceEventType = serviceEventType;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    @Override
    public String toString() {
        return "Request{" + "msisdn=" + msisdn + ", vendorid=" + vendorid + ", creditgranted=" + creditgranted + ", amountdebited=" + amountdebited + ", outstandingdebt=" + outstandingdebt + ", statusCode=" + statusCode + ", statusDescription=" + statusDescription + ", returnMode=" + returnMode + ", transactionId=" + transactionId + ", eventType=" + eventType + ", amountqualified=" + amountqualified + ", "
                + "amountrequested=" + amountrequested + ", transactionType=" + transactionType + ", serviceEventType=" + serviceEventType + ", channelType=" + channelType + '}';
    }

}
