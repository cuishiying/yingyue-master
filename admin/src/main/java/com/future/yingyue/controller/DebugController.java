package com.future.yingyue.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @RequestMapping(path = "/hls",method = RequestMethod.GET)
    public ModelAndView debug(){
        ModelAndView model = new ModelAndView("vedio-hls");
        return model;
    }
}
