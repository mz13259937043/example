package com.github.DchyService.entity;

import java.io.Serializable;

/**
 * (DchyRoleuser)实体类
 *
 * @author qufan
 * @since 2020-10-30 11:38:44
 */
public class DchyRoleuser implements Serializable {
	private static final long serialVersionUID = -34722616438545014L;

	private String id;

	private String roleid;

	private String userid;

	private String fjzd;

	private DchyRole role;

	private DchyPersonal personal;

	public DchyRole getRole() {
		return role;
	}

	public void setRole(DchyRole role) {
		this.role = role;
	}

	public DchyPersonal getPersonal() {
		return personal;
	}

	public void setPersonal(DchyPersonal personal) {
		this.personal = personal;
	}

	public String getFjzd() {
		return fjzd;
	}

	public void setFjzd(String fjzd) {
		this.fjzd = fjzd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}