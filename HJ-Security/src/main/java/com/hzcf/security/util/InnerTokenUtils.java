package com.hzcf.security.util;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Create by hanlin on 2018年5月24日
 **/
@Component
public class InnerTokenUtils {
	private static Logger logger = LoggerFactory.getLogger(InnerTokenUtils.class);
	
	@Value("${spring.application.name}")
	private String appName;
	@Value("${server.port}")
	private int port;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	private String token;
	
	public String getToken() {
		return token;
	}
	
	/**
     * 刷新Token
     */
    public void reloadApiToken() {
        while (StringUtils.isEmpty(token)) {
            try {
                Thread.sleep(1000);
                initToken();
            } catch (InterruptedException e) {
                logger.error(appName+"刷新token失败", e);
            }
        }
    }
    
    
    @PostConstruct
	private void initToken() {
		JSONObject subject = new JSONObject();
		subject.put("appName", appName);
		this.token = jwtUtils.createJWT(String.valueOf(port), subject.toString());
	}
}
