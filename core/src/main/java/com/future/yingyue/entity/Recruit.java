package com.future.yingyue.entity;


import com.future.yingyue.base.BaseEntity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * Created by cuishiying on 2017/6/13.
 * 招募信息
 */
@Entity
public class Recruit extends BaseEntity {

    private static final long serialVersionUID = -7620407921593414388L;
    private String title;//项目名称
    private String introduction;//项目介绍
    private LocalDateTime publicTime;//发布时间
    private LocalDateTime startTime;//报名开始时间
    private LocalDateTime endTime;//报名截至时间
    private String phone;
    private String contacts;//联系人
    private String address;//面试地址
    private String email;//公司邮箱
    private String poster;//海报

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public LocalDateTime getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(LocalDateTime publicTime) {
        this.publicTime = publicTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
