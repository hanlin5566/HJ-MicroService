package com.hzcf.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzcf.auth.dao.UserDAO;
import com.hzcf.auth.dao.entity.UserEntity;
/**
 * Create by hanlin on 2018年5月24日
 **/
@Service
public class AuthService {
	@Autowired
	UserDAO userDAO;
	
	public UserEntity login(UserEntity user){
		UserEntity result = userDAO.findByUserNameAndUserPwd(user.getUserName(), user.getUserPwd());
		return result;
	}
}
