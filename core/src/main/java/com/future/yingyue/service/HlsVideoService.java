package com.future.yingyue.service;


import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.entity.HlsVideo;
import com.future.yingyue.entity.HlsVideoConfig;
import com.future.yingyue.ffmpeg.service.FFmpegManager;
import com.future.yingyue.repository.HlsVideoConfigRepository;
import com.future.yingyue.repository.HlsVideoRepository;
import com.future.yingyue.system.EPlatform;
import com.future.yingyue.system.OSinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * 监控
 */
@Service
@Transactional
public class HlsVideoService {

    @Autowired
    private HlsVideoConfigRepository videoConfigRepository;
    @Autowired
    private HlsVideoRepository videoRepository;
    @Autowired
    private Environment env;

    FFmpegManager fFmpegManager;

    /**
     * ffmpeg转码推流
     *  rtspPath  rtsp://admin:slkj0520@192.168.0.100:554/h264/ch1/main/av_stream
     *  hlsPath   D:/app/nginx-1.12.2/html/hls/slkj.m3u8
     */
    public void startFFmpeg(String showName,String command){
        if(fFmpegManager==null){
            fFmpegManager = new FFmpegManager();
        }
        fFmpegManager.start(showName,command);
    }

    /**
     * 停止转码
     * @param showName
     */
    public void stopFFmpeg(String showName){
        if(fFmpegManager==null){
            fFmpegManager = new FFmpegManager();
        }
        fFmpegManager.stop(showName);
    }

    /**
     * 初始化监控基础配置
     */
    public void initVideoConfig(){
        String nginxPath;
        if(OSinfo.getOSname()== EPlatform.Windows){
            nginxPath = env.getProperty("nginx.win.html.path");
        }else{
            nginxPath = env.getProperty("nginx.linux.html.path");
        }

        String nginxIp = env.getProperty("nginx.ip");

        int size = videoConfigRepository.findAll().size();
        if(size==0){
            HlsVideoConfig videoConfig = new HlsVideoConfig();
            videoConfig.setNginxIp(nginxIp);
            videoConfig.setNginxPath(nginxPath);
            videoConfigRepository.save(videoConfig);
        }

        videoRepository.resettingTranscoding();

    }

    public AjaxResponse updateVideoConfig(String nginxPath, String nginxIp){
        HlsVideoConfig videoConfig = findVideoConfig();
        videoConfig.setNginxPath(nginxPath);
        videoConfig.setNginxIp(nginxIp);
        return AjaxResponse.success();
    }

    /**
     * 查找监控基础配置
     * @return
     */
    public HlsVideoConfig findVideoConfig(){
        HlsVideoConfig one = videoConfigRepository.findOne(1);
        return one;
    }


    /**
     * 分页查找所有摄像头
     * @param pageable
     * @return
     */
    public Page<HlsVideo> findVideos(Pageable pageable){
        Page<HlsVideo> page = videoRepository.findAll(pageable);
        return page;
    }

    /**
     * 保存摄像机配置
     * @param video
     * @return
     */
    public AjaxResponse saveVideoMsg(HlsVideo video){
        try {
            String ipcName = env.getProperty("ipc.m3u8.name");
            HlsVideoConfig videoConfig = findVideoConfig();
            video.setRtspPath("rtsp://"+video.getUsername()+":"+video.getPassword()+"@"+video.getRtspIp()+
            "/h264/"+video.getChannel()+"/"+video.getStreamType()+"/av_stream");
            video.setM3u8Path(videoConfig.getNginxPath()+"/hls/"+video.getShowName()+"/"+ipcName);
            video.setVideoPath(videoConfig.getNginxIp()+"/hls/"+video.getShowName()+"/"+ipcName);
            video.setTranscoding(false);
            File file = new File(videoConfig.getNginxPath() + "/hls/" + video.getShowName());
            boolean b = file.mkdirs();
            if (b){
                videoRepository.save(video);
                return AjaxResponse.success();
            }else{
                return AjaxResponse.fail("存在同名文件");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.fail();
        }
    }

    // 判断文件夹是否存在
    public boolean judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("目录已存在");
                return true;
            } else {
                System.out.println("存在同名文件");
                return false;
            }
        } else {
            System.out.println("正在创建目录 ..."+file.getPath());
            file.mkdirs();
            return true;
        }
    }

    /**
     * 根据id查找摄像机
     * @param id
     * @return
     */
    public HlsVideo findVideo(Integer id){
        HlsVideo one = videoRepository.findOne(id);
        return one;
    }

    /**
     * 修改摄像机配置
     * @param id
     * @param video
     * @return
     */
    public AjaxResponse updateVideo(Integer id,HlsVideo video){
        return AjaxResponse.success();
    }


    /**
     * 摄像机转码
     * @param id
     * @return
     */
    public AjaxResponse transcoding(Integer id){
        HlsVideo video = findVideo(id);
        String cmd = "ffmpeg -i "+video.getRtspPath()+" -vcodec copy -acodec aac -ar 44100 -strict -2 -ac 1 -f hls -s 1280x720 -q 10 -hls_wrap 15 "+video.getM3u8Path();
        try {
            startFFmpeg(video.getShowName(),cmd);
            video.setTranscoding(true);
            return AjaxResponse.success("转码成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.fail("转码失败");
        }
    }

    /**
     * 停止转码
     * @param id
     * @return
     */
    public AjaxResponse stopTranscoding(Integer id){
        HlsVideo video = findVideo(id);
        video.setTranscoding(false);
        try {
            stopFFmpeg(video.getShowName());
            return AjaxResponse.success("已停止");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.fail("无法停止");
        }
    }

}
