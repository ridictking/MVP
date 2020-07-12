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
 * @author victor.akinola
 */
@Entity
@Table(name="EasyCreditRegister")
public class MoreCreditRegister implements Serializable {

    private static final long serialVersionUID = -1430504175088256285L;

    @Id
    private Long msisdn;
    private Long amountAllocated;
    private Long threshold;
    private String description;
    private Timestamp bl_effectiveDate;
    private Timestamp sw_effectiveDate;
    private Timestamp al_effectiveDate;
    private Timestamp bl_deactivateDate;
    private Timestamp sw_deactivateDate;
    private Timestamp al_deactivateDate;
    private boolean specialWhitelist;
    private boolean blacklisted;
    private boolean onAutoLoan;

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public Long getAmountAllocated() {
        return amountAllocated;
    }

    public void setAmountAllocated(Long amountAllocated) {
        this.amountAllocated = amountAllocated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getThreshold() {
        return threshold;
    }

    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }

    public Timestamp getBl_effectiveDate() {
        return bl_effectiveDate;
    }

    public void setBl_effectiveDate(Timestamp bl_effectiveDate) {
        this.bl_effectiveDate = bl_effectiveDate;
    }

    public Timestamp getSw_effectiveDate() {
        return sw_effectiveDate;
    }

    public void setSw_effectiveDate(Timestamp sw_effectiveDate) {
        this.sw_effectiveDate = sw_effectiveDate;
    }

    public Timestamp getAl_effectiveDate() {
        return al_effectiveDate;
    }

    public void setAl_effectiveDate(Timestamp al_effectiveDate) {
        this.al_effectiveDate = al_effectiveDate;
    }

    public Timestamp getBl_deactivateDate() {
        return bl_deactivateDate;
    }

    public void setBl_deactivateDate(Timestamp bl_deactivateDate) {
        this.bl_deactivateDate = bl_deactivateDate;
    }

    public Timestamp getSw_deactivateDate() {
        return sw_deactivateDate;
    }

    public void setSw_deactivateDate(Timestamp sw_deactivateDate) {
        this.sw_deactivateDate = sw_deactivateDate;
    }

    public Timestamp getAl_deactivateDate() {
        return al_deactivateDate;
    }

    public void setAl_deactivateDate(Timestamp al_deactivateDate) {
        this.al_deactivateDate = al_deactivateDate;
    }

    public boolean isSpecialWhitelist() {
        return specialWhitelist;
    }

    public void setSpecialWhitelist(boolean specialWhitelist) {
        this.specialWhitelist = specialWhitelist;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    public void setBlacklisted(boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    public boolean isOnAutoLoan() {
        return onAutoLoan;
    }

    public void setOnAutoLoan(boolean onAutoLoan) {
        this.onAutoLoan = onAutoLoan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msisdn != null ? msisdn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MoreCreditRegister)) {
            return false;
        }
        MoreCreditRegister other = (MoreCreditRegister) object;
        return !((this.msisdn == null && other.msisdn != null) || (this.msisdn != null && !this.msisdn.equals(other.msisdn)));
    }

    @Override
    public String toString() {
        return "EasyCreditRegister[ msisdn=" + msisdn + " ]";
    }

}
