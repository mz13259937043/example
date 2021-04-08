package com.github.DchyService.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * (DchyLspersonal)实体类
 *
 * @author qufan
 * @since 2020-10-27 19:20:52
 */
public class DchyLspersonal implements Serializable {
	private static final long serialVersionUID = -83179343432222067L;

	private String id;
	/**
	 * 名字
	 */
	private String lsname;
	/**
	 * 身份证号
	 */
	private String lsidcard;
	/**
	 * 手机号
	 */
	private String lsphonenum;
	/**
	 * 密码
	 */
	private String lspassword;
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp lscreatetime;
	/**
	 * 附件信息
	 */
	private String lsinfor;
	/**
	 * 状态
	 */
	private String lsstate;
	/**
	 * 企业id
	 */
	private String lsdid;
	/**
	 * 变更表ID
	 */
	private String bgbid;
	/**
	 * 变更表ID
	 */
	private String lstype;

	public String getLstype() {
		return lstype;
	}

	public void setLstype(String lstype) {
		this.lstype = lstype;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLsname() {
		return lsname;
	}

	public void setLsname(String lsname) {
		this.lsname = lsname;
	}

	public String getLsidcard() {
		return lsidcard;
	}

	public void setLsidcard(String lsidcard) {
		this.lsidcard = lsidcard;
	}

	public String getLsphonenum() {
		return lsphonenum;
	}

	public void setLsphonenum(String lsphonenum) {
		this.lsphonenum = lsphonenum;
	}

	public String getLspassword() {
		return lspassword;
	}

	public void setLspassword(String lspassword) {
		this.lspassword = lspassword;
	}

	public Timestamp getLscreatetime() {
		return lscreatetime;
	}

	public void setLscreatetime(Timestamp lscreatetime) {
		this.lscreatetime = lscreatetime;
	}

	public String getLsinfor() {
		return lsinfor;
	}

	public void setLsinfor(String lsinfor) {
		this.lsinfor = lsinfor;
	}

	public String getLsstate() {
		return lsstate;
	}

	public void setLsstate(String lsstate) {
		this.lsstate = lsstate;
	}

	public String getLsdid() {
		return lsdid;
	}

	public void setLsdid(String lsdid) {
		this.lsdid = lsdid;
	}

	public String getBgbid() {
		return bgbid;
	}

	public void setBgbid(String bgbid) {
		this.bgbid = bgbid;
	}

}