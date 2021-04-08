package com.github.DchyService.VO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author qufan
 * @version v1.0
 * @Date 创建时间：2020-10-26 14:52:26
 * @Description 变更资质信息视图类
 */
public class BgZzxxVO implements Serializable {
	private static final long serialVersionUID = 1L;

	// ID
	private String id;

	// 变更表主键
	private String bgdid;

	// 单位主键
	private String bgbid;

	// 证书编号
	private String bgzsbh;

	// 证书有效期
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp bgzsyxq;

	// 资质等级
	private String bgzzdj;

	// 人员类型
	private String bgtype;

	// 发证部门
	private String bgfzbm;

	// 资质附件
	private String bgzzfj;

	// 执业范围
	private String bgzyfw;

	// 状态
	private String bgstate;

	// 申请时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bgsqsj;

	// 通过时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bgtgsj;

	// 日志id
	private String logid;

	// 未通过原因
	private String bgwtgyy;

	// 创建时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date bgcjsj;

	// 多测合一业务范围
	private String bgdchyywfw;

	// 附加字段1
	private String bgfjzd1;

	// 附加字段2
	private String bgfjzd2;

	// 统一社会信用代码
	private String LICENSE_NO;

	// 单位名称
	private String UNIT_NAME;

	// 单位性质
	private String UNIT_TYPE;

	// 企业地址
	private String UNIT_ADDRESS;

	// 经营范围
	private String BUSI_ARRANGE;

	// 法人姓名
	private String LEGAL_NAME;

	// 法人身份证
	private String LEGAL_ID_NUMBER;

	// 联系电话
	private String LINK_PHONE;

	// 注册资金
	private String REGISTERED_CAPITAL;

	// 行业类型名称
	private String INDUSTRY_TYPE_NAME;

	// 经营期限
	private String OPERATING_PERIOD;

	// 营业执照
	private String BUSINESS_LICENSE;

	// 企业状态
	private String COMPANY_STATE;

	// 联系人
	private String LINK_MAN;

	// 联系电话
	private String PHONE;
	
	// 资质审核部门
	private String bgzzshbm;

	public String getBgzzshbm() {
		return bgzzshbm;
	}

	public void setBgzzshbm(String bgzzshbm) {
		this.bgzzshbm = bgzzshbm;
	}

	public String getLINK_MAN() {
		return LINK_MAN;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public void setLINK_MAN(String lINK_MAN) {
		LINK_MAN = lINK_MAN;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBgdid() {
		return bgdid;
	}

	public void setBgdid(String bgdid) {
		this.bgdid = bgdid;
	}

	public String getBgbid() {
		return bgbid;
	}

	public void setBgbid(String bgbid) {
		this.bgbid = bgbid;
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

	public String getBgtype() {
		return bgtype;
	}

	public void setBgtype(String bgtype) {
		this.bgtype = bgtype;
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

	public String getBgzyfw() {
		return bgzyfw;
	}

	public void setBgzyfw(String bgzyfw) {
		this.bgzyfw = bgzyfw;
	}

	public String getBgstate() {
		return bgstate;
	}

	public void setBgstate(String bgstate) {
		this.bgstate = bgstate;
	}

	public Date getBgsqsj() {
		return bgsqsj;
	}

	public void setBgsqsj(Date bgsqsj) {
		this.bgsqsj = bgsqsj;
	}

	public Date getBgtgsj() {
		return bgtgsj;
	}

	public void setBgtgsj(Date bgtgsj) {
		this.bgtgsj = bgtgsj;
	}

	public String getBgwtgyy() {
		return bgwtgyy;
	}

	public void setBgwtgyy(String bgwtgyy) {
		this.bgwtgyy = bgwtgyy;
	}

	public Date getBgcjsj() {
		return bgcjsj;
	}

	public void setBgcjsj(Date bgcjsj) {
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

	public String getLICENSE_NO() {
		return LICENSE_NO;
	}

	public void setLICENSE_NO(String lICENSE_NO) {
		LICENSE_NO = lICENSE_NO;
	}

	public String getUNIT_NAME() {
		return UNIT_NAME;
	}

	public void setUNIT_NAME(String uNIT_NAME) {
		UNIT_NAME = uNIT_NAME;
	}

	public String getUNIT_TYPE() {
		return UNIT_TYPE;
	}

	public void setUNIT_TYPE(String uNIT_TYPE) {
		UNIT_TYPE = uNIT_TYPE;
	}

	public String getUNIT_ADDRESS() {
		return UNIT_ADDRESS;
	}

	public void setUNIT_ADDRESS(String uNIT_ADDRESS) {
		UNIT_ADDRESS = uNIT_ADDRESS;
	}

	public String getBUSI_ARRANGE() {
		return BUSI_ARRANGE;
	}

	public void setBUSI_ARRANGE(String bUSI_ARRANGE) {
		BUSI_ARRANGE = bUSI_ARRANGE;
	}

	public String getLEGAL_NAME() {
		return LEGAL_NAME;
	}

	public void setLEGAL_NAME(String lEGAL_NAME) {
		LEGAL_NAME = lEGAL_NAME;
	}

	public String getLEGAL_ID_NUMBER() {
		return LEGAL_ID_NUMBER;
	}

	public void setLEGAL_ID_NUMBER(String lEGAL_ID_NUMBER) {
		LEGAL_ID_NUMBER = lEGAL_ID_NUMBER;
	}

	public String getLINK_PHONE() {
		return LINK_PHONE;
	}

	public void setLINK_PHONE(String lINK_PHONE) {
		LINK_PHONE = lINK_PHONE;
	}

	public String getREGISTERED_CAPITAL() {
		return REGISTERED_CAPITAL;
	}

	public void setREGISTERED_CAPITAL(String rEGISTERED_CAPITAL) {
		REGISTERED_CAPITAL = rEGISTERED_CAPITAL;
	}

	public String getINDUSTRY_TYPE_NAME() {
		return INDUSTRY_TYPE_NAME;
	}

	public void setINDUSTRY_TYPE_NAME(String iNDUSTRY_TYPE_NAME) {
		INDUSTRY_TYPE_NAME = iNDUSTRY_TYPE_NAME;
	}

	public String getOPERATING_PERIOD() {
		return OPERATING_PERIOD;
	}

	public void setOPERATING_PERIOD(String oPERATING_PERIOD) {
		OPERATING_PERIOD = oPERATING_PERIOD;
	}

	public String getBUSINESS_LICENSE() {
		return BUSINESS_LICENSE;
	}

	public void setBUSINESS_LICENSE(String bUSINESS_LICENSE) {
		BUSINESS_LICENSE = bUSINESS_LICENSE;
	}

	public String getCOMPANY_STATE() {
		return COMPANY_STATE;
	}

	public void setCOMPANY_STATE(String cOMPANY_STATE) {
		COMPANY_STATE = cOMPANY_STATE;
	}

}
