package com.ng.emts.eng.vas.morecreditrouter.util;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getRawStatusCode() == 404
                || response.getRawStatusCode() == 502
                || response.getRawStatusCode() == 503
                || response.getRawStatusCode() == 504;
    }
}
