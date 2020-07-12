package com.ng.emts.morecreditreceiver.service.processor;

import com.ng.emts.morecreditreceiver.model.request.MoreCreditErrorTx;
import com.ng.emts.morecreditreceiver.model.request.MoreCreditRequest;
import com.ng.emts.morecreditreceiver.repo.MoreCreditErrorTxRepo;
import com.ng.emts.morecreditreceiver.repo.MoreCreditRequestRepo;
import com.ng.emts.morecreditreceiver.util.GlobalMethods;
import com.ng.emts.morecreditreceiver.util.LocalIP;
import com.ng.emts.morecreditreceiver.valueobject.MoreCreditResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoreCreditPersistence {
    private  final Logger logger = LoggerFactory.getLogger(getClass());

    private final MoreCreditErrorTxRepo creditErrorTxRepo;
    private final MoreCreditRequestRepo moreCreditRequestRepo;

    @Autowired
    public MoreCreditPersistence(MoreCreditErrorTxRepo creditErrorTxRepo, MoreCreditRequestRepo moreCreditRequestRepo) {
        this.creditErrorTxRepo = creditErrorTxRepo;
        this.moreCreditRequestRepo = moreCreditRequestRepo;
    }

    public void logErrorTx(String msisdn, String vendorId, String requestString,
                           Long amountCredited, Long amountDebited,
                           Long amountRequested, Long debit,
                           String errorCode, String errorMsg,
                           String sourceIp,
                           String additional_info) {

        MoreCreditErrorTx errorTx = new MoreCreditErrorTx();

        errorTx.setAdditional_info(additional_info);
        errorTx.setAmountCredited(amountCredited);
        errorTx.setAmountDebited(amountDebited);
        errorTx.setAmountRequested(amountRequested);
        errorTx.setDebit(debit);
        errorTx.setErrorCode(errorCode);
        errorTx.setErrorMsg(errorMsg);
        errorTx.setHostName(LocalIP.getHostName());
        errorTx.setIpAddress(LocalIP.getLocalIP());
        errorTx.setMsisdn(msisdn);
        errorTx.setRequestString(requestString);
        errorTx.setSourceIp(sourceIp);
        errorTx.setTransactionDate(GlobalMethods.setSubscriptionDate());
        errorTx.setVendorId(vendorId);
        creditErrorTxRepo.save(errorTx);
    }
    public void logErrorTx(String msisdn, MoreCreditResponse response, String additional_info) {
        MoreCreditErrorTx errorTx = new MoreCreditErrorTx();
        errorTx.setTransactionDate(GlobalMethods.setSubscriptionDate());
        errorTx.setIpAddress(LocalIP.getLocalIP());
        errorTx.setHostName(LocalIP.getHostName());
        errorTx.setMsisdn(msisdn);
        errorTx.setErrorCode(String.valueOf(response.getResponsecode()));
        errorTx.setErrorMsg(response.getResponsestring());
        errorTx.setAdditional_info(additional_info);
        try {
            creditErrorTxRepo.save(errorTx);
        } catch (Exception e) {
            logger.error("Error in EasyCreditBean::Error creating Entity EasyCreditErrorTx :::" + e.getMessage(), e);
        }
    }
    public void logMoreCreditRequest(String msisdn,
                                     String requestString,
                                     String medium,
                                     String spTx_id,
                                     String additional_info,
                                     String transReqType,
                                     String sourceIp,
                                     String correlationId) {
        MoreCreditRequest reqTx = new MoreCreditRequest();
        try {
            reqTx.setMsisdn(Long.parseLong(msisdn));
            reqTx.setRequestMedium(medium);
            reqTx.setIpAddress(LocalIP.getLocalIP());
            reqTx.setHostName(LocalIP.getHostName());
            reqTx.setRequestString(requestString);
            reqTx.setAdditional_info(additional_info);
            reqTx.setSpTx_id(spTx_id);
            reqTx.setTransactionDate(GlobalMethods.setSubscriptionDate());
            reqTx.setSourceIp(sourceIp);
            reqTx.setCorrelationId(correlationId);
            reqTx.setTransReqType(sourceIp);
            moreCreditRequestRepo.save(reqTx);
        } catch (Exception e) {
            logger.error("Error in MoreCreditBean::Error creating Entity EasyCreditRequest :::" + e.getMessage(), e);
        }
    }
}
