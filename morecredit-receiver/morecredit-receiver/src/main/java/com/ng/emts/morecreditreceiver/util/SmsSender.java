package com.ng.emts.morecreditreceiver.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SmsSender {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    final String fallbackUrl = "http://10.161.10.203:8089/sms/rest/submit/send-sms";
    //final String fallbackUrl = null;
    final String ussdPushUrl = "http://10.161.11.66:8280/sms/rest/submit/send-ussd-push";
//
//    String url = "";
//    PoolingHttpClientConnectionManager connMgr = null;
//    HttpClientContext context = null;
//    HttpRoute route = null;
//    CloseableHttpClient httpclient = null;
//    com.mashape.unirest.http.HttpResponse<String> responseBody = null;

    @PostConstruct
    private void SetupHttpConnectionPool() {

//        URI uri;
//        try {
//            url = System.getProperty("com.emts.easycredit.smsurl");
//            if (StringUtils.isEmpty(url)) {
//                logger.error("SMS URL not set, add system-properties com.emts.easycredit.smsurl");
//                url = fallbackUrl;
//            }
//            logger.info(String.format("SMS Sender URL: %s", url));
//            uri = new URI(url);
//        } catch (URISyntaxException e) {
//            logger.error(String.format("Cannot continue: %s", e.getMessage(), e));
//            return;
//        }
//
//        connMgr = new PoolingHttpClientConnectionManager();
//        route = new HttpRoute(new HttpHost(uri.getHost(), uri.getPort()));
//        connMgr.setMaxPerRoute(route, 200);
//        connMgr.setMaxTotal(200);
//        httpclient = HttpClients.custom().setConnectionManager(connMgr).build();
        //httpclient = HttpClients.custom().setConnectionManager(connMgr).setConnectionManagerShared(true).build();
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

    public void SendUSSDPush(String msisdn, String msg, String shortCode, String correlationId) {

        if (StringUtils.isEmpty(shortCode)) {
            shortCode = "1111";
        }

//        responseBody = Unirest.post(ussdPushUrl)
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .body("correlationId=" + correlationId + "&serviceid=" + "MoreCredit" + "&msg=" + msg  + "&shortCode=" + shortCode + "&msisdn=" + msisdn).asString();
    }

    
}
