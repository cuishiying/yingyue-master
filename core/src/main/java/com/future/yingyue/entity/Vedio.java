package com.future.yingyue.entity;


import com.future.yingyue.base.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Created by cuishiying on 2017/6/25.
 */
@Entity
public class Vedio extends BaseEntity {
    private static final long serialVersionUID = 114881824199181392L;

    private String name;
    private String intro;//简介
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;
    private String editManage;//管理者
    private String duration;//时长
    private String url;
    private String imageUrl;
    private String category;
    private String remark;
    private String uu;
    private String vu;
    private boolean online;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Admin createdAdmin; // 创建人

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getEditManage() {
        return editManage;
    }

    public void setEditManage(String editManage) {
        this.editManage = editManage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUu() {
        return uu;
    }

    public void setUu(String uu) {
        this.uu = uu;
    }

    public String getVu() {
        return vu;
    }

    public void setVu(String vu) {
        this.vu = vu;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Admin getCreatedAdmin() {
        return createdAdmin;
    }

    public void setCreatedAdmin(Admin createdAdmin) {
        this.createdAdmin = createdAdmin;
    }
}
