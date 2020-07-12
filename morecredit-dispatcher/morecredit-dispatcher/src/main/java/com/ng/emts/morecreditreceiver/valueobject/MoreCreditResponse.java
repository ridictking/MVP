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
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class MoreCreditResponse implements Serializable {

    private static final long serialVersionUID = 3603320560026425658L;
    
    @XmlElement(name = "responseCode")
    private int responsecode;

    @XmlElement(name = "responseString")
    private String responsestring;

    public MoreCreditResponse() {
    }
    
    public MoreCreditResponse(ResponseValueRange response){
    	this.responsecode = response.getValue();
    	this.responsestring = response.getDescription();
    }
    
    public MoreCreditResponse(int responseCode) {
        this.responsecode = responseCode;
        this.responsestring = ResponseValueRange.forValue(responseCode).getDescription();
    }

    public MoreCreditResponse(int responseCode, String responseString) {
        this.responsecode = responseCode;
        this.responsestring = responseString;
    }

    public int getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(int responsecode) {
        this.responsecode = responsecode;
        this.responsestring = ResponseValueRange.forValue(responsecode).getDescription();
    }

    public String getResponsestring() {
        return responsestring;
    }

    public void setResponsestring(String responsestring) {
        this.responsestring = responsestring;
    }

    @Override
    public String toString() {
        return "MoreCreditResponse{" + "responseCode=" + responsecode + ", responseString=" + responsestring + '}';
    }
}
