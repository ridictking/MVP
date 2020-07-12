package com.ng.emts.eng.vas.morecreditrouter.service.jms;


import com.ng.emts.eng.vas.morecreditrouter.model.request.FormRequest;
import com.ng.emts.eng.vas.morecreditrouter.model.request.TransmitterLog;
import com.ng.emts.eng.vas.morecreditrouter.repo.TransmitterLogRepo;
import com.ng.emts.eng.vas.morecreditrouter.util.GlobalMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class QueueProducer {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final TransmitterLogRepo repo;
    @Autowired private JmsTemplate jmsTemplate;
    @Autowired private RabbitTemplate rabbitTemplate;
    @Value("${com.ng.emts.receiver.exchange.name}")
    private String exchange;

    @Autowired
    public QueueProducer(TransmitterLogRepo repo) {
        this.repo = repo;
    }

    public void queueRequest(Map<String, String> parameters) throws JMSException {
        Long transactionId = GlobalMethods.generateTransactionId();
        parameters.putIfAbsent("correlationId", String.valueOf(transactionId));
        jmsTemplate.convertAndSend("java:jboss/jms/queue/routerserv",parameters);
    }
    public void queueRequestv1(Map<String, String> parameters){
        Long transactionId = GlobalMethods.generateTransactionId();
        parameters.putIfAbsent("correlationId", String.valueOf(transactionId));
        rabbitTemplate.convertAndSend(exchange,"key", parameters);
    }

    public void queueRequestV2(FormRequest parameters){
        Long transactionId = GlobalMethods.generateTransactionId();
        parameters.setCorrelationId(String.valueOf(transactionId));
        rabbitTemplate.convertAndSend(exchange,"key", parameters);
        logToDb(parameters.getSourceIp(),
                parameters.getMsisdn(),
                parameters.getMessage(),
                "",parameters.getCorrelationId(),
                "","",new HashMap<>(),
                parameters.getTransactionId());
    }

    private void logToDb(String ipAddress, String msisdn, String message, String messageId, String correlationId, String middlewareUrl, String responseBody, Map<String, String> postParameters, String transactionId) {
        TransmitterLog log = new TransmitterLog();
        log.setCorrelationId(correlationId);
        log.setIpAddress(ipAddress);
        log.setMessage(message);
        log.setMessageId(messageId);
        log.setMsisdn(msisdn);
        log.setPostParameters(postParameters.toString());
        log.setResponseBody(responseBody);
        log.setTransactionDate(new Date());
        log.setUrl(middlewareUrl);
        log.setTransctionId(transactionId);
        try {
            repo.save(log);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
