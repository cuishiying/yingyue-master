package com.future.yingyue.enums;

/**
 * Created by ruiyunitc on 17/2/15.
 */
public enum ProjectStatus {
    firstState(0, "初步接洽"), faceContact(1,"推进面谈"),recommendationOrder(2,"提交建议书"),waittingState(3,"等待对方决策"),successProject(4,"成功")
    ,failureProject(5,"失败"),breakProject(6, "中止"), BDKeeping(7, "BD维护中"),toRkylin(8,"已转瑞金麟"), giveUp(9, "我方放弃");

    private Integer id;
    private String name;

    private ProjectStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
