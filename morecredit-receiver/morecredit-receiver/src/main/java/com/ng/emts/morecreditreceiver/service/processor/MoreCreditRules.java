/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.service.processor;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.ng.emts.morecreditreceiver.exception.MoreCreditException;
import com.ng.emts.morecreditreceiver.model.request.*;
import com.ng.emts.morecreditreceiver.repo.MoreCreditDebtorsLogRepo;
import com.ng.emts.morecreditreceiver.repo.MoreCreditRegisterRepo;
import com.ng.emts.morecreditreceiver.repo.MoreCreditSubscriberBaseRepo;
import com.ng.emts.morecreditreceiver.repo.MoreCreditTxStatusRepo;
import com.ng.emts.morecreditreceiver.service.cache.MemoryCache;
import com.ng.emts.morecreditreceiver.util.GlobalMethods;
import com.ng.emts.morecreditreceiver.util.MoreCreditConstantsUtil;
import com.ng.emts.morecreditreceiver.util.SmsSender;
import com.ng.emts.morecreditreceiver.valueobject.CustomerType;
import com.ng.emts.morecreditreceiver.valueobject.Debt;
import com.ng.emts.morecreditreceiver.valueobject.MoreCreditResponse;
import com.ng.emts.morecreditreceiver.valueobject.ResponseValueRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Ridhwan Oladejo
 */
@Service
public class MoreCreditRules {

    private  final Logger logger = LoggerFactory.getLogger(getClass());

    private final MoreCreditPersistence moreCreditPersistence;
    private final MoreCreditRegisterRepo moreCreditRegisterRepo;
    private final MoreCreditTxStatusRepo moreCreditTxStatusRepo;
    private final MemoryCache cache;
    private final SmsSender smsService;
    private final MoreCreditSubscriberBaseRepo moreCreditSubscriberBaseRepo;
    private final MoreCreditDebtorsLogRepo moreCreditDebtorsLogRepo;

    private MoreCreditCustomerType subType;
    private CustomerType customerType;
    long dupeRequestBlockTimeSeconds;
    String smsShortCode;
    String applicationFailsMessage;
    String smsMsgTye;
    WebserviceMethods webMethod;
    //OCSXmlToObject ocsxmlToObject;
    String querySubscriberAccountTypeSubNotExist;

    private MoreCreditCustomerType theCustomerType;

    @Autowired
    public MoreCreditRules(MoreCreditPersistence moreCreditPersistence, MoreCreditRegisterRepo moreCreditRegisterRepo, MoreCreditTxStatusRepo moreCreditTxStatusRepo, MemoryCache cache, SmsSender smsService, MoreCreditSubscriberBaseRepo moreCreditSubscriberBaseRepo, MoreCreditDebtorsLogRepo moreCreditDebtorsLogRepo) {
        this.moreCreditPersistence = moreCreditPersistence;
        this.moreCreditRegisterRepo = moreCreditRegisterRepo;
        this.moreCreditTxStatusRepo = moreCreditTxStatusRepo;
        this.cache = cache;
        this.smsService = smsService;
        this.moreCreditSubscriberBaseRepo = moreCreditSubscriberBaseRepo;
        this.moreCreditDebtorsLogRepo = moreCreditDebtorsLogRepo;
    }

