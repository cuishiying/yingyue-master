package com.future.yingyue.controller;

import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.push.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/push")
public class PushController {

    @Bean
    public MyWebSocketHandler systemWebSocketHandler() {
        return new MyWebSocketHandler();
    }


    @RequestMapping(path = "/test",method = RequestMethod.GET)
    public ModelAndView test(){
        ModelAndView model = new ModelAndView("test");
        return model;
    }

    @RequestMapping(path = "/login/{userId}",method = RequestMethod.GET)
    @ResponseBody
    public String login(HttpSession session, @PathVariable("userId") Integer userId) {
        System.out.println("登录接口,userId="+userId);
        session.setAttribute("userId", userId);
        System.out.println(session.getAttribute("userId"));
        return "success";
    }

    @RequestMapping(path = "/message",method = RequestMethod.GET)
    @ResponseBody
    public String sendMessage() {
        boolean hasSend = systemWebSocketHandler().sendMessageToUser(4, new TextMessage("发送一条小xi"));
        System.out.println(hasSend);
        return "message";
    }
}
