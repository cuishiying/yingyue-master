package com.future.yingyue.base;

import com.future.yingyue.entity.Admin;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vincent on 2017-2-6.
 */
public class SecurityUtils {
    public static AdminDetails getAdminDetail(HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        return auth == null ? null : (AdminDetails) auth.getPrincipal();
    }
    public static Admin getAdmin(HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        return auth == null ? null : ((AdminDetails) auth.getPrincipal()).getAdmin();
    }
    public static Integer getAdminId(HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        return auth == null ? null : ((AdminDetails) auth.getPrincipal()).getAdmin().getId();
    }

    public static boolean checkAccessId(HttpServletRequest request, Integer id) {
        if (id == null || id < 1) return false;
        AdminDetails adminDetails = SecurityUtils.getAdminDetail(request);
        if (adminDetails == null) return false;
        return id.equals(adminDetails.getId());
    }
}
