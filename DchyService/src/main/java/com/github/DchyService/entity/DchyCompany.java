package com.github.DchyService.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DchyCompany implements Serializable{

    private static final long serialVersionUID = -73203304271105622L;
         
    private String id;
    private String tyshxydm;//统一社会信用代码
    private String dwmc;//单位名称
    private String dwxz;//单位性质
    private String qydz;//企业地址
    private String jyfw;//经营范围
    private String frxm;//法人姓名
    private String frsfz;//法人身份证
    private String frdh;//法人电话
    private String zczj;//注册资金
    private String hylxmc;//行业类型名称
    private String jyqx;//经营期限
    private String yyzz;//营业执照
    private String qyzt;//企业状态
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date cjsj;//创建时间
    private String memo1;//预留字段
    private String memo2;//
    private String lxr;//联系人
    private String lxdh;//联系电话
	private DchyZzxx zzxx; // 资质信息

	public DchyZzxx getZzxx() {
		return zzxx;
	}

	public void setZzxx(DchyZzxx zzxx) {
		this.zzxx = zzxx;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTyshxydm() {
		return tyshxydm;
	}
	public void setTyshxydm(String tyshxydm) {
		this.tyshxydm = tyshxydm;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getDwxz() {
		return dwxz;
	}
	public void setDwxz(String dwxz) {
		this.dwxz = dwxz;
	}
	public String getQydz() {
		return qydz;
	}
	public void setQydz(String qydz) {
		this.qydz = qydz;
	}
	public String getJyfw() {
		return jyfw;
	}
	public void setJyfw(String jyfw) {
		this.jyfw = jyfw;
	}
	public String getFrxm() {
		return frxm;
	}
	public void setFrxm(String frxm) {
		this.frxm = frxm;
	}
	public String getFrsfz() {
		return frsfz;
	}
	public void setFrsfz(String frsfz) {
		this.frsfz = frsfz;
	}
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getZczj() {
		return zczj;
	}
	public void setZczj(String zczj) {
		this.zczj = zczj;
	}
	public String getHylxmc() {
		return hylxmc;
	}
	public void setHylxmc(String hylxmc) {
		this.hylxmc = hylxmc;
	}
	public String getJyqx() {
		return jyqx;
	}
	public void setJyqx(String jyqx) {
		this.jyqx = jyqx;
	}
	public String getYyzz() {
		return yyzz;
	}
	public void setYyzz(String yyzz) {
		this.yyzz = yyzz;
	}
	public String getQyzt() {
		return qyzt;
	}
	public void setQyzt(String qyzt) {
		this.qyzt = qyzt;
	}
	public Date getCjsj() {
		return cjsj;
	}
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	public String getMemo1() {
		return memo1;
	}
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	public String getMemo2() {
		return memo2;
	}
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	public String getFrdh() {
		return frdh;
	}
	public void setFrdh(String frdh) {
		this.frdh = frdh;
	}
	public String getLxr() {
		return lxr;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	
	
    
    
	
}
