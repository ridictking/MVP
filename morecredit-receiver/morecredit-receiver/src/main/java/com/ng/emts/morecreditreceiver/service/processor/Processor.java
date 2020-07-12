package com.ng.emts.morecreditreceiver.service.processor;

import com.ng.emts.morecreditreceiver.model.request.FormRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Processor {
    private final RequestProcessor requestProcessor;

    @Autowired
    public Processor(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }
    public void process(FormRequest request){
        requestProcessor.process(request);
    }
}