    public void setSubType(MoreCreditCustomerType subType) {
        this.subType = subType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    @PostConstruct
    public void init() {
        smsShortCode = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_senderShortCode);
        applicationFailsMessage = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_systemErrorMsg);
        smsMsgTye = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_MORE_SMS_MSG_TYPE);
        querySubscriberAccountTypeSubNotExist = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_SUBSCRIBER_DOES_NOT_EXIST);
    }

    public boolean checkIfRequestIsWinthinAllowableWindow(String parameters, String sourceIp, String msisdn) {
        boolean loanWithinWindow = false;
        //block duplicate request within a time window
        MoreCreditTxStatus subStatus = moreCreditTxStatusRepo.findByMsisdn(Long.parseLong(msisdn));
        dupeRequestBlockTimeSeconds = Long.parseLong(cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_dupeRequestBlockTimeSeconds));
        if (subStatus != null && dupeRequestBlockTimeSeconds != 60) {
            //log in txError
            moreCreditPersistence.logErrorTx(msisdn, "N/A", parameters, 0L, 0L, 0L, 0L,
                    ResponseValueRange.DUPLICATE_TRANSACTION.getValue().toString(),
                    ResponseValueRange.DUPLICATE_TRANSACTION.getDescription(), sourceIp, "DispatchModifiedTime:-" + subStatus.getDispatchModifiedDate());
            loanWithinWindow = true;
        }
        return loanWithinWindow;
    }

    public boolean moreCreditActivation(String msisdn, String parameters, String correlationId) {
        boolean moreCreditActivation = false;
        String CheckIfActivateLoanAirtime = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_ACTIVATELOAN_AIRTIME);
        String CheckIfActivateLoanData = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_ACTIVATELOAN_DATA);
        Optional<MoreCreditRegister> byId = moreCreditRegisterRepo.findById(Long.parseLong(msisdn));
        MoreCreditRegister register = null;
        if(byId.isPresent()){
            register = byId.get();
        }
        logger.info("register :::: " + register);
        logger.info("parameters :::: " + parameters);
        //This for Auto Loan OFF AND ON
        if (register != null && register.isBlacklisted() && !parameters.equals(CheckIfActivateLoanAirtime) && !parameters.equals(CheckIfActivateLoanData)) {
            logger.info(String.format("%s is blacklist.", msisdn));
            smsService.SendSMS1(msisdn,
                    cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_subscriberBlacklistedMsg),
                    smsShortCode, correlationId, smsMsgTye);

            moreCreditActivation = true;

        }
        return moreCreditActivation;
    }

    public boolean checkParameters(String parameters, MoreCreditSubscriber mcSub, String msisdn, String correlationId) {
        boolean routeparameters = false;
        MoreCreditReqRouter route;
        route = cache.getRoute(parameters);

        if (route == null) {
            logger.info(String.format("route not found: parameters=>%s", parameters));
            String messageToSend = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_invalidUSSDRequestStringMsg);
            mcSub.setMessageToSend(messageToSend);
            smsService.SendSMS1(msisdn, messageToSend, smsShortCode, correlationId, smsMsgTye);
            routeparameters = true;
        }
        return routeparameters;
    }

    public boolean eligible(String msisdn, String correlationId, String msgType){
        boolean eligible = true;
        String customerTypeStr;
        String mainProductId;
        MoreCreditResponse response;
        String messageToSend;

        // check EasyCreditSubscriberBase first
        MoreCreditSubscriberBase subBase = null;

        try {
            Optional<MoreCreditSubscriberBase> subscriberBaseRepoById = moreCreditSubscriberBaseRepo.findById(Long.parseLong(msisdn));
            if(subscriberBaseRepoById.isPresent())
                subBase = subscriberBaseRepoById.get();
        } catch (NoResultException ne) {
            logger.error("No Record in EasyCreditSubscriberBase of Subscriber[" + msisdn + "]", ne);
            subBase = null;
        } catch (Exception e) {
            logger.error("Exception in EasyCreditSubscriberBase::find", e);
        }

        if (subBase != null) {
            logger.info("Subscriber details exists in MoreCreditSubscriberBase ::: " + subBase.toString());
            customerTypeStr = subBase.getCustomerType();
            mainProductId = subBase.getMainProductId();
        } else {
            // get sourceMSISDN's accountType: PREPAID, POSTPAID or HYBRID and
            // mainProductId [Enuff-Yarn etc]
            String accountTypeData = webMethod.getAccountType(msisdn);

            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            //OCSAccountTypeMapper obj = gson.fromJson(ocsxmlToObject.getOCSAccountTypeResponse(accountTypeData), OCSAccountTypeMapper.class);
            OCSAccountTypeMapper obj = gson.fromJson(accountTypeData, OCSAccountTypeMapper.class);

            logger.info(String.format("obj.getTxnCode()=>%s", obj.getTxnCode()));
            logger.info(String.format("obj.getDescription()=>%s", obj.getDescription()));
            logger.info(String.format("obj.getCommandId()=>%s", obj.getCommandId()));
            logger.info(String.format("obj.getMainProductID()=>%s", obj.getMainProductID()));
            logger.info(String.format("obj.getPayModeCode()=>%s", obj.getPayModeCode()));
            logger.info(String.format("obj.getPayModeDescription()=>%s", obj.getPayModeDescription()));
            logger.info(String.format("obj.getTxnId()=>%s", obj.getTxnId()));
            if (accountTypeData == null) {
                response = new MoreCreditResponse(ResponseValueRange.OCS_SYS_TRY_AGAIN.getValue());
                String ocsresponse = "QuerySubscriberAccountTypeData == null (from OCS)";

                // if (accountTypeData == null) {
                logger.info(ocsresponse);
                smsService.SendSMS1(msisdn, applicationFailsMessage, smsShortCode, correlationId, msgType);
                moreCreditPersistence.logErrorTx(msisdn, response, ocsresponse);
                return false;
                // }
            } 
            else if (obj.getDescription().contains(String.format(querySubscriberAccountTypeSubNotExist, msisdn))) {
                response = new MoreCreditResponse(ResponseValueRange.OCS_SYS_BUSY.getValue());
                messageToSend = obj.getTxnCode() + "Service unavailable:System is busy";
                logger.info(messageToSend);
                moreCreditPersistence.logErrorTx(msisdn, response, messageToSend);
                smsService.SendSMS1(msisdn, "The loan service is currently busy, please try again later", smsShortCode, correlationId, msgType);
                return false;
            }
            else if (obj.getDescription().contains("Service unavailable:System is busy")) {
                response = new MoreCreditResponse(ResponseValueRange.OCS_SYS_BUSY.getValue());
                messageToSend = obj.getTxnCode() + "Service unavailable:System is busy";
                logger.info(messageToSend);
                moreCreditPersistence.logErrorTx(msisdn, response, messageToSend);
                smsService.SendSMS1(msisdn, "The loan service is currently busy, please try again later", smsShortCode, correlationId, msgType);
                return false;
            } else if (!obj.getTxnCode().equals(MoreCreditConstantsUtil.successfulOCSresponseCode)) {
                response = new MoreCreditResponse(ResponseValueRange.OCS_OTHER_ERROR.getValue());
                messageToSend = GlobalMethods.formatMessage(response.getResponsestring(),
                        new String[]{obj.getTxnCode(), obj.getDescription()});
                moreCreditPersistence.logErrorTx(msisdn, response, messageToSend);
                smsService.SendSMS1(msisdn, "The loan service is currently busy, please try again later", smsShortCode, correlationId, msgType);
                return false;
            }

            //successful.
            customerTypeStr = obj.getPayModeDescription();
            mainProductId = obj.getMainProductID();

            // update EasyCreditSubscriberBase
            subBase = new MoreCreditSubscriberBase();
            subBase.setMsisdn(Long.parseLong(msisdn));
            subBase.setCustomerType(customerTypeStr);
            subBase.setMainProductId(mainProductId);
            subBase.setModifiedDate(GlobalMethods.setSubscriptionDate());
            moreCreditSubscriberBaseRepo.save(subBase);
        }

        if (mainProductBlackListed(mainProductId)) {
            // mainProduct Not allowed
            MoreCreditResponse resp = new MoreCreditResponse(ResponseValueRange.STAFF_LINE_INELIGIBLE.getValue());
            String desc = "MSISDN[" + msisdn + "] mainProductId[" + mainProductId + "] not allowed ::: ";
            logger.info(resp.toString());
            moreCreditPersistence.logErrorTx(msisdn, resp, desc);
            smsService.SendSMS1(msisdn, cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_subscriberLoanIneligibilityMsg), smsShortCode, correlationId, msgType);
            return false;
        }

        theCustomerType = isCustomerTypeAllowed(customerTypeStr);
        if (theCustomerType == null) {
            // CustomerType Not allowed
            MoreCreditResponse resp = new MoreCreditResponse(ResponseValueRange.POSTPAID_LINE_INELIGIBLE.getValue());
            String desc = "MSISDN[" + msisdn + "] CustomerType[" + customerTypeStr + "] not allowed ::: ";
            logger.info(resp.toString());
            moreCreditPersistence.logErrorTx(msisdn, resp, desc);
            smsService.SendSMS1(msisdn, cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_subscriberLoanIneligibilityMsg), smsShortCode, correlationId, msgType);
            return false;
        }
        setCustomerType(CustomerType.forCustomerTypeValue(theCustomerType.getName().toUpperCase()));
        setSubType(theCustomerType);

        //RequestReceiverProcessor reqProc = null;
        //reqProc.setSubType(theCustomerType);
        return true;
    }

    public String getSubType() {
        return theCustomerType.getName().toUpperCase();

    }

    public String removeFromBlacklist(String msisdn, String correlationId) throws MoreCreditException {
        if (msisdn == null) {
            throw new MoreCreditException(ResponseValueRange.MISSING_MSISDN);
        }

        String response = "";
        MoreCreditRegister register = null;
        Optional<MoreCreditRegister> registerRepoById = moreCreditRegisterRepo.findById(Long.parseLong(msisdn));
        if(registerRepoById.isPresent()){
            register = registerRepoById.get();
        }
        if (register == null || !register.isBlacklisted()) {
            response = String.format("Subscriber [%s] is not blacklisted", msisdn);
        } else {// blacklisted subscriber.
            register.setBlacklisted(false);
            register.setBl_effectiveDate(Timestamp.valueOf(LocalDateTime.now()));
            moreCreditRegisterRepo.save(register);
            response = String.format("Subscriber [%s] has been removed from Blacklist", msisdn);
        }

        smsService.SendSMS1(msisdn, cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_activateLoanMsg), smsShortCode, correlationId, smsMsgTye);

        return response;
    }

    public Debt getExistingLoans(MoreCreditSubscriber ecSub) {
        Debt debt = null;
        final long msisdn = ecSub.getMsisdn();

        // TODO Check if either subscriber has an existing P2P loan    
        //checkP2PDebtsLog
        //
        MoreCreditTxStatus txStatus = moreCreditTxStatusRepo.findByMsisdn(msisdn);

        if (txStatus != null && !txStatus.isCreditWorthy()) {
            logger.info("Subscriber has pending loan transaction in EasyCreditTxStatus " + txStatus.toString());
            debt = new Debt();
            debt.setNumOfRecords(txStatus.getRecordCount());
            debt.setTotalOutstanding(txStatus.getOutstandingDebt());
        } else if (txStatus != null && txStatus.isCreditWorthy()) {
            logger.info("Subscriber is Credit Worth in EasyCreditTxStatus " + txStatus.toString());
            debt = new Debt();
            debt.setNumOfRecords(txStatus.getRecordCount());
            debt.setTotalOutstanding(txStatus.getOutstandingDebt());
        } else {
            debt = checkDebtorsLog(String.valueOf(msisdn));
        }
        logger.info(String.format("getExistingLoans::exit::Debts for msisdn::%s =>%s", msisdn, debt == null ? "<<Zero Debt>>" : debt.toString()));
        return debt;
    }

    public Debt checkDebtorsLog(String msisdn) {
        Debt debt = null;
        long loanAmount = 0L;
        int ResponseCount;

        List<MoreCreditDebtorsLog> debtRecords = moreCreditDebtorsLogRepo.findByMsisdn(msisdn);
        logger.info(String.format("checkDebtorsLog::MoreCreditDebtorsLog for msisdn::%s =>%s", msisdn, debtRecords == null ? "<<Zero debtRecords>>" : debtRecords.toString()));
        if (debtRecords != null && !debtRecords.isEmpty()) {
            debt = new Debt();

            Set<MoreCreditDebtorsLog> copySet = Sets.newHashSet(debtRecords);

            debt.setNumOfRecords(copySet.size());
            logger.info(String.format("checkDebtorsLog::final copySet msisdn::%s size=>%s", msisdn, copySet.isEmpty() ? "<<Zero copySet>>" : copySet.size()));

            for (MoreCreditDebtorsLog debtRecord : copySet) {

                loanAmount += Long.parseLong(debtRecord.getLoanAmount());

            }
            debt.setTotalOutstanding(loanAmount);
        }
        logger.info(String.format("checkDebtorsLog::exit::Debts for msisdn::%s =>%s", msisdn, debt == null ? "<<Zero Debt>>" : debt.toString()));

        return debt;

    }

    public boolean mainProductBlackListed(String aMainproductId) {
        try {
            String mainProductBlackList = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_mainProductBlackList);
            String[] mpbList = mainProductBlackList.split(",");
            for (String mainProduct : mpbList) {
                if (aMainproductId.equalsIgnoreCase(mainProduct)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error("MainProduct[" + aMainproductId + "] BlackListed", e);
            return false;
        }
    }

    private MoreCreditCustomerType isCustomerTypeAllowed(String aCustomerType) {
        for (MoreCreditCustomerType customerType1 : cache.getCustomerTypes()) {
            if (aCustomerType.equalsIgnoreCase(customerType1.getName())) {
                return customerType1;
            }
        }
        return null;
    }

    public boolean easyCreditLoanExists(String msisdn, boolean creditOnCreditEnabled, Debt existingLoan, String correlationId) {
        boolean loanExists = false;
        if (existingLoan == null || existingLoan.getNumOfRecords() == 0) {
            loanExists = false;
        } else if (creditOnCreditEnabled) {

            if (existingLoan.getNumOfRecords() < 2) {
                loanExists = false;
            } else {
                LoanExistsNotifyEvent(msisdn, correlationId);
                loanExists = true;
            }
        } else {
            LoanExistsNotifyEvent(msisdn, correlationId);
            loanExists = true;
        }
        logger.info(String.format("easyCreditLoanExists::exit::for msisdn::%s loanExists=>%s", msisdn, loanExists));
        return loanExists;
    }

    public Boolean easyCreditLoanExists(String msisdn, String correlationId) {
        Long loanAmount;
        String covrtloanAmount;
        Double ConvertDobLoanAmount;
        boolean loanExists = false;
        boolean creditOnCreditIsEnabled = Boolean.parseBoolean(cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_creditOnCreditIsOn));
        logger.info("credit-on-credit is :=" + (creditOnCreditIsEnabled ? "ON" : "OFF"));
        MoreCreditSubscriber ecSub = new MoreCreditSubscriber(Long.parseLong(msisdn));
        Debt existingLoan = getExistingLoans(ecSub);

        if (existingLoan == null || existingLoan.getNumOfRecords() == 0) {
            loanExists = false;
        } else if (creditOnCreditIsEnabled) {
            if (existingLoan.getNumOfRecords() < 2) {
                loanExists = false;
            } else {

                LoanExistsNotifyEvent(msisdn, correlationId);
                loanExists = true;
            }
        } else {
            LoanExistsNotifyEvent(msisdn, correlationId);
            loanExists = true;
        }
        logger.info(String.format("easyCreditLoanExists::exit::for msisdn::%s loanExists=>%s", msisdn, loanExists));
        return loanExists;
    }

    private void LoanExistsNotifyEvent(String msisdn, String correlationId) {

        MoreCreditResponse response = new MoreCreditResponse(ResponseValueRange.EASYCREDIT_LOAN_EXISTS.getValue());
        String messageToSend = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_subscriberHasPendingLoanMsg);
        smsService.SendSMS1(msisdn, messageToSend, smsShortCode, correlationId, smsMsgTye);
        logger.info(String.format("moreCreditLoanExists::msisdn =>%s, EasyCreditResponse =>%s, messageToSend =>%s, ", msisdn, response.toString(), messageToSend));
        // Update EasyCreditErrorTx
        moreCreditPersistence.logErrorTx(msisdn, response, messageToSend);
    }

    public boolean amountRequestedIsConfigured(String amountRequested) {
        try {
            String amountsConfigured = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_amountsConfigured);
            String[] amountList = amountsConfigured.split(",");
            for (String anAmount : amountList) {
                if (amountRequested.equalsIgnoreCase(anAmount)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error("Amount Requested[" + amountRequested + "] Not Configured", e);
            return false;
        }
    }

    public String addToBlacklist(String msisdn, String correlationId) throws MoreCreditException {
        if (msisdn == null) {
            throw new MoreCreditException(ResponseValueRange.MISSING_MSISDN);
        }

        String response = "";

        MoreCreditRegister register = null;
        Optional<MoreCreditRegister> moreCreditRegisterRepoById = moreCreditRegisterRepo.findById(Long.parseLong(msisdn));
        if(moreCreditRegisterRepoById.isPresent()){
            register = moreCreditRegisterRepoById.get();
        }
        if (register == null) {
            register = new MoreCreditRegister();
            register.setMsisdn(Long.parseLong(msisdn));
            register.setBlacklisted(true);
            register.setBl_deactivateDate(Timestamp.valueOf(LocalDateTime.now()));
            moreCreditRegisterRepo.save(register);
            response = String.format("Subscriber [%s] has been added to Blacklist", msisdn);
        } else if (register.isBlacklisted()) {
            response = String.format("Subscriber [%s] is already in Blacklist", msisdn);
        } else {
            register.setBlacklisted(true);
            moreCreditRegisterRepo.save(register);
            response = String.format("Subscriber [%s] has been added to Blacklist", msisdn);
        }
        smsService.SendSMS1(msisdn, cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_deactivateLoanMsg), smsShortCode, correlationId, smsMsgTye);

        return response;
    }
}
