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
 * @author olufemi.oshin
 */
@Entity
/*
 * A Reference to the Debtors' table dump from OCS. All Subs in this table at any point in time 
 * have an existing loan. A sub with two records already has credit-on-credit. A single-record sub
 * may be dispatched to SPs for a 2nd loan
 */
@NamedQueries({
    @NamedQuery(name = "MoreCreditCDR.getAllCDR", query = "SELECT cdr FROM MoreCreditDebtorsLog cdr"),
     @NamedQuery(name = "MoreCreditCDR.deAllCDR", query = "DELETE FROM MoreCreditDebtorsLog")
})
public class MoreCreditDebtorsLog implements Serializable {

    private static final long serialVersionUID = 1730920801273876863L;
    @Id
    @SequenceGenerator(name = "EasyCrdCDRSeq", sequenceName = "ECCDR_SEQ_GEN", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EasyCrdCDRSeq")
    private Long id;
    private String msisdn;
    private String loanAmount;
    private String loanPoundage;
    Timestamp dateOfBorrow;
    private Timestamp dateOfRepay;
    private Timestamp transactiondate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanPoundage() {
        return loanPoundage;
    }

    public void setLoanPoundage(String loanPoundage) {
        this.loanPoundage = loanPoundage;
    }

    public Timestamp getDateOfBorrow() {
        return dateOfBorrow;
    }

    public void setDateOfBorrow(Timestamp dateOfBorrow) {
        this.dateOfBorrow = dateOfBorrow;
    }

    public Timestamp getDateOfRepay() {
        return dateOfRepay;
    }

    public void setDateOfRepay(Timestamp dateOfRepay) {
        this.dateOfRepay = dateOfRepay;
    }

    public Timestamp getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(Timestamp transactiondate) {
        this.transactiondate = transactiondate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dateOfBorrow != null ? dateOfBorrow.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MoreCreditDebtorsLog)) {
            return false;
        }
        MoreCreditDebtorsLog other = (MoreCreditDebtorsLog) object;
        return !((this.dateOfBorrow == null && other.dateOfBorrow != null) || (this.dateOfBorrow != null && !this.dateOfBorrow.equals(other.dateOfBorrow)));
    }

    @Override
    public String toString() {
        return "EasyCreditDebtorsLog{" + "msisdn=" + msisdn + ", loanAmount=" + loanAmount + ", dateOfBorrow=" + dateOfBorrow + ", dateOfRepay=" + dateOfRepay +", transactiondate="+transactiondate+ '}';
    }
}
