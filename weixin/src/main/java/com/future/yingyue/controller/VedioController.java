package com.future.yingyue.controller;


import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.dto.VedioQueryDTO;
import com.future.yingyue.entity.Vedio;
import com.future.yingyue.service.VedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/vedio")
public class VedioController {

    @Autowired
    private VedioService vedioService;

    /**
     * 视频列表
     * @return
     */
    @RequestMapping
    public ModelAndView vedioListView(@PageableDefault Pageable pageable){
        ModelAndView model = new ModelAndView("vedio-list");
        Page<Vedio> page = vedioService.findAll(null,pageable);
        model.addObject("page",page);
        return model;
    }

    @RequestMapping(path = "list/data",method = RequestMethod.GET)
    public AjaxResponse vedioListData(@PageableDefault Pageable pageable){
        Page<Vedio> page = vedioService.findAll(null,pageable);
        return AjaxResponse.success(page);
    }
}
