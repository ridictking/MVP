package com.ng.emts.eng.vas.morecreditrouter.service.admin;

import com.ng.emts.eng.vas.morecreditrouter.model.response.ServiceRouter;
import com.ng.emts.eng.vas.morecreditrouter.repo.ServiceRouterRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AdminService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, ServiceRouter> routerMap;
    private List<ServiceRouter> routers;

    private final  ServiceRouterRepo routerRepo;

    public AdminService(ServiceRouterRepo routerRepo) {
        this.routerRepo = routerRepo;
    }

    @PostConstruct
    public void init() {
        logger.info("#############################################");
        logger.info("###      Setting up the CACHE INSTANCE     #####");
        logger.info("#############################################");

        //process request router.
        routers = new CopyOnWriteArrayList<>(loadReqRouters());
        logger.info(String.format("%s routers loaded from db", routers.size()));
        //a map of string/router, for a more efficient key-based lookup before regexes.
        routerMap = new ConcurrentHashMap<>();
        for (ServiceRouter route : routers) {
            route.compilePattern();
            routerMap.put(route.getServiceString().toLowerCase(), route);
        }

        logger.info("Configurations loaded successfully from database");
    }

    private List<ServiceRouter> loadReqRouters(){
        List<ServiceRouter> reqRouters = new ArrayList<>();
        routerRepo.findAll().forEach(reqRouters::add);
        return reqRouters;
    }
    public ServiceRouter getRoute(String matchString) {

        logger.info(String.format("looking up route for %s", matchString));

        ServiceRouter route = routerMap.getOrDefault(matchString.toLowerCase(), null);
        if (route != null) {
            return route;
        }
        //key lookup failed, do regex lookup.

        for (ServiceRouter oneRoute : routers) {
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
}
