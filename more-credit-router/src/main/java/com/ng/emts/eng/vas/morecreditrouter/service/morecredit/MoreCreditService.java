package com.ng.emts.eng.vas.morecreditrouter.service.morecredit;

import com.ng.emts.eng.vas.morecreditrouter.model.request.FormRequest;
import com.ng.emts.eng.vas.morecreditrouter.service.cache.MemoryCache;
import com.ng.emts.eng.vas.morecreditrouter.service.jms.QueueProducer;
import com.ng.emts.eng.vas.morecreditrouter.service.router.RouterDispatch;
import com.ng.emts.eng.vas.morecreditrouter.service.sms.SmsService;
import com.ng.emts.eng.vas.morecreditrouter.util.GlobalMethods;
import com.ng.emts.eng.vas.morecreditrouter.util.MoreCreditConstantsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;

@Service
public class MoreCreditService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RouterDispatch routerDispatch;
    private final MemoryCache cache;
    private final QueueProducer queueProducer;
    private final SmsService smsService;
    private HashMap<String, String> map;
    String smsShortCode;
    String smsMsgTye;
    String serviceStringSERCOMUSSDRequest;
    String serviceStringIPCCUSSDRequest;
    String serviceStringIVRRequest;
    String serviceStringSercomPortalRequest;
    String serviceStringFacebookRequest;
    String serviceStringSERCOMSMSRequest;
    String MESSAGE_KEY_Service_String_API_REQUESTS;
    String getBaseURL;
    String conURL;

    @Autowired
    public MoreCreditService(RouterDispatch routerDispatch, MemoryCache cache, QueueProducer queueProducer, SmsService smsService) {
        this.routerDispatch = routerDispatch;
        this.cache = cache;
        this.queueProducer = queueProducer;
        this.smsService = smsService;
    }

    @PostConstruct
    public void init() {

        smsShortCode = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_senderShortCode);
        smsMsgTye = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_MORE_SMS_MSG_TYPE);
        serviceStringSERCOMUSSDRequest = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_Service_String_SERCOMUssd_Request);
        serviceStringIPCCUSSDRequest = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_Service_String_IPCCUssd_Request);
        serviceStringIVRRequest = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_FEATURE_API_CHANNEL_IVR);
        serviceStringFacebookRequest = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_FEATURE_API_CHANNEL_SERVICE_API_FACEBOOK);
        serviceStringSERCOMSMSRequest = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_Service_String_SERCOMSMS_Request);
        MESSAGE_KEY_Service_String_API_REQUESTS = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_Service_String_API_REQUESTS);
        getBaseURL = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_NEW_MORE_CREDIT_BASE_URL);
        serviceStringSercomPortalRequest = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_FEATURE_API_CHANNEL_SERCOM_PORTAL);
        map = new HashMap<>();
    }

    public void processRequest(HashMap<String, String> request) throws Exception {
        //log to queue
        try {
            queueProducer.queueRequestv1(request);
        } catch (Exception e) {
            //log to db
            throw e;
        }
    }
    public void processRequest2(FormRequest request){
        //log to queue
        try {
            queueProducer.queueRequestV2(request);
        } catch (Exception e) {
            //log to db
            throw e;
        }
    }
    public String dispatchEventUSSDAdapter(FormRequest request){
        logger.info("Request to router Bean :::::::: [req_type=" + request.getReqType() + ",message=" + request.getMessage() + ", msisdn=" + request.getMsisdn() + ", "
                + "session_id=" + request.getSessionId() + ",SourceIp=" + request.getSourceIp() + ", correlation-id=" + request.getCorrelationId()
                + "]");
        boolean emptyOrNull = GlobalMethods.checkInputParameter(request.getReqType(), request.getMessage(), request.getMsisdn(), request.getSessionId());
        if (emptyOrNull) {
            //exit
            String messageToSend = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_invalidUSSDRequestStringMsg);
            //smsService.SendSMS1(msisdn, messageToSend, smsShortCode, correlationId, smsMsgTye);
        } else {
            try {
                map.put("serviceString", serviceStringSERCOMUSSDRequest);
                processRequest(setMap(request));
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }
    //SMS Adapter Implementation
    public String smsDispatch(FormRequest request) {

        logger.info("Request received from SMS GW :: [message=" + request.getMessage() + ", msisdn=" + request.getMsisdn() + ", sourceIp=" + request.getSourceIp() + "]");

        boolean emptyOrNull = GlobalMethods.checkInputParameter(request.getMessage(), request.getMsisdn());
        if (emptyOrNull) {
            //exit
            String messageToSend = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_invalidUSSDRequestStringMsg);
            //smsService.SendSMS1(msisdn, messageToSend, smsShortCode, correlationId, smsMsgTye);
        } else {

            try {
                map.put("serviceString", serviceStringSERCOMSMSRequest);
                processRequest(setMap(request));
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        //return cache.nextTagMsg();
        return null;
    }

    public String dispatchEventIPCCAdapter(FormRequest request)  {

        logger.info("Request received from IPCC USSD GW :: [ message=" + request.getMessage() + ", msisdn=" + request.getMsisdn() + ", session_id=" + request.getSessionId() + "]");

        boolean emptyOrNull = GlobalMethods.checkInputParameter(request.getMessage(), request.getMsisdn(), request.getSessionId());
        if (emptyOrNull) {
            //exit
            String messageToSend = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_invalidUSSDRequestStringMsg);
            //smsService.SendSMS1(msisdn, messageToSend, smsShortCode, correlationId, smsMsgTye);
        } else {
            try {
                map.put("serviceString", serviceStringIPCCUSSDRequest);
                processRequest(setMap(request));
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }
//
//    //IPCC Adapter Implementation
//    public String dispatchEventIPCCAdapter(FormRequest request){
//
//        logger.info("Request received from IPCC USSD GW :: [ message=" + request.getMessage() + ", msisdn=" + request.getMsisdn() + ", session_id=" + request.getSessionId() + "]");
//
//        boolean emptyOrNull = GlobalMethods.checkInputParameter(request.getMessage(), request.getMsisdn(), request.getSessionId());
//        if (emptyOrNull) {
//            //exit
//            String messageToSend = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_invalidUSSDRequestStringMsg);
//            //smsService.SendSMS1(msisdn, messageToSend, smsShortCode, correlationId, smsMsgTye);
//        } else {
//            try {
//                processRequest(setMap(request));
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    public void dispatchEventAPI(FormRequest request) {
        logger.info("serviceStringIVRRequest :::::::: " + serviceStringIVRRequest);
        logger.info("serviceStringFacebookRequest :::::::: " + serviceStringFacebookRequest);
        logger.info("serviceStringFacebookRequest :::::::: " + MESSAGE_KEY_Service_String_API_REQUESTS);
        logger.info("Request to router Bean for API request:::::::: [msisdn=" + request.getMsisdn() +
                ",amountrequested=" + request.getAmountRequested() +
                ", transaction_id=" + request.getTransactionId() + ", "
                + "channel=" + request.getMedium() +
                ",request_type=" + request.getRequestType() +
                ", additional_info=" + request.getAdditionalInfo() +
                ", transaction_type=" + request.getTransactionType()
                + ",sourceIp=" + request.getSourceIp() +
                ",correlationId=" + request.getCorrelationId() + "]");
        boolean emptyOrNull = GlobalMethods.checkInputParameter(request.getAmountRequested(),
                request.getMsisdn(), request.getTransactionId(), request.getAdditionalInfo(), request.getTransactionType(), request.getMedium());
        if (emptyOrNull) {
            //exit
            String messageToSend = cache.getApplicationSetting(MoreCreditConstantsUtil.MESSAGE_KEY_invalidUSSDRequestStringMsg);
            //smsService.SendSMS1(msisdn, messageToSend, smsShortCode, correlationId, smsMsgTye);
        }
        try {
//            if (serviceStringIVRRequest.equals(request.getAdditionalInfo()))
//                map.put("serviceString", serviceStringIVRRequest);
            if (request.getAdditionalInfo().equals("ivr"))
                request.setServiceString("serviceStringIVRRequest");
                //map.put("serviceString", "serviceStringIVRRequest");
            else if (serviceStringFacebookRequest.equals(request.getAdditionalInfo()))
                map.put("serviceString", serviceStringFacebookRequest);
            else if (serviceStringSercomPortalRequest.equals(request.getAdditionalInfo()))
                map.put("serviceString", serviceStringSercomPortalRequest);
            //processRequest(setMap(request));
            processRequest2(request);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
    private HashMap<String,String> setMap(FormRequest request){
        map.put("msisdn", request.getMsisdn());
        map.put("medium", request.getMedium());
        map.put("paramx", request.getMessage());
        map.put("session_id", request.getSessionId());
        map.put("sourceIp", request.getSourceIp());
        map.put("correlationId", request.getCorrelationId());
        map.put("req_type", request.getReqType());
        map.put("transaction_id", request.getTransactionId());
        map.put("additional_info", request.getAdditionalInfo());
        map.values().removeAll(Collections.singleton(null));
        return map;
    }
}
