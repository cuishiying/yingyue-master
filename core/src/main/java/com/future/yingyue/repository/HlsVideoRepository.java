package com.future.yingyue.repository;

import com.future.yingyue.entity.HlsVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HlsVideoRepository extends JpaRepository<HlsVideo,Integer>{

    /**
     * 重置转码状态
     * @return
     */
    @Modifying
    @Query("update HlsVideo v set v.transcoding = false")
    Integer resettingTranscoding();

}
