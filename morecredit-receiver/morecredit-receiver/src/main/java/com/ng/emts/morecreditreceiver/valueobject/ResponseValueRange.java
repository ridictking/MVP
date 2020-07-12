/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.valueobject;

/**
 *
 * @author victor.akinola
 */
public enum ResponseValueRange {

    SUCCESSFUL(0, "Status updated successfully."),
    UNFULFILLED_LOAN_IS_OUTSTANDING(1001, "Subscriber loan request was unfulfilled."),
    NETWORK_LIFETIME_X_LOWER(1002, "Network lifetime x lower."),
    SUBSCRIBER_NOT_IN_ACTIVE_BASE(1003, "subscriber not in active base "),
    INVALID_AMOUNT_X_REQUESTED(1004, "Invalid amount requested"),
    TRANSACTION_ALREADY_SUBMITTED(1005, "Transaction already submitted (Duplicate)"),
    UNFULFILLED_LOAN_SCORED_BELOW_AMOUNT_REQUESTED(1006, "Subscriber is eligible but asked for more than amount qualified for"),
    OCS_SYS_SUBSCRIBER_DOES_NOT_EXIST(6001, "Subscriber does not exist  - 118030260"),
    OCS_SYS_INTERNAL_ERROR_NO_DATA(6002, "Internal error: no data is found  - 118010106"),
    OCS_SYS_INTERNAL_ERROR_NULLPOINTER(6003, "Internal error: Null pointer  - 118010101"),
    OCS_SYS_ACCESS_MODE_3_DOES_NOT_EXIST(6004, "Access mode 3 does not exist  - 118032234"),
    OCS_SYS_QUEUE_DOES_NOT_EXIST(6005, "The queue does not exist.  - 412013300"),
    OCS_SYS_TARGET_QUEUE_FIND_FAILED(6006, "Failed to find the target queue.  - 412013110"),
    OCS_SYS_NO_ROUTING_INFORMATION(6007, "No routing information corresponds to the service.  - 412013511"),
    OCS_SYS_UNKNOWN_ERROR(6008, "System error. Unknown error - 118030478"),
    OCS_SYS_TRY_AGAIN(6009, "System Error. Please try again later."),
    OCS_SYS_OUTSTANDING_DEBT(6011, "Have outstanding debt of x   - 118030260"),
    OCS_SYS_NETWORK_LIFETIME_LOWER(6012, "Network lifetime x lower   - 118030260"),
    OCS_SYS_SUB_NOT_ACTIVE(6013, "Subscriber not in active base  - 118030260"),
    OCS_SYS_INVALID_AMOUNT_REQUESTED(6014, "Invalid amount: x requested.  - 118030260"),
    OCS_SYS_DUPLICATE_TRANSACTION(6016, "Transaction already submitted (Duplicate)   - 118030260"),
    OCS_SYS_BUSY(6017, "Service unavailable:System is busy"),
    OCS_OTHER_ERROR(6018, "OCS ErrorCode:{0}::OCS ErrorDesc:{1}"),
    EASYCREDIT_LOAN_EXISTS(2001, "Subscriber has existing Easy Credit Loan"),
    STAFF_LINE_INELIGIBLE(2002, "Staff Line cannot get loan."),
    POSTPAID_LINE_INELIGIBLE(2003, "Post paid line cannot get loan"),
    INVALID_VENDORID(2004, "Invalid Vendor ID: {0}."),
    INVALID_EVENT_TYPE(2005, "Invalid Event Type."),
    FUTURE_EVENT_TYPE(2006, "Invalid Event Type, Reserved For Future Use."),
    UNKNOWN_TRANSACTION(2007, "Unknown Transaction ID From Vendor."),
    UNKNOWN_VENDOR_TRANSACTION(2008, "Transaction ID Not Linked With Vendor."),
    MISSING_MSISDN(2009, "Missing MSISDN or Other required values."),
    VENDOR_TRANSACTION_MISMATCH(2010, "There is a mismatch between the vendor ID and transaction info."),
    DUPLICATE_TRANSACTION(2011, "Duplicate request within configured time window."),
    P2P_LOAN_EXISTS(2012, "Subscriber has existing P2P Loan"),
    EMPTY_RESPONSE_BODY(2017, "Empty Response Body"),
    SYSTEM_ERROR(2998, "System Error. Please try again later."),
    SERVICE_INACTIVE(2999, "The Easy Credit Service is not active yet."),
    EVC_DIAMETER_FAILURE(3001, "Diameter Failure - EVC"),
    EVC_UNKNOWN(3002, "Unknown - EVC"),
    EVC_TRADE_PARTNER_ACCOUNT_EXHAUSTED(3003, "Insufficient Funds in Trade partner account - EVC"),
    EVC_SYSTEM_VOLUMES_EXCEEDED(3004, "System volumes exceeded - EVC"),
    EVC_NO_LOAN_AMOUNT_CONFIGURED_IN_SYSTEM(3005, "Loan application failed because no loan amount is configured in the system - EVC"),
    EVC_EVC_FAILED_TO_RESPOND(3006, "Evc failed to respond - EVC"),
    EVC_SUB_NOT_ACTIVE(3007, "Loan application failed because the subscriber is not in the active state. - EVC"),
    AMOUNT_REQUESTED_REQUIRED(3008, "Amount requested required"),
    INVALID_TRANSACTION_TYPE(3009, "Invalid Vendor Transaction Type: {0}."),
    INVALID_MSISDN(3010, "Invalid MSISDN from Vendor: {0}."),
    AMOUNT_QUALIFIED_REQUIRED(3010, "Amount qulaified for required"),
    UNKNOWN_SERVICE_API(3011, "The Addtional_Info value is not a recognised service");

    private final Integer value;
    private final String description;

    private ResponseValueRange(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return this.value;
    }

    public static ResponseValueRange forValue(Integer value) {
        for (ResponseValueRange v : values()) {
            if (v.getValue().equals(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException("No ResponseValueRange value for parameter " + value);
    }

    public String getDescription() {
        return this.description;
    }
}
