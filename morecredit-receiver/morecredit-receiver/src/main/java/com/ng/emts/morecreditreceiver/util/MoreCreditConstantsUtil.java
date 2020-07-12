/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.util;

/**
 *
 * @author OSHIN
 */
public class MoreCreditConstantsUtil {

    //Messages/Settings
    public static final String MESSAGE_KEY_operator = "operator";
    public static final String MESSAGE_KEY_successFulStatusUpdateMsg = "successFulStatusUpdateMsg";
    public static final String MESSAGE_KEY_subscriberLoanIneligibilityMsg = "subscriberLoanIneligibilityMsg";
    public static final String MESSAGE_KEY_failedEVCRechargeMsg = "failedEVCRechargeMsg";
    public static final String MESSAGE_KEY_systemErrorMsg = "systemErrorMsg";
    public static final String MESSAGE_KEY_onReqReceivedMsg = "onReqReceivedMsg";
    public static final String MESSAGE_KEY_invalidUSSDRequestStringMsg = "invalidUSSDRequestStringMsg";
    public static final String MESSAGE_KEY_subscriberBlacklistedMsg = "subscriberBlacklistedMsg";
    public static final String MESSAGE_KEY_autoLoanAlreadyOptInMsg = "autoLoanAlreadyOptInMsg";
    public static final String MESSAGE_KEY_autoLoanAlreadyOptOutMsg = "autoLoanAlreadyOptOutMsg";
    public static final String MESSAGE_KEY_autoLoanSuccessfulOptInBalanceGT100Msg = "autoLoanSuccessfulOptInBalanceGT100Msg";
    public static final String MESSAGE_KEY_autoLoanSuccessfulOptInBalanceLT100Msg = "autoLoanSuccessfulOptInBalanceLT100Msg";
    public static final String MESSAGE_KEY_autoLoanSuccessfulOptOutMsg = "autoLoanSuccessfulOptOutMsg";
    public static final String MESSAGE_KEY_autoLoanOptInFailedMsg = "autoLoanOptInFailedMsg";
    public static final String MESSAGE_KEY_autoLoanOptOutFailedMsg = "autoLoanOptOutFailedMsg";
    public static final String MESSAGE_KEY_autoLoanNotSubscribedMsg = "autoLoanNotSubscribedMsg";
    public static final String MESSAGE_KEY_autoLoanNotSubscribedAsLoanExistsMsg = "autoLoanNotSubscribedAsLoanExistsMsg";
    public static final String MESSAGE_KEY_customerTypeNotAllowedMsg = "customerTypeNotAllowedMsg";
    public static final String MESSAGE_KEY_deactivateLoanMsg = "deactivateLoanMsg";
    public static final String MESSAGE_KEY_activateLoanMsg = "activateLoanMsg";
    public static final String MESSAGE_KEY_borrowerHasOutstandingP2PLoanMsg = "borrowerHasOutstandingP2PLoanMsg";
    public static final String MESSAGE_KEY_subscriberHasPendingLoanMsg = "subscriberHasPendingLoanMsg";
    public static final String MESSAGE_KEY_subscriberHasPendingLoanReqMsg = "subscriberHasPendingLoanRequestMsg";
    public static final String MESSAGE_KEY_wrongAmountRequestedMsg = "wrongAmountRequestedMsg";
    public static final String MESSAGE_KEY_helpNotificationMsg = "helpNotificationMsg";
    public static final String MESSAGE_KEY_p2pIsLiveMsg = "p2pIsLive";
    public static final String SETTING_KEY_operation = "EasyCredit";
    public static final String SETTING_KEY_mainProductBlackList = "mainProductBlackList";
    public static final String SETTING_KEY_autoTopUpDefaultThreshold = "autoTopUpDefaultThreshold";
    public static final String SETTING_KEY_senderShortCode = "senderShortCode";
    public static final String SETTING_KEY_amountsConfigured = "amountsConfigured";
    public static final String SETTING_KEY_dupeRequestBlockTimeSeconds = "dupeRequestBlockTimeSeconds";
    public static final String MESSAGE_KEY_creditOnCreditIsOn = "cOncIsON";
    public static final String MESSAGE_KEY_FEATURE_AIRTIME = "airTime";
    public static final String MESSAGE_KEY_FEATURE_DATA = "data";
    public static final String MESSAGE_KEY_FEATURE_CREDIT_ON_CREDIT = "creditOnCredit";
    public static final String MESSAGE_KEY_FEATURE_SERVICE_PROVISIONING = "serviceProvisioning";
    public static final String MESSAGE_KEY_FEATURE_API_CHANNEL_IVR = "serviceAPIIVR";
    public static final String MESSAGE_KEY_FEATURE_API_CHANNEL_SERVICE_API_FACEBOOK = "serviceAPIFACEBOOK";
    public static final String MESSAGE_KEY_FEATURE_API_CHANNEL_SERVICE_API_SCORING = "checkScoring";
    public static final String MESSAGE_KEY_FEATURE_FACEBOOK_NOTIFICATION_SERVER_URL = "facebookNotifyServerURL";
    public static final String MESSAGE_KEY_FEATURE_FACEBOOK_CARRIER_ID = "facebookCarrierId";
    public static final String MESSAGE_KEY_FEATURE_FACEBOOK_SECRET_KEY = "facebook9MobileSecretKey";
    //Real Static Constants! 
    public static final int accountType = 2000;
    public static final int dataAccountType = 4507;
    public static final String successfulOCSresponseCode = "405000000";
    public static final String SETTING_KEY_changeappendantproductProductid = "changeappendantproductProductid";
    public static final String SETTING_KEY_changeappendantproductValidmode = "changeappendantproductValidmode";
    public static final String SETTING_KEY_ACTIVATELOAN_AIRTIME = "activateloanparamairtime";
    public static final String SETTING_KEY_ACTIVATELOAN_DATA = "activateloanparamdataloan";
    public static final String SETTING_KEY_FACEBOOK_DATA_BALANCE_QUALIFICATION_THRESHOLD = "faceBookDataBalanceForQualificationThreshold";
    public static final String SETTING_KEY_FACEBOOK_AIRTIME_BALANCE_QUALIFICATION_THRESHOLD = "faceBookAirtimeBalanceForQualificationThreshold";
    public static final String SETTING_KEY_MORE_CREDIT_MORNITORING_SMS_SENDER = "morecreditmonitoringsmssender";
    public static final String SETTING_KEY_MORE_CREDIT_BASE_URL = "moreCreditBaseURL";
    public static final String SETTING_KEY_MORE_SMS_MSG_TYPE = "moreCreditSMSMsgType";
    public static final String SETTING_KEY_P2P_BASE_URL_GET = "p2pBaseUrl";
    public static final String SETTING_KEY_OCS_BASE_URL = "ocsBaseUrl";
    public static final String SETTING_KEY_OCS_ACCOUNT_TYPE_URL = "ocsAccountTypeUrlBaseUrl";
    public static final String SETTING_KEY_BES_BE_ID = "besBeId";
    public static final String SETTING_KEY_BES_TYPE = "besType";
    public static final String SETTING_KEY_APP_NAME = "besAppName";
    public static final String SETTING_KEY_CREATE_SUPPLEMENTARY_OFFERING_MODIFICATION_ORDER = "createSupplementaryOfferingModificationOrder";
    public static final String SETTING_KEY_CREATE_MC_DISPATCHER_SERVICE_URL = "dispatcherServiceURL";
    public static final String SETTING_KEY_NEW_MORE_CREDIT_BASE_URL = "moreCreditNewBaseURL";
    public static final String SETTING_KEY_MC_AUTO_LOAN_SERVICE_URL = "autoLoanServiceInterface";
    public static final String SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_BASE_URL = "cbsAdapterInSpringBootBaseURL";
    public static final String SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_BASE_URL_Test_Env = "cbsAdapterInSpringBootBaseURLTestEnv";
    public static final String SETTING_KEY_CBS_ADAPTER_IN_SPRING_BOOT_QUERY_SUBS_ACCT_TYPE = "querySubscriberAccountTypeSpringBoot";
    
     public static final String SETTING_KEY_SUBSCRIBER_DOES_NOT_EXIST="querySubscriberAccountTypeSubNotExist";

}
