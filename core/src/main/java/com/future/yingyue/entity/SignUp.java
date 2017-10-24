package com.future.yingyue.entity;


import com.future.yingyue.base.BaseEntity;
import com.future.yingyue.enums.AuditStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * Created by cuishiying on 2017/6/13.
 * 报名信息
 */
@Entity
public class SignUp extends BaseEntity {
    private static final long serialVersionUID = -2531891436761592915L;
    private String name;
    private String nickName;//昵称
    private String phone;
    private String email;
    private String cardA;//身份证正面
    private String cardB;//身份证反面
    private String signupItem;//报名项目
    private LocalDateTime signTime;//报名时间
    @Enumerated(EnumType.STRING)
    private AuditStatus auditStatus; //会员状态


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardA() {
        return cardA;
    }

    public void setCardA(String cardA) {
        this.cardA = cardA;
    }

    public String getCardB() {
        return cardB;
    }

    public void setCardB(String cardB) {
        this.cardB = cardB;
    }

    public String getSignupItem() {
        return signupItem;
    }

    public void setSignupItem(String signupItem) {
        this.signupItem = signupItem;
    }

    public LocalDateTime getSignTime() {
        return signTime;
    }

    public void setSignTime(LocalDateTime signTime) {
        this.signTime = signTime;
    }

    public AuditStatus getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(AuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }
}
