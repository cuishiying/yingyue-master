package com.future.yingyue.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class VedioQueryDTO {

    private String keyword;

    private Boolean online;

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

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
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
