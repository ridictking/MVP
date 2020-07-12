package com.ng.emts.morecreditreceiver.service.rabbit;

import com.ng.emts.morecreditreceiver.config.RabbitMqConfig;
import com.ng.emts.morecreditreceiver.model.request.FormRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QueueListener {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${com.ng.emts.receiver.queue.name}")
    private String routerQueue;

//    @RabbitListener(queues = "${com.ng.emts.receiver.queue.name}")
//    public void receiveMessage(FormRequest request){
//        logger.info(request.toString());
//    }
}
