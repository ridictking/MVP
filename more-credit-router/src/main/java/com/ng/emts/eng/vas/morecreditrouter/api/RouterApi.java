package com.ng.emts.eng.vas.morecreditrouter.api;

import com.ng.emts.eng.vas.morecreditrouter.model.request.FormRequest;
import com.ng.emts.eng.vas.morecreditrouter.service.cache.MemoryCache;
import com.ng.emts.eng.vas.morecreditrouter.service.morecredit.MoreCreditService;
import com.ng.emts.eng.vas.morecreditrouter.util.MoreCreditConstantsUtil;
import com.ng.emts.eng.vas.morecreditrouter.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/route")
public class RouterApi {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MoreCreditService moreCreditService;
    private final MemoryCache cache;
    private String getBaseUrl;
    //private HttpServletRequest httpServletRequest;

    @Autowired
    public RouterApi(MoreCreditService moreCreditService, MemoryCache cache) {
        this.moreCreditService = moreCreditService;
        this.cache = cache;
    }

    @PostConstruct
    public  void init(){
        getBaseUrl = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_NEW_MORE_CREDIT_BASE_URL);
    }

    @RequestMapping(
            value = "/eventapi",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> dispatchEventApi(@RequestParam Map<String, String> formParams, HttpServletRequest httpServletRequest){
        FormRequest request = new FormRequest();
        try{
            request.setAmountRequested(formParams.get("amountrequested"));
            request.setTransactionId(formParams.get("transaction_id"));
            request.setMsisdn(formParams.get("msisdn"));
            request.setMedium(formParams.get("channel"));
            request.setRequestType(formParams.get("request_type"));
            request.setAdditionalInfo(formParams.get("additional_info"));
            request.setTransactionType(formParams.get("transaction_type"));
            request.setSourceIp(WebUtils.getClientIpAddr(httpServletRequest));
            //request.setSourceIp("10.100.2.13");
            request.setCorrelationId(httpServletRequest.getHeader("correlation_id"));
            moreCreditService.dispatchEventAPI(request);
            String response = "<response><resultCode>0</resultCode><resultDesc>Operation is succssful</resultDesc></response>";
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
