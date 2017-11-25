package com.future.yingyue.controller;


import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.base.SecurityUtils;
import com.future.yingyue.dto.AdminQueryDTO;
import com.future.yingyue.entity.Admin;
import com.future.yingyue.entity.AdminRole;
import com.future.yingyue.service.AdminService;
import com.future.yingyue.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ExcelService excelService;

    /**
     * hasAnyAuthority-url请求权限控制
     * @param @RequestParam(required = false) String keyword,
     * @param pageable
     * @param httpRequest
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @RequestMapping
    public ModelAndView index(AdminQueryDTO queryDTO, @PageableDefault Pageable pageable, HttpServletRequest httpRequest){
        ModelAndView model = new ModelAndView("admin-list");
        Page<Admin> page = adminService.getCustomers(queryDTO,pageable);
        List<AdminRole> adminRoles = adminService.findAllAdminRoles();
        model.addObject("page",page);
        model.addObject("queryDTO",queryDTO);
        model.addObject("adminRoles",adminRoles);
        return model;
    }

    @RequestMapping(path="/detail/{id}", method= RequestMethod.GET)
    public ModelAndView customerDetail(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView("admin-detail");
        Admin admin = adminService.findById(id);
        model.addObject("admin", admin);
        model.addObject("disabled", true);
        return model;
    }

    @RequestMapping(path="/edit/{id}", method=RequestMethod.GET)
    public ModelAndView editCustomer(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView("admin-edit");
        Admin admin = adminService.findById(id);
        model.addObject("admin", admin);
        model.addObject("disabled", false);
        return model;
    }
    @RequestMapping(path="/edit/{id}", method=RequestMethod.POST)
    public AjaxResponse editCustomer(@PathVariable Integer id,@RequestBody Admin admin) {
        AjaxResponse response = adminService.updateAdmin(id,admin);
        return response;
    }

    @RequestMapping(path="/delete/{id}", method=RequestMethod.POST)
    public AjaxResponse deleteCustomer(@PathVariable Integer id) {
        AjaxResponse ajaxResponse = adminService.deleteAdmin(id);
        return ajaxResponse;
    }

    @RequestMapping(path = "/import", method = RequestMethod.POST)
    public AjaxResponse<?> importExcel(HttpServletRequest request) throws Exception{
        AjaxResponse ajaxResponse = excelService.importAdmin(request);
        return ajaxResponse;
    }

    @RequestMapping(path = "/export",method = RequestMethod.GET)
    public AjaxResponse exportExcel(HttpServletResponse response) {
        excelService.exportAdmin(response);
        return AjaxResponse.success();
    }
}
