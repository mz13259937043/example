package com.github.DchyService.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DchyPersonal implements Serializable {
	private static final long serialVersionUID = -73203304271105622L;
	private String id;
	private String name;
	private String idcard;
	private String password;
	private String phonenum;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp createtime;
	private String infor;
	private String state;
	private String type;
	private String ssbm;
	private String memo2;
	private String did;
	private List<String> roleidlist;

	private List<DchyRole> rolelist;

	public List<String> getRoleidlist() {
		return roleidlist;
	}

	public void setRoleidlist(List<String> roleidlist) {
		this.roleidlist = roleidlist;
	}

	public List<DchyRole> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<DchyRole> rolelist) {
		this.rolelist = rolelist;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getInfor() {
		return infor;
	}

	public void setInfor(String infor) {
		this.infor = infor;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSsbm() {
		return ssbm;
	}

	public void setSsbm(String ssbm) {
		this.ssbm = ssbm;
	}

	public String getMemo2() {
		return memo2;
	}

	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

}
