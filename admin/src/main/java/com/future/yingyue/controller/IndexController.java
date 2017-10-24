package com.future.yingyue.controller;

import com.future.yingyue.base.SecurityUtils;
import com.future.yingyue.entity.Admin;
import com.future.yingyue.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @RequestMapping
    public ModelAndView index(@RequestParam(required = false) String keyword, @PageableDefault Pageable pageable,HttpServletRequest httpRequest){
        Admin admin = SecurityUtils.getAdmin(httpRequest);
        ModelAndView model = new ModelAndView("index");
        return model;
    }

    @RequestMapping(path = "/userinfo",method = RequestMethod.GET)
    public ModelAndView userInfo(@RequestParam(required = false) String keyword, @PageableDefault Pageable pageable,HttpServletRequest httpRequest){
        Admin admin = SecurityUtils.getAdmin(httpRequest);
        return new ModelAndView("redirect:/admin/detail/"+admin.getId());
    }
}
