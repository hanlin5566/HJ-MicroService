package com.hzcf.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hzcf.auth.dao.entity.UserEntity;
import com.hzcf.auth.service.AuthService;
import com.hzcf.base.result.ResponseCode;
import com.hzcf.base.result.ResponseData;
import com.hzcf.security.util.JWTUtils;
/**
 * Create by hanlin on 2018年5月24日
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthService authService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@PostMapping()
	public ResponseData login(@RequestBody UserEntity userEntity){
		UserEntity result = authService.login(userEntity);
		if(result != null ){
			//登录成功返回token
			//构造subject
			String token = jwtUtils.createJWT(result.getId().toString(), result.toString());
			return ResponseData.ok(token);
		}else{
			return ResponseData.fail(ResponseCode.LOGIN_FAILED);
		}
	}
}