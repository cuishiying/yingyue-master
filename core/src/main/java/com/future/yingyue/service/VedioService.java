package com.future.yingyue.service;


import com.future.yingyue.base.AjaxResponse;
import com.future.yingyue.dto.VedioQueryDTO;
import com.future.yingyue.entity.Vedio;
import com.future.yingyue.repository.VedioRepository;
import com.future.yingyue.utils.StrUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiying on 2017/6/30.
 */
@Service
@Transactional
public class VedioService {

    @Autowired
    private VedioRepository vedioRepository;

    public AjaxResponse addVedio(Vedio vedio){
        String url = vedio.getUrl();
        Map<String, String> stringStringMap = StrUtils.URLRequest(url);
        String uu = stringStringMap.get("uu");
        String vu = stringStringMap.get("vu");
        vedio.setUu(uu);
        vedio.setVu(vu);
        vedio.setCreateTime(LocalDateTime.now());
        vedioRepository.save(vedio);
        return AjaxResponse.success("保存成功");
    }

    public Page<Vedio> findAll(VedioQueryDTO vedioQueryDTO,Pageable pageable){
        Specification<Vedio> specification = getWhereClause(vedioQueryDTO);
        Page<Vedio> page = vedioRepository.findAll(specification,pageable);
        return page;
    }

    public Vedio findById(Integer id){
        Vedio vedio = vedioRepository.findOne(id);
        return vedio;
    }

    public AjaxResponse onlineVedio(Integer id,boolean online){
        Vedio vedio = vedioRepository.findOne(id);
        vedio.setOnline(online);
        return AjaxResponse.success();
    }

    public AjaxResponse deleteVedio(Integer id){
        try{
            vedioRepository.delete(id);
            return AjaxResponse.success("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResponse.success("删除失败");
        }
    }

    /**
     * 查询条件
     * @param queryVo
     * @return
     */
    private Specification<Vedio> getWhereClause(VedioQueryDTO queryVo) {
        return (root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();

            //关键词
            if(queryVo!=null&& StringUtils.isNotBlank(queryVo.getKeyword())){
                predicate.add(cb.or(cb.like(root.<String>get("name"), "%" + queryVo.getKeyword().trim() + "%"),
                        cb.like(root.<String>get("intro"), "%" + queryVo.getKeyword().trim() + "%")));

            }
            //检查时间
            if (queryVo!=null&&queryVo.getBeginDate() != null) {
                LocalDateTime begin = LocalDateTime.of(queryVo.getBeginDate(), LocalTime.MIN);
                Predicate dateQuery = cb.greaterThanOrEqualTo(root.<LocalDateTime>get("createTime"), begin);
                predicate.add(dateQuery);
            }
            if (queryVo!=null&&queryVo.getEndDate() != null) {
                LocalDateTime end = LocalDateTime.of(queryVo.getEndDate(), LocalTime.MAX);
                Predicate dateQuery = cb.lessThanOrEqualTo(root.<LocalDateTime>get("createTime"), end);
                predicate.add(dateQuery);
            }

            return query.where(predicate.toArray(new Predicate[predicate.size()])).getRestriction();
        };
    }
}
