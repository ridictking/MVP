/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.model.request;

/**
 *
 * @author OSHIN
 */
public class OCSAccountTypeMapper {

    String commandId;
    String description;
    String txnCode;
    String txnId;
    String mainProductID;
    String payModeCode;
    String payModeDescription;

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(String txnCode) {
        this.txnCode = txnCode;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getMainProductID() {
        return mainProductID;
    }

    public void setMainProductID(String mainProductID) {
        this.mainProductID = mainProductID;
    }

    public String getPayModeCode() {
        return payModeCode;
    }

    public void setPayModeCode(String payModeCode) {
        this.payModeCode = payModeCode;
    }

    public String getPayModeDescription() {
        return payModeDescription;
    }

    public void setPayModeDescription(String payModeDescription) {
        this.payModeDescription = payModeDescription;
    }

}
