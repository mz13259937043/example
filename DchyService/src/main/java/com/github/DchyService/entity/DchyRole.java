package com.github.DchyService.entity;

import java.io.Serializable;

/**
 * (DchyRole)实体类
 *
 * @author qufan
 * @since 2020-10-30 11:37:49
 */
public class DchyRole implements Serializable {
	private static final long serialVersionUID = -24594623907174780L;

	private String id;
	// 角色名称
	private String role;
	// 备注
	private String count;

	private String fjzd1;

	private String fjzd2;
	// 角色类型，预留字段
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getFjzd1() {
		return fjzd1;
	}

	public void setFjzd1(String fjzd1) {
		this.fjzd1 = fjzd1;
	}

	public String getFjzd2() {
		return fjzd2;
	}

	public void setFjzd2(String fjzd2) {
		this.fjzd2 = fjzd2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}