package com.ng.emts.eng.vas.morecreditrouter.service.cache;

import com.google.common.collect.Iterables;
import com.ng.emts.eng.vas.morecreditrouter.model.request.MoreCreditConstantsSettings;
import com.ng.emts.eng.vas.morecreditrouter.repo.MoreCreditConstantsSettingsRepo;
import com.ng.emts.eng.vas.morecreditrouter.repo.MoreCreditTagMessageRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemoryCache {
    private static final Logger logger = LoggerFactory.getLogger(MemoryCache.class);

    private final MoreCreditConstantsSettingsRepo settingsRepo;
    private final MoreCreditTagMessageRepo tagMessageRepo;

    private Map<String, String> settingsCache;
    Iterator<String> tagMessages;

    @Autowired
    public MemoryCache(MoreCreditConstantsSettingsRepo settingsRepo, MoreCreditTagMessageRepo tagMessageRepo) {
        this.settingsRepo = settingsRepo;
        this.tagMessageRepo = tagMessageRepo;
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

        logger.info("Configurations loaded successfully from database");
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

    public String getApplicationSetting(String key) {
        String value = settingsCache.get(key);
        logger.info(String.format("key=>%s, value=>%s", key, value));

        return settingsCache.get(key);
    }
}
