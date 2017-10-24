package com.future.yingyue.entity;

import com.future.yingyue.base.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

/**
 * Created by cuishiying on 2017/5/9.
 * 权限
 */
@Entity
public class Authority extends BaseEntity implements GrantedAuthority {

    private static final long serialVersionUID = 202454345423212279L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
