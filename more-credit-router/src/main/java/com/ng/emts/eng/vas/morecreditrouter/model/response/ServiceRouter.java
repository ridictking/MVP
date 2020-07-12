package com.ng.emts.eng.vas.morecreditrouter.model.response;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.regex.Pattern;

@Entity
@Table(name = "More_Credit_SR_Router_CSMS")
public class ServiceRouter implements Serializable {

    private static final long serialVersionUID = 6978683891429226019L;

    @Id
    private String serviceString;
    private String url;
    private String service;
    private String dispatchMethod;//expects xmlrpc or rest.
    private String proxyHandle;//for xmlrpc only.

    @Transient
    private Pattern pattern; //pattern

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDispatchMethod() {
        return dispatchMethod;
    }

    public void setDispatchMethod(String dispatchMethod) {
        this.dispatchMethod = dispatchMethod;
    }

    public String getProxyHandle() {
        return proxyHandle;
    }

    public void setProxyHandle(String proxyHandle) {
        this.proxyHandle = proxyHandle;
    }

    public String getServiceString() {
        return serviceString;
    }

    public void setServiceString(String serviceString) {
        this.serviceString = serviceString;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "Route{service=" + service + ", serviceString=" + serviceString + ", url=" + url + ", service=" + service + ", dispatchMethod=" + dispatchMethod + '}';
    }

    public Pattern retrievePattern() {
        return this.pattern;
    }

    public void compilePattern() {
        try {
            //doing this in setter of setMatchString should work, but I had issues once.
            pattern = Pattern.compile(this.serviceString);
        } catch (Exception e) {
        }
    }

}
