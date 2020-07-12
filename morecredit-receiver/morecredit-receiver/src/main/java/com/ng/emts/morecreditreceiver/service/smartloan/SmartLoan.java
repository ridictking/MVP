/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.service.smartloan;

import com.ng.emts.morecreditreceiver.util.GlobalMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 *
 * @author OSHIN
 */
@Service
public class SmartLoan {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String treatSmartLoan(String msisdn, String transactionType, String transactionId) {
        logger.info("transactionId:>>>>>>>>> " + transactionId);
        logger.info("transactionType:>>>>>>>>> " + transactionType);
        String stripMSISDN = GlobalMethods.stripMsisdn(msisdn);
        logger.info("StripMSISDN:>>>>>>>>> " + GlobalMethods.stripMsisdn(msisdn));

        String rmvResonseBodyBraces = null;
        String finalResponse = null;
       /* MoreCreditVendor vendor = null;
        String SERVICE_API_CHANNEL_SCORING = cache.getApplicationSetting(EasyCreditConstantsUtil.MESSAGE_KEY_FEATURE_API_CHANNEL_SERVICE_API_SCORING);
        vendor = cache.nextVendorProfiling("API", SERVICE_API_CHANNEL_SCORING);
        logger.info(String.format("Avialable Vendor on Channel >>>>>>>>>>: %s", vendor));
        logger.info(String.format("Vendor's Name >>>>>>>>>>: %s", vendor.getVendorName()));
        int request_type_id = RequestVendorIdEnum.forValue(vendor.getVendorName()).getId();
        logger.info(String.format("request_type_id >>>>>>>>>>: %s", request_type_id));

        switch (request_type_id) {
            case 1: //It is Simbrella
                responseBody = Unirest.post(vendor.getVendorEligibilityUrl())
                        .header("Content-Type", "application/json")
                        .body("msisdn=" + stripMSISDN + "&transactionId=" + transactionId + "&transactionType=" + transactionType)
                        .asString();
                logger.info(String.format("responseBody >>>>>>>>>>: %s", responseBody.getBody()));
                break;

            case 2:// It is GreyStone

                break;
            case 3:// It is NanoAirtime
                responseBody = Unirest.get(vendor.getVendorEligibilityUrl() + "msisdn=" + stripMSISDN + "&transactionId=" + transactionId + "&transactionType=" + transactionType)
                        .header("Cache-Control", "no-cache")
                        .header("Postman-Token", "d925a093-8c39-422a-b248-cb5de5a514fe")
                        .asString();
                logger.info(String.format("responseBody >>>>>>>>>>: %s", responseBody.getBody()));

                break;
            case 4:// It is T3_Wireless

                break;

            default:
            //do nothing. //throw new AssertionError();
        }

        Gson gson = new Gson();
        ScoringResultWithDenominations cnvrtScoringToObject = gson.fromJson(responseBody.getBody(), ScoringResultWithDenominations.class);
        int increase = 1;

        for (ScoringDenominations sD : cnvrtScoringToObject.getDenominations()) {

            //rmvResonseBodyBraces = "Please Select Amount:" + "\r" + "\r" + increase++ + "." + "\t" + sD.getAmount();
            //int cntrToInteger = Integer.parseInt(sD.getAmount());
            //logger.info(String.format("cntrToInteger >>>>>>>>>>: %s", cntrToInteger));
            rmvResonseBodyBraces = increase++ + "." + "\t" + sD.getAmount();
            logger.info(String.format("rmvResonseBodyBraces >>>>>>>>>>: %s", rmvResonseBodyBraces));

        }
        if (cnvrtScoringToObject.getEligibility_Status().toUpperCase() == "FALSE") {
            return null;

        } else {

            logger.info(String.format(
                    "Request to log, msisdn=>%s,"
                    + "rmvResonseBodyBraces=>%s, transactionId=>%s, "
                    + "transactionType=>%s,  vendor.getVendorEligibilityUrl()=>%s, vendor.GetId=>%s",
                    msisdn, rmvResonseBodyBraces, transactionId, transactionType,
                    vendor.getVendorEligibilityUrl(), vendor.getId()));
            db.logScoringRequests(msisdn, vendor.getId(), vendor.getVendorEligibilityUrl(), transactionId, transactionType, rmvResonseBodyBraces.toString(), "Smart_Loan");
            finalResponse = "Please Select Amount:" + "\r" + "\r" + rmvResonseBodyBraces.toString();
            return finalResponse;
        }
    }*/
       
        return finalResponse;
    }
}
