package com.ng.emts.morecreditreceiver.service.rabbit;

import com.google.gson.Gson;
import com.ng.emts.morecreditreceiver.model.request.FormRequest;
import com.ng.emts.morecreditreceiver.service.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;


public class QueueConsumer implements MessageListener {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Processor processor;

    @Autowired
    public QueueConsumer(Processor processor) {
        this.processor = processor;
    }
    //    public void receiveMessage(String message) {
//        logger.info("Receiving message from the inbound queue");
//        System.out.println("Received <" + message + ">");
//        logger.info("Received <" + message + ">");
//    }

    @Override
    public void onMessage(Message message) {
        logger.info("Receiving message from the inbound queue");
        System.out.println("Received <" + new String(message.toString()) + ">");
        logger.info("Received <" + new String(message.getBody()) + ">");
        logger.info("Converting received message");
        Gson gson = new Gson();
        FormRequest request = gson.fromJson(new String(message.getBody()),FormRequest.class);
        logger.info(request.toString());
        processor.process(request);
    }
}
