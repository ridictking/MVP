/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.model.request;

import javax.persistence.*;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 *
 * @author victor.akinola
 */
@Entity
@Table(name = "MoreCreditReqRouter_CSMS")
public class MoreCreditReqRouter implements Serializable {

    private static final long serialVersionUID = 2299990603011694082L;

    @Id
    private String matchString; 
    private String requestType;
    private String description;
    private boolean isBroadcast;
    private String value;
    private String transactionType;
    //
    @Transient
    private Pattern pattern; //pattern

    public String getMatchString() {
        return matchString;
    }

    public void setMatchString(String matchString) {
        this.matchString = matchString;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBroadcast() {
        return isBroadcast;
    }

    public void setBroadcast(boolean isBroadcast) {
        this.isBroadcast = isBroadcast;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matchString != null ? matchString.hashCode() : 0);
        return hash;
    }

//    @Override
//    public boolean equals(Object object) {
//        if (!(object instanceof com.ng.emts.sercom.more.credit.receiver.entities.MoreCreditReqRouter)) {
//            return false;
//        }
//        com.ng.emts.sercom.more.credit.receiver.entities.MoreCreditReqRouter other = (com.ng.emts.sercom.more.credit.receiver.entities.MoreCreditReqRouter) object;
//        if ((this.matchString == null && other.matchString != null) || (this.matchString != null && !this.matchString.equals(other.matchString))) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public String toString() {
        return "MoreCreditReqRouter [matchString=" + matchString + ", requestType=" + requestType + ", description="
                + description + ", isBroadcast=" + isBroadcast + ", value=" + value + "]";
    }

    //
    public Pattern retrievePattern() {
        return this.pattern;
    }

    public void compilePattern() {
        try {
            //doing this in setter of setMatchString should work, but I had issues once.
            pattern = Pattern.compile(this.matchString);
        } catch (Exception e) {
        }
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
