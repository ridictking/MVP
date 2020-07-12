/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ng.emts.morecreditreceiver.util;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.*;

/**
 *
 * @author victor.akinola
 */
public class GlobalMethods {

    private  static final Logger logger = LoggerFactory.getLogger(GlobalMethods.class);

    private final Random random = new Random(hashCode());

       
    public String toString(Map result) {
        StringBuilder retstring = new StringBuilder("[{");
        // Construct a comma separated toString()
        Set<String> keys = result.keySet();
        int n = keys.size();
        for (String key : keys) {
            if (--n == 0) {
                retstring.append(key).append("=").append(result.get(key)).append("}]");
            } else {
                retstring.append("").append(key).append("=").append(result.get(key)).append(", ");
            }
        }
        return retstring.toString();
    }


    private static String getDayofWeek(int dateIndex) {
        String day = "";
        try {
            switch (dateIndex) {
                case 1:
                    day = "Sunday";
                    break;
                case 2:
                    day = "Monday";
                    break;
                case 3:
                    day = "Tuesday";
                    break;
                case 4:
                    day = "Wednesday";
                    break;
                case 5:
                    day = "Thursday";
                    break;
                case 6:
                    day = "Friday";
                    break;
                case 7:
                    day = "Saturday";
                    break;
            }
        } catch (Exception ex) {
            logger.error("Exception/Error inside getDayofWeek() <<: ", ex);
        }
        return day.toUpperCase();
    }

    public static synchronized long generateTransactionId() {
        String randomString = Long.toString(System.currentTimeMillis()) + Integer.toString(Math.abs(new Random().nextInt(99999)));
        return Long.parseLong(randomString);
    }

    public static String generateUUID1() {
        Random rand = new Random();
        int START = 0;
        int END = 9;
        String longUuid = "";
        try {
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString().replaceAll("(\\p{Alpha})", String.valueOf(showRandomInteger(START, END, rand)));
            if (randomUUIDString.startsWith("0", START)) {
                randomUUIDString = randomUUIDString.substring(1);
            }
            longUuid = randomUUIDString.replaceAll("-", "").substring(0, 19);
        } catch (Exception e) {
            logger.error("Exception/Error inside generateUUID() <<: ", e);
        }
        return longUuid;
    }

    private static int showRandomInteger(int aStart, int aEnd, Random aRandom) {
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long) aEnd - (long) aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long) (range * aRandom.nextDouble());
        return (int) (fraction + aStart);
    }

//    public static long generateUUID() {
//        UUID uuid = Generators.timeBasedGenerator(EthernetAddress.fromInterface()).generate();
//        return uuid.timestamp();
//    }

    public static String stripMsisdn(String msisdn) {
        if (msisdn.startsWith("+234")) {
            msisdn = msisdn.substring(4);
        } else if (msisdn.startsWith("234")) {
            msisdn = msisdn.substring(3);
        } else if (msisdn.startsWith("0")) {
            msisdn = msisdn.substring(1);
        }
        return msisdn;
    }

    public static Timestamp setSubscriptionDate() {
        Calendar today = Calendar.getInstance();
        return (new Timestamp(today.getTimeInMillis()));
    }

    public static String formatMessage(String message, String[] parameters) {
        logger.info(message + " Entering formatMessage..................  ");
        String formatMessage = message;
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                String holder = "{" + i + "}";
                if (formatMessage.contains(holder)) {
                    formatMessage = formatMessage.replace(holder, parameters[i]);
                }
            }
        }
        logger.info(" Leaving formatMessage..................  " + formatMessage);
        return formatMessage;
    }

    public static boolean checkInputParameter(String... args) {
        boolean valid = false;
        for (String arg : args) {
            valid |= Strings.isNullOrEmpty((arg == null ? arg : arg.trim()));
        }
        return valid;
    }
}
