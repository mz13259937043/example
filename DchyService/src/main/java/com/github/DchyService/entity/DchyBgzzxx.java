package com.github.DchyService.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * (DchyBgzzxx)实体类
 *
 * @author qufan
 * @since 2020-10-27 19:20:28
 */
public class DchyBgzzxx implements Serializable {
	private static final long serialVersionUID = -81392128695076504L;
	// 企业ID
	private String bgdid;
	// 证书编号
	private String bgzsbh;
	// 证书有效期
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp bgzsyxq;
	// 资质等级
	private String bgzzdj;
	// 发证部门，默认值 市资源规划局
	private String bgfzbm;
	// 资质附件，上传附件后返回的ID
	private String bgzzfj;

	private String bgstate;

	private String bgwtgyy;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp bgsqsj;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp bgtgsj;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp bgcjsj;

	private String bgdchyywfw;

	private String bgfjzd1;

	private String bgfjzd2;

	private String id;

	private String bgzyfw;

	private String bgbid;

	private String logid;
	// 资质审核部门
	private String bgzzshbm;

	public String getBgwtgyy() {
		return bgwtgyy;
	}

	public void setBgwtgyy(String bgwtgyy) {
		this.bgwtgyy = bgwtgyy;
	}

	public String getBgzzshbm() {
		return bgzzshbm;
	}

	public void setBgzzshbm(String bgzzshbm) {
		this.bgzzshbm = bgzzshbm;
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

	public String getBgdid() {
		return bgdid;
	}

	public void setBgdid(String bgdid) {
		this.bgdid = bgdid;
	}

	public String getBgzsbh() {
		return bgzsbh;
	}

	public void setBgzsbh(String bgzsbh) {
		this.bgzsbh = bgzsbh;
	}

	public Timestamp getBgzsyxq() {
		return bgzsyxq;
	}

	public void setBgzsyxq(Timestamp bgzsyxq) {
		this.bgzsyxq = bgzsyxq;
	}

	public String getBgzzdj() {
		return bgzzdj;
	}

	public void setBgzzdj(String bgzzdj) {
		this.bgzzdj = bgzzdj;
	}

	public String getBgfzbm() {
		return bgfzbm;
	}

	public void setBgfzbm(String bgfzbm) {
		this.bgfzbm = bgfzbm;
	}

	public String getBgzzfj() {
		return bgzzfj;
	}

	public void setBgzzfj(String bgzzfj) {
		this.bgzzfj = bgzzfj;
	}

	public String getBgstate() {
		return bgstate;
	}

	public void setBgstate(String bgstate) {
		this.bgstate = bgstate;
	}

	public Timestamp getBgsqsj() {
		return bgsqsj;
	}

	public void setBgsqsj(Timestamp bgsqsj) {
		this.bgsqsj = bgsqsj;
	}

	public Timestamp getBgtgsj() {
		return bgtgsj;
	}

	public void setBgtgsj(Timestamp bgtgsj) {
		this.bgtgsj = bgtgsj;
	}

	public Timestamp getBgcjsj() {
		return bgcjsj;
	}

	public void setBgcjsj(Timestamp bgcjsj) {
		this.bgcjsj = bgcjsj;
	}

	public String getBgdchyywfw() {
		return bgdchyywfw;
	}

	public void setBgdchyywfw(String bgdchyywfw) {
		this.bgdchyywfw = bgdchyywfw;
	}

	public String getBgfjzd1() {
		return bgfjzd1;
	}

	public void setBgfjzd1(String bgfjzd1) {
		this.bgfjzd1 = bgfjzd1;
	}

	public String getBgfjzd2() {
		return bgfjzd2;
	}

	public void setBgfjzd2(String bgfjzd2) {
		this.bgfjzd2 = bgfjzd2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBgzyfw() {
		return bgzyfw;
	}

	public void setBgzyfw(String bgzyfw) {
		this.bgzyfw = bgzyfw;
	}

}