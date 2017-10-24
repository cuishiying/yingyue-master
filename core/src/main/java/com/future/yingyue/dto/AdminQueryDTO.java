package com.future.yingyue.dto;

import com.future.yingyue.entity.AdminRole;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class AdminQueryDTO {

    private String keyword;
    private Integer adminRoleId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate beginDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getAdminRoleId() {
        return adminRoleId;
    }

    public void setAdminRoleId(Integer adminRoleId) {
        this.adminRoleId = adminRoleId;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
