package com.github.DchyService.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (Jsuser)实体类
 *
 * @author ljc
 * @since 2020-09-28 15:02:59
 */
public class DchyUser implements Serializable {
    private static final long serialVersionUID = -73203304271105622L;

    private String id;

    private String username;

    private String idcard;

    private String phonenum;

    private String legalpersonid;

    private String role;
   
    private String address;

    private Date createtime;

    private String memo;

    private String memo1;

    private String memo2;

    private String isdelete;

    private String companyname;
    
    private String license;

    public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	private Integer pagenum;

    private Integer pagesize;

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getLegalpersonid() {
        return legalpersonid;
    }

    public void setLegalpersonid(String legalpersonid) {
        this.legalpersonid = legalpersonid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	

}