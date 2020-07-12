package com.ng.emts.eng.vas.morecreditrouter.model.request;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "More_Credit_Router_TRTT_Log")
public class TransmitterLog implements Serializable {

    private static final long serialVersionUID = 3985324440557133678L;
    private static final String SEQ_NAME = "More_Credit_TXLOG_ID_SEQ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, initialValue = 1, allocationSize = 1)
    private long id;

    private String messageId;
    private String msisdn;
    private String message;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date transactionDate;
    private String ipAddress;
    private String correlationId;
    private String url;
    private String responseBody;
    private String postParameters;
    private String transctionId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getPostParameters() {
        return postParameters;
    }

    public void setPostParameters(String postParameters) {
        this.postParameters = postParameters;
    }

    public String getTransctionId() {
        return transctionId;
    }

    public void setTransctionId(String transctionId) {
        this.transctionId = transctionId;
    }

    @Override
    public String toString() {
        return String.format("msisdn=>%s, message=>%s, messageid=>%s", msisdn, message, messageId);
    }
}
