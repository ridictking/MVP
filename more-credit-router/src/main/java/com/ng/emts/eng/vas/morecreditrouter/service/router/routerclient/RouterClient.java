package com.ng.emts.eng.vas.morecreditrouter.service.router.routerclient;

import com.ng.emts.eng.vas.morecreditrouter.model.request.FormRequest;
import com.ng.emts.eng.vas.morecreditrouter.model.request.TransmitterLog;
import com.ng.emts.eng.vas.morecreditrouter.model.response.ServiceRouter;
import com.ng.emts.eng.vas.morecreditrouter.repo.TransmitterLogRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static com.ng.emts.eng.vas.morecreditrouter.util.MoreCreditConstantsUtil.*;

@Component
public class RouterClient {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected HttpHeaders headers;
    private final RestTemplate restTemplate;
    private final TransmitterLogRepo repo;

    @Autowired
    public RouterClient(RestTemplate restTemplate, TransmitterLogRepo repo) {
        this.restTemplate = restTemplate;
        this.repo = repo;
        setDefaultHeaders();
    }

    private void setDefaultHeaders() {
        headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
    }


    public void processForm(FormRequest formRequest,
                            String bindingAddress,
                            ServiceRouter route,
                            String url,
                            Map<String, String> postParameters ){
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        if(formRequest.getServiceString().equals(SERVICE_API_IVR)
                || formRequest.getServiceString().equals(SERCOM_PORTAL)
                || formRequest.getServiceString().equals(SERVICE_API_FACEBOOK)
                || formRequest.getServiceString().equals(SERCOM_SMS)){
            headers.set("correlation-id", formRequest.getCorrelationId());
        }
        try{
            MultiValueMap<String,String> request = new LinkedMultiValueMap<>();
            request.add("msisdn", formRequest.getMsisdn());
            if(formRequest.getServiceString().equals(SERCOM_SMS))
                request.add("correlationId", String.valueOf(formRequest.getCorrelationId()));
            else
                request.add("correlationId", formRequest.getCorrelationId());
            request.add("transaction_id", formRequest.getTransactionId());
            request.add("sourceIp", formRequest.getSourceIp());
            request.add("medium", formRequest.getMedium());
            request.add("amountrequested", formRequest.getAmountRequested());
            request.add("additional_info", formRequest.getAdditionalInfo());
            request.add("transaction_type", formRequest.getTransactionType());
            request.add("request_type", formRequest.getRequestType());
            request.add("message", formRequest.getMessage());
            request.add("session_id", formRequest.getSessionId());
            request.add("req_type", formRequest.getReqType());

            request.values().removeAll(Collections.singleton(null));

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url,entity,String.class);
            if(response.getStatusCode().is2xxSuccessful()){
                if(response.hasBody()){
                    logToDb(bindingAddress,
                            formRequest.getMsisdn(),
                            formRequest.getServiceString(),
                            "",
                            formRequest.getCorrelationId(),
                            route.getUrl(),
                            response.getBody(),
                            postParameters,
                            String.valueOf(formRequest.getTransactionId()));
                }
            }
        }catch (RestClientException e){
            logger.error(e.toString());
            e.printStackTrace();
        }
    }
    private void logToDb(String ipAddress, String msisdn, String message, String messageId, String correlationId, String middlewareUrl, String responseBody, Map<String, String> postParameters, String transactionId) {
        TransmitterLog log = new TransmitterLog();
        log.setCorrelationId(correlationId);
        log.setIpAddress(ipAddress);
        log.setMessage(message);
        log.setMessageId(messageId);
        log.setMsisdn(msisdn);
        log.setPostParameters(postParameters.toString());
        log.setResponseBody(responseBody);
        log.setTransactionDate(new Date());
        log.setUrl(middlewareUrl);
        log.setTransctionId(transactionId);
        try {
            repo.save(log);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
