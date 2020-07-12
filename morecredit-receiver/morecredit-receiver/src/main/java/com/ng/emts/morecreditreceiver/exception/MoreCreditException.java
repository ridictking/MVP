package com.ng.emts.morecreditreceiver.exception;


import com.ng.emts.morecreditreceiver.valueobject.MoreCreditResponse;
import com.ng.emts.morecreditreceiver.valueobject.Request;
import com.ng.emts.morecreditreceiver.valueobject.ResponseValueRange;

public class MoreCreditException extends Exception {

	private static final long serialVersionUID = 8940340400055595812L;
	private MoreCreditResponse response;
	private Request request;
	
	public MoreCreditException(Throwable cause){
		super(cause);
	}

	public MoreCreditException(int responseCode, String responseDescription) {
		response = new MoreCreditResponse(responseCode, responseDescription);
    }
	
	public MoreCreditException(ResponseValueRange futureEventType) {
		this(futureEventType.getValue(), futureEventType.getDescription());
	}

	public MoreCreditResponse getResponse(){
		return response;
	}
	
	//include the original request if available for easy logging
	public void setRequest(Request request){
		this.request = request;
	}
	
	public Request getRequest(){
		return request;
	}
}
