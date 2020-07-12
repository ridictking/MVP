/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.service.processor;

import com.ng.emts.morecreditreceiver.service.cache.MemoryCache;
import com.ng.emts.morecreditreceiver.util.MoreCreditConstantsUtil;
import com.ng.emts.morecreditreceiver.valueobject.MoreCreditResponse;
import com.ng.emts.morecreditreceiver.valueobject.RequestType;
import com.ng.emts.morecreditreceiver.valueobject.ResponseValueRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 *
 * @author Ridhwan Oladejo
 */
@Service
public class WebserviceMethods {

    private  final Logger logger = LoggerFactory.getLogger(getClass());

    private final MemoryCache cache;
    private final RestTemplate restTemplate;


    private String P2P_BASE_URL_GET;
    private String OCS_BASE_URL;
    private String OCS_ACCOUNT_TYPE_URL;
    private String BE_ID;
    private String TYPE;
    private String APP_NAME;
    private String SETTING_KEY_CREATE_SUPPLEMENTARY_OFFERING_MODIFICATION_ORDER;
    private String SETTING_KEY_CREATE_MC_DISPATCHER_SERVICE_URL;
    private String SETTING_KEY_MC_AUTO_LOAN_SERVICE_URL;
    private String MoreCreditNewBaseURL;
    private String SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_BASE_URL;
    private String SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_BASE_URL_Test_Env;
    private String SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_QUERY_SUBS_ACCT_TYPE;

    @Autowired
    public WebserviceMethods(MemoryCache cache, RestTemplate restTemplate) {
        this.cache = cache;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() {
        P2P_BASE_URL_GET = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_P2P_BASE_URL_GET);
        OCS_BASE_URL = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_OCS_BASE_URL);
        OCS_ACCOUNT_TYPE_URL = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_OCS_ACCOUNT_TYPE_URL);
        BE_ID = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_BES_BE_ID);
        TYPE = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_BES_TYPE);
        APP_NAME = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_APP_NAME);
        SETTING_KEY_CREATE_SUPPLEMENTARY_OFFERING_MODIFICATION_ORDER = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_CREATE_SUPPLEMENTARY_OFFERING_MODIFICATION_ORDER);
        SETTING_KEY_CREATE_MC_DISPATCHER_SERVICE_URL = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_CREATE_MC_DISPATCHER_SERVICE_URL);
        MoreCreditNewBaseURL = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_NEW_MORE_CREDIT_BASE_URL);
        SETTING_KEY_MC_AUTO_LOAN_SERVICE_URL = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_MC_AUTO_LOAN_SERVICE_URL);
        SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_BASE_URL = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_BASE_URL);
        SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_BASE_URL_Test_Env = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_BASE_URL_Test_Env);
        SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_QUERY_SUBS_ACCT_TYPE = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_QUERY_SUBS_ACCT_TYPE);
    }

    //WebService client to P2P Service.
    public boolean p2pLoanExists(String msisdn){
        HttpHeaders headers = new HttpHeaders();
        headers.add("cache-control", "no-cache");
        headers.add("accept", "text/plain");
        try {
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(P2P_BASE_URL_GET, HttpMethod.GET, entity, String.class);
            logger.info("p2pLoanExists invoked ::: " + response.getStatusCode() + ":/:" + response.getStatusCodeValue());
            return Boolean.parseBoolean(response.getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    //WebService client to OCS Service. 
    public String getAccountType(String msisdn){
        logger.info(String.format("OCS_BASE_URL.concat(SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_BASE_URL>>>>>>>>>>>>>>>>>>>=>%s", SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_BASE_URL +
                SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_QUERY_SUBS_ACCT_TYPE));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "*/*");
        try {
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_BASE_URL.concat(SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_QUERY_SUBS_ACCT_TYPE) + msisdn;
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }

    //WebService client to  MoreCredit Dispatcher Service.
    public MoreCreditResponse  requestToDispatcher(String msisdn,
                                                   Long amountrequested,
                                                   String correlationid,
                                                   String channel,
                                                   RequestType request_type,
                                                   String txtype,
                                                   String requeststring,
                                                   Long pendingloan,
                                                   Long recordcount,
                                                   boolean isbroadcast,
                                                   String additional_info,
                                                   String messageToSend,
                                                   String SubType){
        try {

            logger.info("Receiver request ::::::: [amountrequested=" + amountrequested + ", correlationid=" + correlationid + ", channel=" + channel + ", "
                    + "request_type=" + request_type + ", msisdn=" + msisdn + ", txtype=" + txtype
                    + ", pendingloan=" + pendingloan + ", recordcount" + recordcount + ", isbroadcast=" + isbroadcast + ", SubType=" + SubType
                    + ", additional_info=" + additional_info + ", messageToSend=" + messageToSend + "]");

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            MultiValueMap<String,String> request = new LinkedMultiValueMap<>();
            request.add("msisdn", msisdn);
            request.add("amountrequested", String.valueOf(amountrequested));
            request.add("correlationid", correlationid);
            request.add("channel", channel);
            request.add("request_type", String.valueOf(request_type));
            request.add("txtype", txtype);
            request.add("requeststring", requeststring);
            request.add("pendingloan", String.valueOf(pendingloan));
            request.add("recordcount", String.valueOf(recordcount));
            request.add("isbroadcast", String.valueOf(isbroadcast));
            request.add("additional_info", additional_info);
            request.add("messageToSend", messageToSend);
            request.add("subType", SubType);
            request.values().removeAll(Collections.singleton(null));
            String url = MoreCreditNewBaseURL + SETTING_KEY_CREATE_MC_DISPATCHER_SERVICE_URL;
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(request, headers);
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, entity, String.class);
            return new MoreCreditResponse(ResponseValueRange.SUCCESSFUL);
        } catch (RestClientException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }


    public MoreCreditResponse toAutoLoanService(String msisdn, String channel, boolean triggerSMS, boolean true_Optin_false_Optout, Long amountRequested, String correlationId) {
        try {

            logger.info(String.format("msisdn>>>>>>>>>>>>>>>>>>>=>%s", msisdn));
            logger.info(String.format("channel>>>>>>>>>>>>>>>>>>>=>%s", channel));
            logger.info(String.format("triggerSMS>>>>>>>>>>>>>>>>>>>=>%s", triggerSMS));
            logger.info(String.format("true_Optin_false_Optout>>>>>>>>>>>>>>>>>>>=>%s", true_Optin_false_Optout));
            logger.info(String.format("amountRequested>>>>>>>>>>>>>>>>>>>=>%s", amountRequested));
            logger.info(String.format("correlationId>>>>>>>>>>>>>>>>>>>=>%s", correlationId));
            String url = MoreCreditNewBaseURL + SETTING_KEY_MC_AUTO_LOAN_SERVICE_URL;
            logger.info(String.format("OCS_BASE_URL.concat(Auto Loan URL>>>>>>>>>>>>>>>>>>>=>%s", url));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            MultiValueMap<String,String> request = new LinkedMultiValueMap<>();
            request.add("msisdn", msisdn);
            request.add("channel", channel);
            request.add("triggerSMS",String.valueOf(triggerSMS) );
            request.add("true_Optin_false_Optout", String.valueOf(true_Optin_false_Optout));
            request.add("amountRequested", String.valueOf(amountRequested));
            request.add("correlationId", correlationId);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(request, headers);
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, entity, String.class);
            return new MoreCreditResponse(ResponseValueRange.SUCCESSFUL);
        } catch (RestClientException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }

}
