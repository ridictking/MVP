package com.ng.emts.eng.vas.morecreditrouter.service.jms;

import com.ng.emts.eng.vas.morecreditrouter.model.request.FormRequest;
import com.ng.emts.eng.vas.morecreditrouter.model.response.ServiceRouter;
import com.ng.emts.eng.vas.morecreditrouter.service.cache.MemoryCache;
import com.ng.emts.eng.vas.morecreditrouter.service.router.RouterDispatch;
import com.ng.emts.eng.vas.morecreditrouter.service.router.routerclient.RouterClient;
import com.ng.emts.eng.vas.morecreditrouter.util.Config;
import com.ng.emts.eng.vas.morecreditrouter.util.GlobalMethods;
import com.ng.emts.eng.vas.morecreditrouter.util.MoreCreditConstantsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class QueueConsumer implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(QueueConsumer.class.getName());

    private final MemoryCache cache;
    private final Config config;
    private final RouterDispatch routerDispatch;
    private final RouterClient client;
    private String getBaseURL;

    @Autowired
    public QueueConsumer(MemoryCache cache, Config config, RouterDispatch routerDispatch, RouterClient client) {
        this.cache = cache;
        this.config = config;
        this.routerDispatch = routerDispatch;
        this.client = client;
    }

    @PostConstruct
    public void init(){
        getBaseURL = cache.getApplicationSetting(MoreCreditConstantsUtil.SETTING_KEY_NEW_MORE_CREDIT_BASE_URL);
    }


    @JmsListener(destination = "java:jboss/jms/queue/routerserv")
    public void receiveMessage(Message message){
        MapMessage msg = null;

        Map<String, String> map = new HashMap<>();

        try {
            if ((message instanceof MapMessage)) {
                msg = (MapMessage) message;

                //Enumeration<String> incoming = msg.getMapNames();
                Enumeration incoming = msg.getMapNames();
                while (incoming.hasMoreElements()) {
                    String key = (String) incoming.nextElement();
                    String value = msg.getString(key);
                    map.put(key, value);
                }
                logger.info(String.format("QueueReceiver Processing message >>>>>>>>: %s", map.toString()));
                processMessage(map);
            } else {
                logger.error("Message of wrong type: " + message.getClass().getName());
            }
        } catch (JMSException ex) {
            logger.error("QueueReceiver:::JMSException : " + ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.error("QueueReceiver:::Exception: "+ ex.getMessage(), ex);
        }
    }

    private void processMessage(Map<String,String> map){
        FormRequest request = new FormRequest();
        request.setServiceString(map.get("serviceString"));
        request.setMsisdn(map.get("msisdn"));
        request.setCorrelationId(map.get("correlationId"));
        request.setAmountRequested(map.get("amountrequested"));
        request.setTransactionId(map.get("transaction_id"));
        request.setMedium(map.get("medium"));
        request.setRequestType(map.get("request_type"));
        request.setAdditionalInfo(map.get("additional_info"));
        request.setTransactionType(map.get("transaction_type"));
        request.setTranId(GlobalMethods.generateTransactionId());
        request.setSourceIp(map.get("sourceIp"));
        request.setMessage(map.get("paramx"));
        request.setSessionId(map.get("session_id"));
        request.setReqType(map.get("req_type"));

        String bindAddress = System.getProperty("jboss.bind.address");
        //String bindAddress = config.getBindAddress();

        try{
            logger.info("Router map requests.... ::: ");
            ServiceRouter route = routerDispatch.GetRoute(request.getServiceString());
            String url = getBaseURL+route.getUrl();
            client.processForm(request,bindAddress,route,url,map);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public void onMessage(org.springframework.amqp.core.Message message) {

    }


    public enum ServiceString {
        SERCOM_USSD,
        SERCOM_SMS,
        SERVICE_API_IVR,
        SERCOM_PORTAL,
        IPCC_USSD
    }

}
