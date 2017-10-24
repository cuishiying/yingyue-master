package com.future.yingyue.service;

import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.entity.Recruit;
import com.future.yingyue.repository.RecruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by cuishiying on 2017/6/16.
 */
@Service
@Transactional
public class RecruitService {

    @Autowired
    private RecruitRepository recruitRepository;

    public AjaxResponse releaseMessage(Recruit recruit){
        recruit.setPublicTime(LocalDateTime.now());
        recruitRepository.save(recruit);
        return AjaxResponse.success();
    }

    public Page<Recruit> findAll(Pageable pageable){
        Page<Recruit> page = recruitRepository.findAll(pageable);
        return page;
    }

    public AjaxResponse deleteRecruit(Integer id){
        try{
            recruitRepository.delete(id);
            return AjaxResponse.success("删除成功");
        }catch (Exception e){
           e.printStackTrace();
            return AjaxResponse.fail("删除失败");
        }
    }
}
