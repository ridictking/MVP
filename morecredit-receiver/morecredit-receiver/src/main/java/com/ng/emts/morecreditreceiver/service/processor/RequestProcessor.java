package com.ng.emts.morecreditreceiver.service.processor;

import com.ng.emts.morecreditreceiver.exception.MoreCreditException;
import com.ng.emts.morecreditreceiver.model.request.FormRequest;
import com.ng.emts.morecreditreceiver.model.request.MoreCreditReqRouter;
import com.ng.emts.morecreditreceiver.model.request.MoreCreditSubscriber;
import com.ng.emts.morecreditreceiver.service.cache.MemoryCache;
import com.ng.emts.morecreditreceiver.service.smartloan.SmartLoan;
import com.ng.emts.morecreditreceiver.util.GlobalMethods;
import com.ng.emts.morecreditreceiver.util.MoreCreditConstantsUtil;
import com.ng.emts.morecreditreceiver.util.SmsSender;
import com.ng.emts.morecreditreceiver.valueobject.Debt;
import com.ng.emts.morecreditreceiver.valueobject.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RequestProcessor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${com.ng.emts.receiver.exchange.name}")
    private String exchange;

    private final MemoryCache cache;
    private final MoreCreditRules businessRules;
    private final MoreCreditPersistence moreCreditPersistence;
    private final WebserviceMethods webserviceMethods;
    private final SmsSender smsService;
    private final SmartLoan smLoan;
    private final RabbitTemplate rabbitTemplate;
    private String onRequestReceivedMessage;
    private String smsShortCode;
    private String applicationFailsMessage;
    private String smsMsgTye;
    private String correlationId;

    @Autowired
    public RequestProcessor(MemoryCache cache, MoreCreditRules businessRules, MoreCreditPersistence moreCreditPersistence, WebserviceMethods webserviceMethods, SmsSender smsService, SmartLoan smLoan, RabbitTemplate rabbitTemplate) {
        this.cache = cache;
        this.businessRules = businessRules;
        this.moreCreditPersistence = moreCreditPersistence;
        this.webserviceMethods = webserviceMethods;
        this.smsService = smsService;
        this.smLoan = smLoan;
        this.rabbitTemplate = rabbitTemplate;
    }
    @PostConstruct
    public void init() {
        onRequestReceivedMessage = cache.nextTagMsg();
        smsShortCode = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_senderShortCode);
        applicationFailsMessage = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_systemErrorMsg);
        smsMsgTye = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_MORE_SMS_MSG_TYPE);
    }

    public void process(FormRequest request){

        String parameters = request.getMessage();
        String sourceIp = request.getSourceIp();
        String msisdn = request.getMsisdn();
        String medium = request.getMedium();
        String session_id = request.getSessionId();
        String additional_info = request.getAdditionalInfo();
        String transReqType = request.getRequestType();
        correlationId = request.getCorrelationId();

        logger.info("processRequest :::: " + request.toString());

        //Check if same request is not within 60secs window
        if (businessRules.checkIfRequestIsWinthinAllowableWindow(parameters, sourceIp, msisdn)) {
            return;
        }

        moreCreditPersistence.logMoreCreditRequest(msisdn, parameters, medium, session_id, additional_info, transReqType, sourceIp, correlationId);
        //Check if subscriber optin for blacklisting
        if (businessRules.moreCreditActivation(msisdn, parameters, correlationId)) {
            return;
        }
        MoreCreditSubscriber mcSub = new MoreCreditSubscriber();

        mcSub.setMessageToSend(onRequestReceivedMessage);

        parameters = parameters.toLowerCase();

        mcSub.setRequestMedium(medium);
        mcSub.setRequestString(parameters);
        mcSub.setMsisdn(Long.parseLong(msisdn));
        mcSub.setSmsShortCode(smsShortCode);
        mcSub.setChannel(medium);
        mcSub.setCorrelationId(correlationId);
        mcSub.setAdditionalInfo(additional_info);
        MoreCreditReqRouter route;
        //Check if parameter not in the MoreCreditReqRouter table
        if (businessRules.checkParameters(parameters, mcSub, msisdn, correlationId)) {
            return;
        }
        String request_type;
        route = cache.getRoute(parameters);
        request_type = route.getRequestType();

        logger.info("RequestType :::" + route.getRequestType() + ", amountRequested :::= " + route.getValue());

        mcSub.setRequestType(RequestType.forValue(route.getRequestType()));
        mcSub.setBroadcast(route.isBroadcast());
        mcSub.setSubscriptionDate(GlobalMethods.setSubscriptionDate());
        mcSub.setTxType(route.getTransactionType());
        mcSub.setTransReqType(transReqType);

        int request_type_id = RequestType.forValue(request_type).getId();
        logger.info(String.format("request_type_id >>>>>>>>>>: %s", request_type_id));
        // Eligibility checks
        if (!businessRules.eligible(msisdn, correlationId, smsMsgTye)) {
            return;
        }

        //mcSub.setSubType(businessRules.getSubType());
        boolean creditOnCreditIsEnabled = Boolean.parseBoolean(cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_creditOnCreditIsOn));
        logger.info("credit-on-credit is :=" + (creditOnCreditIsEnabled ? "ON" : "OFF"));
        Debt existingLoan = businessRules.getExistingLoans(mcSub);
        mcSub.setPendingLoan(existingLoan != null ? existingLoan.getTotalOutstanding() : 0L);
        mcSub.setRecordCount(existingLoan != null ? existingLoan.getNumOfRecords() : 0L);
        switch (request_type_id) {
            case 1: // NOR = Normal loan request and ...
                // Check if There Exists pending Loan
                   /* if (businessRules.easyCreditLoanExists(msisdn, creditOnCreditIsEnabled, existingLoan, correlationId)) {
                        return;
                    }*/

                mcSub.setAmountRequested(Long.parseLong(route.getValue()));
                rabbitTemplate.convertAndSend(exchange,"DISPATCHER_KEY",mcSub);
                //creditProcessor.acceptRequest(ecSub);
                webserviceMethods.requestToDispatcher(msisdn,
                        mcSub.getAmountRequested(),
                        correlationId,
                        mcSub.getChannel(),
                        mcSub.getRequestType(),
                        mcSub.getTxType(),
                        mcSub.getRequestString(),
                        mcSub.getPendingLoan(),
                        mcSub.getRecordCount(),
                        mcSub.isBroadcast(),
                        additional_info,
                        mcSub.getMessageToSend(),
                        "PREPAID");

                break;

            case 3:// HAQ
            case 5:// CES
                //Check if There Exists pending Loan
                    /*  if (businessRules.easyCreditLoanExists(msisdn, creditOnCreditIsEnabled, existingLoan,correlationId)) {
                        return;
                    }*/
                mcSub.setAmountRequested(0L);
                webserviceMethods.requestToDispatcher(msisdn,
                        mcSub.getAmountRequested(),
                        correlationId,
                        mcSub.getChannel(),
                        mcSub.getRequestType(),
                        mcSub.getTxType(),
                        mcSub.getRequestString(),
                        mcSub.getPendingLoan(),
                        mcSub.getRecordCount(),
                        mcSub.isBroadcast(),
                        additional_info,
                        mcSub.getMessageToSend(), "PREPAID");
                break;
            case 4:
                logger.info(String.format("ecSub.getMsisdn()>>>>>>>>>>: %s", mcSub.getMsisdn()));
                logger.info(String.format("ecSub.getTransactionId()>>>>>>>>>>: %s", mcSub.getCorrelationId()));
                logger.info(String.format("ecSub.getTxType()>>>>>>>>>>: %s", mcSub.getTxType()));
                String getTreatedSmartLoan = smLoan.treatSmartLoan(String.valueOf(mcSub.getMsisdn()), String.valueOf(mcSub.getTxType()), String.valueOf(mcSub.getCorrelationId()));

                if ("null".equals(getTreatedSmartLoan)) {
                    String messageToSend = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_subscriberHasPendingLoanReqMsg);
                    mcSub.setMessageToSend(messageToSend);
                    smsService.SendSMS1(msisdn, messageToSend, smsShortCode, correlationId, smsMsgTye);
                    return;
                } else {
                    logger.info(String.format("Sending request to USSD Push>>>>>>>>>>: %s", getTreatedSmartLoan));
                    smsService.SendUSSDPush(msisdn, getTreatedSmartLoan, "", correlationId);
                }
                break;
            case 6: // AUTOON
                logger.info("Amount Requested :::" + Long.parseLong(route.getValue()));
                logger.info("Channel :::" + mcSub.getChannel());
                //autoLoan(msisdn, ecSub.getChannel(), true, true, Long.parseLong(route.getValue()) * 100);
                webserviceMethods.toAutoLoanService(msisdn, mcSub.getChannel(), true, true, 0L, correlationId);
                break;
            case 7: // AUTOOFF
                webserviceMethods.toAutoLoanService(msisdn, mcSub.getChannel(), true, false, 0L, correlationId);
                break;
            case 8: // HELP
                String messageToSend = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_helpNotificationMsg);
                mcSub.setMessageToSend(messageToSend);
                smsService.SendSMS1(msisdn, messageToSend, smsShortCode, correlationId, smsMsgTye);
                break;
            case 9: // ACTIVATELOAN
                try {
                    businessRules.removeFromBlacklist(msisdn, correlationId);
                } catch (MoreCreditException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
                break;
            case 10: // DEACTIVATELOAN
                try {
                    businessRules.addToBlacklist(msisdn, correlationId);
                } catch (MoreCreditException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
                break;
            case 2:// SW = Special whitelist-Not yet handled.
            default:
                //do nothing. //throw new AssertionError();
        }
    }
}
