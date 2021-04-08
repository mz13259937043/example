package com.github.DchyService.VO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author qufan
 * @version v1.0
 * @Date 创建时间：2020-10-26 14:24:23
 * @Description 资质等级VO类
 */
public class ZzxxVO implements Serializable {
	private static final long serialVersionUID = 1L;
	// ID
	private String id;

	// 单位主键
	private String did;

	// 证书编号
	private String zsbh;
	// 人员类型
	private String type;

	// 证书有效期
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp zsyxq;

	// 资质等级
	private String zzdj;

	// 发证部门
	private String fzbm;

	// 资质附件
	private String zzfj;

	// 执业范围
	private String zyfw;

	// 状态
	private String state;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp djsj;

	// 创建时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date cjsj;

	// 多测合一业务范围
	private String dchyywfw;

	// 附加字段1
	private String fjzd1;

	// 附加字段2
	private String fjzd2;

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
	private String zzshbm;
	//办件量
	private Integer bjl;

	public Timestamp getDjsj() {
		return djsj;
	}

	public void setDjsj(Timestamp djsj) {
		this.djsj = djsj;
	}

	public String getZzshbm() {
		return zzshbm;
	}

	public void setZzshbm(String zzshbm) {
		this.zzshbm = zzshbm;
	}

	public String getLINK_MAN() {
		return LINK_MAN;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getZyfw() {
		return zyfw;
	}

	public void setZyfw(String zyfw) {
		this.zyfw = zyfw;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
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

	public Integer getBjl() {
		return bjl;
	}

	public void setBjl(Integer bjl) {
		this.bjl = bjl;
	}

}
