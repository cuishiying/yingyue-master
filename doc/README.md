##山西未来科技有限公司

###项目名称：影约App

###1、项目说明：
    
        admin-------后台管理
                
                资金众筹
                演员招募
                剧本征集
                班前培训
                幕后合作
                发行合作
                商务合作
                活动大会
                
        api  -------接口
        core -------核心代码
        forend------PC前端
        parent------依赖管理
        weixin------m站
        
###2、流程说明：
        
        1、招募
        
            注册帐号--填写报名信息--审核--公开海选、才艺展示(初赛)--复赛--决赛--签约座谈--参演微电影
            
        2、报名
        
###3、接口：
        
        1、初始化接口
        2、导入导出接口
        3、pv统计
        
###4、直播方案：
        
        1、rtsp-ffmpeg-nginx-hls
        2、ffmpeg -i "rtsp://admin:slkj0520@192.168.0.100:554/h264/ch1/main/av_stream"  -vcodec copy -acodec aac -ar 44100 -strict -2 -ac 1 -f hls -s 1280x720 -q 10 -hls_wrap  15 D:/app/nginx-1.12.2/html/hls/slkj.m3u8