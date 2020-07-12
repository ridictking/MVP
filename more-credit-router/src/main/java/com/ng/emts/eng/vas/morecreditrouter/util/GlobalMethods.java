package com.ng.emts.eng.vas.morecreditrouter.util;




import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public class GlobalMethods {
    public static synchronized long generateTransactionId() {
        String randomString = System.currentTimeMillis() + Integer.toString(Math.abs(new Random().nextInt(99999)));
        return Long.parseLong(randomString);
    }
    public static boolean checkInputParameter(String... args) {
        boolean valid = false;
        for (String arg : args) {
            valid = StringUtils.isBlank(arg);
            if(!valid) StringUtils.strip(arg);
            //valid |= Strings.isNullOrEmpty((arg == null ? arg : arg.trim()));
        }
        return valid;
    }
}
