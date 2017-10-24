package com.future.yingyue.enums;

/**
 * Created by cuishiying on 2017/6/12.
 */
public enum Sex {
    secure(0,"保密"),man(1,"男"),woman(2,"女");
    private Integer id;
    private String name;

    private Sex(Integer id, String name) {
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
