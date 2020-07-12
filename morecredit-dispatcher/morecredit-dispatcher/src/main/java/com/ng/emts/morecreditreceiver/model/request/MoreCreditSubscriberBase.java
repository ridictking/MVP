/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.model.request;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author olufemi.oshin
 */
@Entity
@Table(name="EasyCreditSubscriberBase")
public class MoreCreditSubscriberBase implements Serializable {

    private static final long serialVersionUID = -6184205696075560608L;

    @Id
    private Long msisdn;
    private String customerType;
    private Timestamp modifiedDate;
    private String mainProductId;

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getMainProductId() {
        return mainProductId;
    }

    public void setMainProductId(String mainProductId) {
        this.mainProductId = mainProductId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msisdn != null ? msisdn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MoreCreditSubscriberBase)) {
            return false;
        }
        MoreCreditSubscriberBase other = (MoreCreditSubscriberBase) object;
        return (this.msisdn != null || other.msisdn == null) && (this.msisdn == null || this.msisdn.equals(other.msisdn));
    }

    @Override
    public String toString() {
        return "MoreCreditSubscriberBase{" + "msisdn=" + msisdn + ", customerType=" + customerType + ", modifiedDate=" + modifiedDate + '}';
    }

}
