package com.github.DchyService.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * (DchyLszzxx)实体类
 *
 * @author qufan
 * @since 2020-10-27 19:21:07
 */
public class DchyLszzxx implements Serializable {
    private static final long serialVersionUID = -59849044245468579L;

    private String id;

    private String lsdid;

    private String lszsbh;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp lszsyxq;

    private String lszzdj;

    private String lsfzbm;

    private String lszzfj;

    private String lsstate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp lscjsj;

    private String lsdchyywfw;

    private String lsfjzd1;

    private String lsfjzd2;

    private String bgbid;

    private String lszyfw;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp lssqsj;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp lstgsj;
    
    


    public Timestamp getLstgsj() {
		return lstgsj;
	}

	public void setLstgsj(Timestamp lstgsj) {
		this.lstgsj = lstgsj;
	}

	public Timestamp getLssqsj() {
		return lssqsj;
	}

	public void setLssqsj(Timestamp lssqsj) {
		this.lssqsj = lssqsj;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLsdid() {
        return lsdid;
    }

    public void setLsdid(String lsdid) {
        this.lsdid = lsdid;
    }

    public String getLszsbh() {
        return lszsbh;
    }

    public void setLszsbh(String lszsbh) {
        this.lszsbh = lszsbh;
    }

    public Timestamp getLszsyxq() {
        return lszsyxq;
    }

    public void setLszsyxq(Timestamp lszsyxq) {
        this.lszsyxq = lszsyxq;
    }

    public String getLszzdj() {
        return lszzdj;
    }

    public void setLszzdj(String lszzdj) {
        this.lszzdj = lszzdj;
    }

    public String getLsfzbm() {
        return lsfzbm;
    }

    public void setLsfzbm(String lsfzbm) {
        this.lsfzbm = lsfzbm;
    }

    public String getLszzfj() {
        return lszzfj;
    }

    public void setLszzfj(String lszzfj) {
        this.lszzfj = lszzfj;
    }

    public String getLsstate() {
        return lsstate;
    }

    public void setLsstate(String lsstate) {
        this.lsstate = lsstate;
    }

    public Timestamp getLscjsj() {
        return lscjsj;
    }

    public void setLscjsj(Timestamp lscjsj) {
        this.lscjsj = lscjsj;
    }

    public String getLsdchyywfw() {
        return lsdchyywfw;
    }

    public void setLsdchyywfw(String lsdchyywfw) {
        this.lsdchyywfw = lsdchyywfw;
    }

    public String getLsfjzd1() {
        return lsfjzd1;
    }

    public void setLsfjzd1(String lsfjzd1) {
        this.lsfjzd1 = lsfjzd1;
    }

    public String getLsfjzd2() {
        return lsfjzd2;
    }

    public void setLsfjzd2(String lsfjzd2) {
        this.lsfjzd2 = lsfjzd2;
    }

    public String getBgbid() {
        return bgbid;
    }

    public void setBgbid(String bgbid) {
        this.bgbid = bgbid;
    }

    public String getLszyfw() {
        return lszyfw;
    }

    public void setLszyfw(String lszyfw) {
        this.lszyfw = lszyfw;
    }

}