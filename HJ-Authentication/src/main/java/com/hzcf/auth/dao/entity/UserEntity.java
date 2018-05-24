package com.hzcf.auth.dao.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;

/**
 * system_user 
 * @author huhanlin 2017-12-27
 */
@Table(name="system_user")
@Entity
public class UserEntity {
    /**
     * system_user主键
     */
	@Id
    @GeneratedValue
	private Long id;


    /**
     * 性别 0
     */
    private String sex;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 接口/api名称
     */
    private String apiName;

    /**
     * api/接口密码
     */
    private String apiPwd;

    /**
     * 部门Id
     */
    private String deptId;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 加密字符串
     */
    private String userSalt;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 邮编
     */
    private String userTel;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 公司Code
     */
    private String companyCode;

    /**
     * 创建人
     */
    private Integer createUid;

    /**
     * 修改人
     */
    private Integer updateUid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 数据状态（0.未知，1.正常，-1.删除)
     */
    private Integer dataStatus;

	public Long getId() {
		return id;
	}

	public String getSex() {
		return sex;
	}

	public String getUserName() {
		return userName;
	}

	public String getApiName() {
		return apiName;
	}

	public String getApiPwd() {
		return apiPwd;
	}

	public String getDeptId() {
		return deptId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public String getUserSalt() {
		return userSalt;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserTel() {
		return userTel;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public Integer getCreateUid() {
		return createUid;
	}

	public Integer getUpdateUid() {
		return updateUid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public Integer getDataStatus() {
		return dataStatus;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public void setApiPwd(String apiPwd) {
		this.apiPwd = apiPwd;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setCreateUid(Integer createUid) {
		this.createUid = createUid;
	}

	public void setUpdateUid(Integer updateUid) {
		this.updateUid = updateUid;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	
}