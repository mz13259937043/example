package com.github.DchyService.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * (DchyZzxx)实体类
 *
 * @author qufan
 * @since 2020-10-27 19:21:32
 */
public class DchyZzxx implements Serializable {
	private static final long serialVersionUID = 270133842707922765L;

	private String id;

	private String did;

	private String zsbh;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp zsyxq;

	private String zzdj;

	private String fzbm;

	private String zzfj;

	private String state;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp cjsj;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp djsj;

	private String dchyywfw;

	private String fjzd1;

	private String fjzd2;

	private String zyfw;

	private String zzshbm;

	// 企业名称
	private String qymc;
	// 页码数
	private int pageNum;
	// 页码大小
	private int pageSize;

	private int offset;
	private int limit;
	
	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Timestamp getDjsj() {
		return djsj;
	}

	public void setDjsj(Timestamp djsj) {
		this.djsj = djsj;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getQymc() {
		return qymc;
	}

	public void setQymc(String qymc) {
		this.qymc = qymc;
	}

	public String getZzshbm() {
		return zzshbm;
	}

	public void setZzshbm(String zzshbm) {
		this.zzshbm = zzshbm;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getZsbh() {
		return zsbh;
	}

	public void setZsbh(String zsbh) {
		this.zsbh = zsbh;
	}

	public Timestamp getZsyxq() {
		return zsyxq;
	}

	public void setZsyxq(Timestamp zsyxq) {
		this.zsyxq = zsyxq;
	}

	public String getZzdj() {
		return zzdj;
	}

	public void setZzdj(String zzdj) {
		this.zzdj = zzdj;
	}

	public String getFzbm() {
		return fzbm;
	}

	public void setFzbm(String fzbm) {
		this.fzbm = fzbm;
	}

	public String getZzfj() {
		return zzfj;
	}

	public void setZzfj(String zzfj) {
		this.zzfj = zzfj;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getCjsj() {
		return cjsj;
	}

	public void setCjsj(Timestamp cjsj) {
		this.cjsj = cjsj;
	}

	public String getDchyywfw() {
		return dchyywfw;
	}

	public void setDchyywfw(String dchyywfw) {
		this.dchyywfw = dchyywfw;
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

	public String getZyfw() {
		return zyfw;
	}

	public void setZyfw(String zyfw) {
		this.zyfw = zyfw;
	}

}