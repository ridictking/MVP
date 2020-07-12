/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.model.request;


import com.ng.emts.morecreditreceiver.util.GlobalMethods;
import com.ng.emts.morecreditreceiver.valueobject.CustomerType;
import com.ng.emts.morecreditreceiver.valueobject.RequestType;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author victor.akinola
 */
public class MoreCreditSubscriber implements Serializable {

    private static final long serialVersionUID = 4177341979372228661L;

    private Timestamp subscriptionDate;
    protected Long msisdn;
    private String description;
    private String messageToSend;
    private String transactionId;
    private Long creditId;
    private String ipAddress;
    private String hostName;
    private String requestMedium;
    private boolean triggerSMS;
    private boolean success;
    private GlobalMethods globalMethods;
    private String errorMessage;
    private String applicationfailsMessage;
    private String requestString;
    private String clientUrl;
    private Long amountRequested;
    private String smsShortCode;
    private RequestType requestType;
    private CustomerType customerType;
    private String channel;
    private MoreCreditCustomerType subType;
    private boolean broadcast;
    private String tagMsg;
    private String txType;
    private String correlationId;
    private Long pendingLoan;
    private Long recordCount;
    private String transReqType;
    private String additionalInfo;

    public MoreCreditSubscriber() {
        amountRequested = 0L;
        recordCount = 0L;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public MoreCreditSubscriber(Long msisdn) {
        this.msisdn = msisdn;
    }

    public Long getAmountRequested() {
        return amountRequested;
    }

    public void setAmountRequested(Long amountRequested) {
        this.amountRequested = amountRequested;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public String getRequestString() {
        return requestString;
    }

    public void setRequestString(String requestString) {
        this.requestString = requestString;
    }

    public Timestamp getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Timestamp subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getRequestMedium() {
        return requestMedium;
    }

    public void setRequestMedium(String requestMedium) {
        this.requestMedium = requestMedium;
    }

    public boolean isTriggerSMS() {
        return triggerSMS;
    }

    public void setTriggerSMS(boolean triggerSMS) {
        this.triggerSMS = triggerSMS;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public GlobalMethods getGlobalMethods() {
        return globalMethods;
    }

    public void setGlobalMethods(GlobalMethods globalMethods) {
        this.globalMethods = globalMethods;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessageToSend() {
        return messageToSend;
    }

    public void setMessageToSend(String messageToSend) {
        this.messageToSend = messageToSend;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public String getApplicationfailsMessage() {
        return applicationfailsMessage;
    }

    public void setApplicationfailsMessage(String applicationfailsMessage) {
        this.applicationfailsMessage = applicationfailsMessage;
    }

    public String getSmsShortCode() {
        return smsShortCode;
    }

    public void setSmsShortCode(String smsShortCode) {
        this.smsShortCode = smsShortCode;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public MoreCreditCustomerType getSubType() {
        return subType;
    }

    public void setSubType(MoreCreditCustomerType subType) {
        this.subType = subType;
    }

    public boolean isBroadcast() {
        return broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    public String getTagMsg() {
        return tagMsg;
    }

    public void setTagMsg(String tagMsg) {
        this.tagMsg = tagMsg;
    }

    public Long getPendingLoan() {
        return pendingLoan;
    }

    public void setPendingLoan(Long pendingLoan) {
        this.pendingLoan = pendingLoan;
    }

    public String getTransReqType() {
        return transReqType;
    }

    public void setTransReqType(String transReqType) {
        this.transReqType = transReqType;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "MoreCreditSubscriber{" + "subscriptionDate=" + subscriptionDate + ", msisdn=" + msisdn + ", amountRequested="
                + amountRequested + ", recordCount=" + recordCount + ",transactionId="
                + transactionId + ", requestMedium=" + requestMedium + ", additionalInfo=" + additionalInfo + ", requestMedium=" + transReqType + '}';
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
