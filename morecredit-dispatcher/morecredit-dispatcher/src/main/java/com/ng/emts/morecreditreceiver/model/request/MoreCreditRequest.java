package com.ng.emts.morecreditreceiver.model.request;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
/*
 * A history of all requests from all channels.
 */
@Table(name = "EasyCreditRequest")
public class MoreCreditRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long txId;
    private Long msisdn;
    private Timestamp transactionDate;
    private String sourceIp; //add to db
    private String ipAddress;
    private String hostName;
    private String requestMedium;
    private String requestString;
    private String spTx_id;
    private String additional_info;
    private String correlationId;//add to db
    private String transReqType;

    public Long getTxId() {
        return this.txId;
    }

    public void setTxId(Long txId) {
        this.txId = txId;
    }

    public Long getMsisdn() {
        return this.msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public Timestamp getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getRequestMedium() {
        return this.requestMedium;
    }

    public void setRequestMedium(String requestMedium) {
        this.requestMedium = requestMedium;
    }

    public String getRequestString() {
        return this.requestString;
    }

    public void setRequestString(String requestString) {
        this.requestString = requestString;
    }

    public String getSpTx_id() {
        return spTx_id;
    }

    public void setSpTx_id(String spTx_id) {
        this.spTx_id = spTx_id;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationid) {
        this.correlationId = correlationid;
    }

    public String getTransReqType() {
        return transReqType;
    }

    public void setTransReqType(String transReqType) {
        this.transReqType = transReqType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.txId != null ? this.txId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MoreCreditRequest)) {
            return false;
        }
        MoreCreditRequest other = (MoreCreditRequest) object;
        return ((this.txId != null) || (other.txId == null)) && ((this.txId == null) || (this.txId.equals(other.txId)));
    }

    @Override
    public String toString() {
        return "EasyCreditRequest{" + "msisdn=" + msisdn + ", requestMedium=" + requestMedium
                + ", transReqType=" + transReqType + ", requestString=" + requestString + '}';
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }
}
