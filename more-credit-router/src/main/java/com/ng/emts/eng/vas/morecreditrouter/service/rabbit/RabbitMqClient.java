//package com.ng.emts.eng.vas.morecreditrouter.service.rabbit;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageListener;
//
//
//public class RabbitMqClient implements MessageListener {
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
////    public void receiveMessage(String message) {
////        logger.info("Receiving message from the inbound queue");
////        System.out.println("Received <" + message + ">");
////        logger.info("Received <" + message + ">");
////    }
//
//    @Override
//    public void onMessage(Message message) {
//        logger.info("Receiving message from the inbound queue");
//        System.out.println("Received <" + new String(message.toString()) + ">");
//        logger.info("Received <" + new String(message.getBody()) + ">");
//    }
//}
