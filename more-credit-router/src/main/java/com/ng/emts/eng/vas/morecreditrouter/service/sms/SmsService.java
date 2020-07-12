package com.ng.emts.eng.vas.morecreditrouter.service.sms;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${com.emts.push.ussd.url}")
    private String ussdPushUrl;

    @Value("${com.emts.easycredit.smsurl}")
    private String url;

    private int maxTotal;
    //private  int defaut
    private final RestTemplate restTemplate;

    @Autowired
    public SmsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    private void setupHttpConnectionPoo(){
        if(StringUtils.isEmpty(url)) logger.error("SMS URL not set, add properties com.emts.easycredit.smsurl");
        logger.info(String.format("SMS Sender URL: %s", url));
    }

    public void SendSMS1(String msisdn, String settingValueBySettingName, String shortCode, String correlationId, String msgType) {

        if (StringUtils.isEmpty(shortCode)) {
            shortCode = "665";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<request><msisdn>")
                .append(msisdn)
                .append("</msisdn><serviceid>MoreCredit</serviceid><msg>")
                .append(settingValueBySettingName)
                .append("</msg><msgType>")
                .append(msgType)
                .append("</msgType><shortCode>")
                .append(shortCode)
                .append("</shortCode><correlationId>")
                .append(correlationId)
                .append("</correlationId></request>");

        logger.info(sb.toString());
//
//        try {
//            ResponseHandler<String> responseHandler = (final HttpResponse response) -> {
//                int status = response.getStatusLine().getStatusCode();
//                if (status >= 200 && status < 300) {
//                    HttpEntity entity = response.getEntity();
//                    return entity != null ? EntityUtils.toString(entity) : null;
//                } else {
//                    throw new ClientProtocolException("Unexpected response status: " + status);
//                }
//            };
//            HttpEntity entity = new StringEntity(sb.toString(), ContentType.create("application/xml", Consts.UTF_8));
//            HttpPost httppost = new HttpPost(url);
//            logger.info(String.format("SMS Sender Full-URL: %s", url));
//            httppost.setEntity(entity);
//            httppost.setHeader("Accept", "application/json,application/xml,text/xml");
//
//            String responseBody = httpclient.execute(httppost, responseHandler, HttpClientContext.create()); //using handler ensures resources are closed.
//            logger.info(String.format("response received: %s", responseBody));
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
    }

    public HttpClient httpClient(int maxTotal, int defaultMaxPerRoute){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
    }
}
