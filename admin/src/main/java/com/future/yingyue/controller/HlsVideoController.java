package com.future.yingyue.controller;

import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.entity.HlsVideo;
import com.future.yingyue.entity.HlsVideoConfig;
import com.future.yingyue.enums.StreamType;
import com.future.yingyue.service.HlsVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 视频监控
 */
@RestController
@RequestMapping("/video")
public class HlsVideoController {


    @Autowired
    private HlsVideoService videoService;


    @RequestMapping(path = "/list",method = RequestMethod.GET)
    public ModelAndView videoListView(@PageableDefault(value = 10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        ModelAndView model = new ModelAndView("hlsvideo-list");
        Page<HlsVideo> page = videoService.findVideos(pageable);
        model.addObject("page",page);
        return model;
    }

    @RequestMapping(path = "/detail/{id}",method = RequestMethod.GET)
    public ModelAndView videoDetailView(@PathVariable Integer id){
        ModelAndView model = new ModelAndView("hlsvideo-detail");
        HlsVideo video = videoService.findVideo(id);
        model.addObject("ipcPath",video.getVideoPath());
        return model;
    }


    @RequestMapping(path = "/add",method = RequestMethod.GET)
    public ModelAndView addVideoView(){
        ModelAndView model = new ModelAndView("hlsvideo-add");
        model.addObject("streamType", StreamType.values());
        return model;
    }

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public AjaxResponse addVideo(@RequestBody HlsVideo video){
        AjaxResponse ajaxResponse = videoService.saveVideoMsg(video);
        return ajaxResponse;
    }


    @RequestMapping(path = "/transcoding/{id}",method = RequestMethod.GET)
    public AjaxResponse transcoding(@PathVariable Integer id){
        AjaxResponse ajaxResponse = videoService.transcoding(id);
        return ajaxResponse;
    }

    @RequestMapping(path = "/transcoding/stop/{id}",method = RequestMethod.GET)
    public AjaxResponse stopTranscoding(@PathVariable Integer id){
        AjaxResponse ajaxResponse = videoService.stopTranscoding(id);
        return ajaxResponse;
    }

    @RequestMapping(path = "/conf",method = RequestMethod.GET)
    public ModelAndView nginxConf(){
        ModelAndView model = new ModelAndView("hlsvideo-conf");
        HlsVideoConfig videoConfig = videoService.findVideoConfig();
        model.addObject("videoConfig",videoConfig);
        return model;
    }

    @RequestMapping(path = "/conf",method = RequestMethod.POST)
    public AjaxResponse updateVideoConfig(@RequestParam String nginxPath,@RequestParam String nginxIp){
        AjaxResponse ajaxResponse = videoService.updateVideoConfig(nginxPath, nginxIp);
        return ajaxResponse;
    }


}
