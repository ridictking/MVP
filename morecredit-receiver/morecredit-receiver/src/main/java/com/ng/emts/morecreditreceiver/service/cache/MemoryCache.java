package com.ng.emts.morecreditreceiver.service.cache;

import com.google.common.collect.Iterables;
import com.ng.emts.morecreditreceiver.model.request.MoreCreditConstantsSettings;
import com.ng.emts.morecreditreceiver.model.request.MoreCreditCustomerType;
import com.ng.emts.morecreditreceiver.model.request.MoreCreditReqRouter;
import com.ng.emts.morecreditreceiver.repo.MoreCreditConstantsSettingsRepo;
import com.ng.emts.morecreditreceiver.repo.MoreCreditCustomerTypeRepo;
import com.ng.emts.morecreditreceiver.repo.MoreCreditReqRouterDao;
import com.ng.emts.morecreditreceiver.repo.MoreCreditTagMessageRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemoryCache {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, String> settingsCache;
    Iterator<String> tagMessages;

    private Map<String, MoreCreditReqRouter> routerMap;//for a more efficient lookup.
    private List<MoreCreditCustomerType> customerTypes;
    private List<MoreCreditReqRouter> routers;

    private final MoreCreditConstantsSettingsRepo settingsRepo;
    private final MoreCreditTagMessageRepo tagMessageRepo;
    private final MoreCreditReqRouterDao reqRouterDao;
    private final MoreCreditCustomerTypeRepo customerTypeRepo;

    @Autowired
    public MemoryCache(MoreCreditConstantsSettingsRepo settingsRepo, MoreCreditTagMessageRepo tagMessageRepo, MoreCreditReqRouterDao reqRouterDao, MoreCreditCustomerTypeRepo customerTypeRepo) {
        this.settingsRepo = settingsRepo;
        this.tagMessageRepo = tagMessageRepo;
        this.reqRouterDao = reqRouterDao;
        this.customerTypeRepo = customerTypeRepo;
    }

    @PostConstruct
    public void init(){
        logger.info("#############################################");
        logger.info("###      Setting up the CACHE INSTANCE     #####");
        logger.info("#############################################");

        //application settings
        List<MoreCreditConstantsSettings> appSettings = loadApplicationSetting();
        settingsCache = new ConcurrentHashMap<>();
        for (MoreCreditConstantsSettings moreCreditSetting : appSettings) {
            settingsCache.put(moreCreditSetting.getSettingName(), moreCreditSetting.getSettingValue());
            logger.info(String.format("%s=>%s", moreCreditSetting.getSettingName(), moreCreditSetting.getSettingValue()));
        }
        logger.info("Done loading settings");
        //process all tag messages
        List<String> tagMsgList = loadTagMessages();
        for (String tag : tagMsgList) {
            logger.info(String.format("tag=>%s", tag));
        }
        //put inside iterable.cycle
        tagMessages = Iterables.cycle(tagMsgList).iterator();
        logger.info("Tags loaded successfully.");

        //process request router.
        routers = new CopyOnWriteArrayList<>(loadReqRouters());
        logger.info(String.format("%s routers loaded from db", routers.size()));
        //a map of string/router, for a more efficient key-based lookup before regexes.
        routerMap = new ConcurrentHashMap<>();
        for (MoreCreditReqRouter route : routers) {
            route.compilePattern();
            routerMap.put(route.getMatchString().toLowerCase(), route);
        }

        //process all customer types;
        customerTypes = new CopyOnWriteArrayList<>(loadCustomerTypes());
        logger.info(String.format("%s customer types loaded from db", customerTypes.size()));

        logger.info("Configurations loaded successfully from database");
    }

    public String nextTagMsg() {
        if (tagMessages.hasNext()) {
            return tagMessages.next();
        }
        return "";
    }

    public List<MoreCreditReqRouter> getRequestRouters() {
        return routers;
    }

    public List<MoreCreditCustomerType> getCustomerTypes() {
        return customerTypes;
    }

    public MoreCreditReqRouter getRoute(String matchString) {
        logger.info(String.format("looking up route for %s", matchString));

        MoreCreditReqRouter route = routerMap.getOrDefault(matchString.toLowerCase(), null);
        if (route != null) {
            return route;
        }
        //key lookup failed, do regex lookup.

        for (MoreCreditReqRouter oneRoute : routers) {
            Pattern pattern = oneRoute.retrievePattern();
            if (pattern == null) {
                return null;//could happen if regex is malformed or empty.
            }
            Matcher matcher = pattern.matcher(matchString);
            if (matcher.matches()) {
                return oneRoute;
            }
        }
        //no match
        return null;
    }

    public String getApplicationSetting(String key) {
        String value = settingsCache.get(key);
        logger.info(String.format("key=>%s, value=>%s", key, value));

        return settingsCache.get(key);
    }

    private List<MoreCreditConstantsSettings> loadApplicationSetting(){
        List<MoreCreditConstantsSettings> settings = new ArrayList<>();
        settingsRepo.findAll().forEach(settings::add);
        return settings;
    }
    private List<String> loadTagMessages(){
        List<String> tagMessages = new ArrayList<>();
        tagMessageRepo.findAll().forEach(x-> tagMessages.add(x.getMessage()));
        return tagMessages;
    }
    private List<MoreCreditReqRouter> loadReqRouters() {
        List<MoreCreditReqRouter> moreCreditReqRouters = new ArrayList<>();
        reqRouterDao.findAll().forEach(moreCreditReqRouters::add);
        return moreCreditReqRouters;
    }
    private List<MoreCreditCustomerType> loadCustomerTypes() {
        List<MoreCreditCustomerType> moreCreditCustomerTypes = new ArrayList<>();
        customerTypeRepo.findAll().forEach(moreCreditCustomerTypes::add);
        return moreCreditCustomerTypes;
    }
}
