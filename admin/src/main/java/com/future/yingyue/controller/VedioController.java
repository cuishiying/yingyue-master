package com.future.yingyue.controller;

import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.dto.VedioQueryDTO;
import com.future.yingyue.entity.Vedio;
import com.future.yingyue.service.VedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by cuishiying on 2017/6/25.
 */
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
    public ModelAndView vedioListView(VedioQueryDTO vedioQueryDTO, @PageableDefault Pageable pageable){
        ModelAndView model = new ModelAndView("vedio-list");
        Page<Vedio> page = vedioService.findAll(vedioQueryDTO,pageable);
        model.addObject("page",page);
        model.addObject("queryDTO",vedioQueryDTO);
        return model;
    }

    /**
     * 视频上传
     * @return
     */
    @RequestMapping(path = "/add",method = RequestMethod.GET)
    public ModelAndView addVedioView(){
        ModelAndView model = new ModelAndView("vedio-add");
        return model;
    }

    /**
     * 视频上传
     * @return
     */
    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public AjaxResponse addVedio(@RequestBody Vedio vidio){
        AjaxResponse ajaxResponse = vedioService.addVedio(vidio);
        return ajaxResponse;
    }

    /**
     * 视频编辑
     * @return
     */
    @RequestMapping(path = "/update",method = RequestMethod.GET)
    public ModelAndView updateVedioView(){
        ModelAndView model = new ModelAndView("vedio-update");
        return model;
    }

    /**
     * 视频编辑
     * @return
     */
    @RequestMapping(path = "/update",method = RequestMethod.POST)
    public AjaxResponse updateVedio(@RequestBody Vedio vedio){
        return null;
    }

    /**
     * 删除视频
     * @return
     */
    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public AjaxResponse deleteVedio(@PathVariable Integer id){
        return vedioService.deleteVedio(id);
    }


    /**
     * 视频上下线
     * @param id
     * @return
     */
    @RequestMapping(path = "/online/{id}",method = RequestMethod.POST)
    public AjaxResponse grapCustomer(@PathVariable Integer id, @RequestParam boolean online){
        AjaxResponse ajaxResponse = vedioService.onlineVedio(id, online);
        return ajaxResponse;
    }

    /**
     * 播放视频
     * @param id
     * @return
     */
    @RequestMapping(path = "/play/{id}",method = RequestMethod.GET)
    public ModelAndView vedioDetail(@PathVariable Integer id){
        ModelAndView model = new ModelAndView("vedio-play");
        Vedio vedio = vedioService.findById(id);
        model.addObject("vedio",vedio);
        return model;
    }
}
