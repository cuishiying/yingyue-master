package com.future.yingyue.repository;

import com.future.yingyue.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by cuishiying on 2017/5/11.
 */
public interface AdminRoleRepository extends JpaRepository<AdminRole,Integer>,JpaSpecificationExecutor<AdminRole> {
    AdminRole findByRoleName(String roleName);
}
