package com.future.yingyue.controller;

import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.base.sms.*;
import com.future.yingyue.entity.Admin;
import com.future.yingyue.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private VerifyCodeService verifyCodeService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private SmsProperties smsProperties;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(HttpServletRequest request) {
        request.getSession().invalidate();
        ModelAndView model = new ModelAndView("customer-login");
        return model;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public AjaxResponse login(String accountNumber, String password, HttpServletRequest request) {
        AjaxResponse result = adminService.login(accountNumber, password);
        if (result.isSuccess()) {
            request.getSession().setAttribute("customerId", result.getData());
            result.setData(null);
        }
        return result;
    }
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("customer-login");
        request.getSession().invalidate();
        return model;
    }

    @RequestMapping(path = "/regist", method = RequestMethod.GET)
    public ModelAndView registPage() {
        ModelAndView model = new ModelAndView("customer-regist");
        return model;
    }

    @RequestMapping(path = "/regist", method = RequestMethod.POST)
    public AjaxResponse regist(@RequestBody Admin customer, String code) {
//        AjaxResponse checked = this.validCode(customer.getPhone(), code);
//        if (!checked.isSuccess()) return checked;
        AjaxResponse result = adminService.regist(customer);
        return result;
    }

    @RequestMapping(path = "/sendCode", method = RequestMethod.POST)
    public AjaxResponse sendCode(String phone) {
        String code = "";
        if (!this.validPhone(phone))
            return AjaxResponse.fail("手机号格式不正确");
        try{
            // TODO: 2017/11/11  
           code = verifyCodeService.get(phone);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResponse.fail("验证码发送失败！");
        }

        SmsParams params = new SmsParams();
        params.setCode(code);
        params.setProduct("莎蔓莉莎");
        Future<Boolean> future = smsService.send(phone, smsProperties.getRegisterCode(), params);
        try {
            if (!future.get()) {
                return AjaxResponse.fail("验证码发送失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResponse.success("验证码发送成功！");
    }

    @RequestMapping(value = "/checkCode", method = RequestMethod.GET)
    public AjaxResponse validCode(String phone, String code) {
        if (!this.validPhone(phone)) {
            return AjaxResponse.fail("手机号格式不正确！");
        }
        VerifyResult validate = verifyCodeService.validate(phone, code);
        return validate.isSuccess() ? AjaxResponse.success() : AjaxResponse.fail(validate.getMessage());
    }

    @RequestMapping(path = "/password", method = RequestMethod.GET)
    public ModelAndView password() {
        ModelAndView model = new ModelAndView("customer-password");
        return model;
    }

    @RequestMapping(path = "/password", method = RequestMethod.POST)
    public AjaxResponse password(String phone, String code, String password) {
        AjaxResponse checked = this.validCode(phone, code);
        if (!checked.isSuccess()) return checked;
        AjaxResponse result = adminService.updatePassword(phone, password);
        return result;
    }

    @RequestMapping(path = "/phone", method = RequestMethod.GET)
    public ModelAndView phone(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("customer-phone");
        Integer empId = (Integer) request.getSession().getAttribute("adminId");
        if (empId == null) model.setViewName("customer-login");
        return model;
    }

    @RequestMapping(path = "/phone", method = RequestMethod.POST)
    public AjaxResponse phone(String phone, String code, HttpServletRequest request) {
        Integer empId = (Integer) request.getSession().getAttribute("adminId");
        if (empId == null) {
            return AjaxResponse.fail("当前未登录");
        } else {
            AjaxResponse checked = this.validCode(phone, code);
            if (!checked.isSuccess()) return checked;
            AjaxResponse result = adminService.updatePhone(empId, phone);
            return result;
        }
    }

    private boolean validPhone(String phone) {
        // return Pattern.matches("((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}", phone);
        return Pattern.matches("1\\d{10}", phone);
    }
}
