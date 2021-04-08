package com.github.DchyService.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * (DchyBgpersonal)实体类
 *
 * @author qufan
 * @since 2020-10-27 19:10:58
 */
public class DchyBgpersonal implements Serializable {
	private static final long serialVersionUID = -13417685774401344L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 姓名
	 */
	private String bgname;
	/**
	 * 身份证号
	 */
	private String bgidcard;
	/**
	 * 手机号码
	 */
	private String bgphonenum;
	/**
	 * 密码
	 */
	private String bgpassword;
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp bgcreatetime;
	/**
	 * 附件信息
	 */
	private String bginfor;
	/**
	 * 变更状态
	 */
	private String bgstate;
	/**
	 * 人员类型
	 */
	private String bgtype;
	/**
	 * 企业ID
	 */
	private String bgdid;
	/**
	 * 变更表ID
	 */
	private String bgbid;
	/**
	 * 
	 * 日志表id
	 */
	private String logid;
	/**
	 * 
	 * 角色id
	 */
	private String roleid;

	public String getRoleid() {
		return roleid;
	}

	public String getBgtype() {
		return bgtype;
	}

	public void setBgtype(String bgtype) {
		this.bgtype = bgtype;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getBgbid() {
		return bgbid;
	}

	public void setBgbid(String bgbid) {
		this.bgbid = bgbid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBgname() {
		return bgname;
	}

	public void setBgname(String bgname) {
		this.bgname = bgname;
	}

	public String getBgidcard() {
		return bgidcard;
	}

	public void setBgidcard(String bgidcard) {
		this.bgidcard = bgidcard;
	}

	public String getBgphonenum() {
		return bgphonenum;
	}

	public void setBgphonenum(String bgphonenum) {
		this.bgphonenum = bgphonenum;
	}

	public String getBgpassword() {
		return bgpassword;
	}

	public void setBgpassword(String bgpassword) {
		this.bgpassword = bgpassword;
	}

	public Timestamp getBgcreatetime() {
		return bgcreatetime;
	}

	public void setBgcreatetime(Timestamp bgcreatetime) {
		this.bgcreatetime = bgcreatetime;
	}

	public String getBginfor() {
		return bginfor;
	}

	public void setBginfor(String bginfor) {
		this.bginfor = bginfor;
	}

	public String getBgstate() {
		return bgstate;
	}

	public void setBgstate(String bgstate) {
		this.bgstate = bgstate;
	}

	public String getBgdid() {
		return bgdid;
	}

	public void setBgdid(String bgdid) {
		this.bgdid = bgdid;
	}

}