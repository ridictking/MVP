package com.ng.emts.eng.vas.morecreditrouter.service.rabbit;


public interface RouterService {
    void onReceiveMessage(String Message);
    void sendMessageToVendor(long channelId, long featureId);
    //void processFinancialRequest(ReceiverRequestData requestData);
    boolean pingVendor(String endPoint, int timeOutInMillis, int socketInMillis );
    //void processEligibilityStatusRequest(ReceiverRequestData reqData, Vendor vendor, ExecutionTimeUtil timeUtil, String jsonReqObj);
}
