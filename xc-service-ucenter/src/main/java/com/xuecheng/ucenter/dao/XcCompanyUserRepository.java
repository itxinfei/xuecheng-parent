package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcCompanyUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
@Mapper
public interface XcCompanyUserRepository extends JpaRepository<XcCompanyUser, String> {

    //根据用户id查询该用户所属的公司id
    XcCompanyUser findByUserId(String userId);
}
