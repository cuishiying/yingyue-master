package com.future.yingyue.controller;


import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.dto.VedioQueryDTO;
import com.future.yingyue.entity.Vedio;
import com.future.yingyue.service.VedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ModelAndView vedioListView(@PageableDefault(value = 10,sort = "lastUpdateTime",direction = Sort.Direction.DESC) Pageable pageable){
        ModelAndView model = new ModelAndView("vedio-list");
        VedioQueryDTO vedioQueryDTO = new VedioQueryDTO();
        vedioQueryDTO.setOnline(true);
        Page<Vedio> page = vedioService.findAll(vedioQueryDTO,pageable);
        model.addObject("page",page);
        return model;
    }

    /**
     * 上拉加载数据
     * @param pageable
     * @return
     */
    @RequestMapping(path = "list/data",method = RequestMethod.GET)
    public AjaxResponse vedioListData(@PageableDefault(value = 10,sort = "lastUpdateTime",direction = Sort.Direction.DESC) Pageable pageable){
        VedioQueryDTO vedioQueryDTO = new VedioQueryDTO();
        vedioQueryDTO.setOnline(true);
        Page<Vedio> page = vedioService.findAll(vedioQueryDTO,pageable);
        return AjaxResponse.success(page);
    }

    /**
     * 播放视频
     * @param id
     * @return
     */
    @RequestMapping(path = "/detail/{id}",method = RequestMethod.GET)
    public ModelAndView vedioDetail(@PathVariable Integer id){
        ModelAndView model = new ModelAndView("vedio-detail");
        Vedio vedio = vedioService.findById(id);
        model.addObject("vedio",vedio);
        return model;
    }
}
