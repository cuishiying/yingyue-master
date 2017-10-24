package com.future.yingyue.repository;


import com.future.yingyue.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecruitRepository extends JpaRepository<Recruit, Integer>,JpaSpecificationExecutor<Recruit> {

}

