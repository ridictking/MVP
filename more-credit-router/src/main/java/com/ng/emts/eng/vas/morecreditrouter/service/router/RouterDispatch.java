package com.ng.emts.eng.vas.morecreditrouter.service.router;

import com.ng.emts.eng.vas.morecreditrouter.model.response.ServiceRouter;
import com.ng.emts.eng.vas.morecreditrouter.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouterDispatch {
    private final AdminService adminService;

    @Autowired
    public RouterDispatch(AdminService adminService) {
        this.adminService = adminService;
    }
    public ServiceRouter GetRoute(String ussdString) {
        return adminService.getRoute(ussdString);
    }
}
