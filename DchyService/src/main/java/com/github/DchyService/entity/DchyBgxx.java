package com.github.DchyService.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * (DchyBgxx)实体类
 *
 * @author qufan
 * @since 2020-10-27 19:20:02
 */
public class DchyBgxx implements Serializable {
    private static final long serialVersionUID = -99101603298760488L;

    private String id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp bgcjsj;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp bgjssj;

    private String bgstate;

    private String fjzd1;

    private String fjzd2;

    private String bgdid;

    private String bgyy;

    private String bgjg;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getBgcjsj() {
        return bgcjsj;
    }

    public void setBgcjsj(Timestamp bgcjsj) {
        this.bgcjsj = bgcjsj;
    }

    public Timestamp getBgjssj() {
        return bgjssj;
    }

    public void setBgjssj(Timestamp bgjssj) {
        this.bgjssj = bgjssj;
    }

    public String getBgstate() {
        return bgstate;
    }

    public void setBgstate(String bgstate) {
        this.bgstate = bgstate;
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

    public String getBgdid() {
        return bgdid;
    }

    public void setBgdid(String bgdid) {
        this.bgdid = bgdid;
    }

    public String getBgyy() {
        return bgyy;
    }

    public void setBgyy(String bgyy) {
        this.bgyy = bgyy;
    }

    public String getBgjg() {
        return bgjg;
    }

    public void setBgjg(String bgjg) {
        this.bgjg = bgjg;
    }

}