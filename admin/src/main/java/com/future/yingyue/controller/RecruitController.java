package com.future.yingyue.controller;

import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.entity.Recruit;
import com.future.yingyue.service.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 招募模块
 */

@RestController
@RequestMapping("/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    /**
     * 信息发布
     * @return
     */
    @RequestMapping(path = "/releaseMessage",method = RequestMethod.GET)
    public ModelAndView releaseMessageView(){
        ModelAndView model = new ModelAndView("recruit-release");
        return model;
    }
    /**
     * 信息发布
     * @return
     */
    @RequestMapping(path = "/releaseMessage",method = RequestMethod.POST)
    public AjaxResponse releaseMessage(@RequestBody Recruit recruit){
        AjaxResponse ajaxResponse = recruitService.releaseMessage(recruit);
        return ajaxResponse;
    }
    /**
     * 招募管理
     * @return
     */
    @RequestMapping(path = "/recruitManage",method = RequestMethod.GET)
    public ModelAndView recruitManageView(@PageableDefault Pageable pageable){
        ModelAndView model = new ModelAndView("recruit-manage");
        Page<Recruit> page = recruitService.findAll(pageable);
        model.addObject("page",page);
        return model;
    }
    /**
     * 报名管理
     * @return
     */
    @RequestMapping(path = "/signupManage",method = RequestMethod.GET)
    public ModelAndView signupManageView(@RequestParam(required = false) String keyword, @PageableDefault Pageable pageable){
        ModelAndView model = new ModelAndView("recruit-signup");
        Page<Recruit> page = recruitService.findAll(pageable);
        model.addObject("page",page);
        model.addObject("keyword", keyword);
        return model;
    }

    /**
     * 删除招募信息
     * @param id
     * @return
     */
    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public AjaxResponse deleteReleaseMessage(@PathVariable Integer id){
        AjaxResponse response = recruitService.deleteRecruit(id);
        return response;
    }
}
