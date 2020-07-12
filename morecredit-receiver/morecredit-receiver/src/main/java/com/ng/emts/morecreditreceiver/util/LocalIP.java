package com.ng.emts.morecreditreceiver.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalIP {

    private static String ipAddress;
    private static String hostName;

    static {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            ipAddress = ip.getHostAddress();
            hostName = ip.getHostName();
        } catch (UnknownHostException e) {
            System.err.println(e);
        }
    }

    public static String getLocalIP() {
        return ipAddress;
    }
    
    public static String getHostName() {
        return hostName;
    }
}
