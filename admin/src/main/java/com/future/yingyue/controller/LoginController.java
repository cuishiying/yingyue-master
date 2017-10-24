package com.future.yingyue.controller;

import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.entity.Admin;
import com.future.yingyue.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录注册
 */
@RestController
@RequestMapping("/account")
public class LoginController {
    @Autowired
    private AdminService adminService;



    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(HttpServletRequest request) {
        request.getSession().invalidate();
        ModelAndView model = new ModelAndView("account-login");
        return model;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public AjaxResponse login(String accountNumber, String password, HttpServletRequest request) {
        AjaxResponse ajaxResponse = adminService.login(accountNumber, password);
        if(ajaxResponse.isSuccess()){
            request.getSession().setAttribute("adminId", ajaxResponse.getData());
        }
        return ajaxResponse;
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("account-login");
        request.getSession().invalidate();
        return model;
    }

    @RequestMapping(path = "/regist", method = RequestMethod.GET)
    public ModelAndView registPage() {
        ModelAndView model = new ModelAndView("account-regist");
        return model;
    }

    @RequestMapping(path = "/regist", method = RequestMethod.POST)
    public AjaxResponse regist(@RequestBody Admin admin, String code) {
        try{
            AjaxResponse response = adminService.regist(admin);
            return response;
        }catch (Exception e){
            return AjaxResponse.fail("您的帐号已存在");
        }
    }

    @RequestMapping(path = "/sendCode", method = RequestMethod.POST)
    public AjaxResponse sendCode(String phone) {

        return AjaxResponse.success("验证码发送成功！");
    }

    @RequestMapping(value = "/checkCode", method = RequestMethod.GET)
    public AjaxResponse validCode(String phone, String code) {

        return AjaxResponse.success();
    }
    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("account-index");
        Integer empId = (Integer) request.getSession().getAttribute("adminId");
        if (empId == null) {
            model.setViewName("login");
        } else {
            Admin admin = adminService.findById(empId);
            model.addObject("admin", admin);
        }
        return model;
    }

    @RequestMapping(path = "/password", method = RequestMethod.GET)
    public ModelAndView password() {
        ModelAndView model = new ModelAndView("account-password");
        return model;
    }

    @RequestMapping(path = "/password", method = RequestMethod.POST)
    public AjaxResponse password(String phone, String code, String password) {
        AjaxResponse checked = this.validCode(phone, code);
        if (!checked.isSuccess()) return checked;
        AjaxResponse result = adminService.updatePassword(phone, password);
        return result;
    }
}
