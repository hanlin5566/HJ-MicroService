package com.hzcf.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hzcf.auth.dao.entity.UserEntity;
/**
 * Create by hanlin on 2018年5月24日
 **/
public interface UserDAO extends JpaRepository<UserEntity, Long>{
	public UserEntity findByUserNameAndUserPwd(String userName,String userpwd);
}
